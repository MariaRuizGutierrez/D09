
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.MessageFolder;
import domain.SocialIdentity;
import domain.Sponsor;
import domain.Sponsorship;

@Service
@Transactional
public class SponsorService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private SponsorRepository			sponsorRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private MessageFolderService		messageFolderService;

	@Autowired
	private ActorService				actorService;

	@Autowired
	private ConfigurationSystemService	configurationSystemService;


	// Constructors -----------------------------------------------------------
	public SponsorService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------
	public Sponsor create() {
		Sponsor result;
		UserAccount userAccount;
		Authority authority;
		Collection<SocialIdentity> socialIdentities;
		Collection<MessageFolder> messagesFolders;
		Collection<Sponsorship> sponsorships;

		result = new Sponsor();
		userAccount = new UserAccount();
		authority = new Authority();
		socialIdentities = new ArrayList<>();
		messagesFolders = new ArrayList<>();
		sponsorships = new ArrayList<>();

		messagesFolders.addAll(this.messageFolderService.createDefaultFoldersForRegister(result));

		authority.setAuthority(Authority.SPONSOR);
		userAccount.addAuthority(authority);
		userAccount.setActivated(true);
		result.setUserAccount(userAccount);
		result.setMessagesFolders(messagesFolders);
		result.setSocialIdentities(socialIdentities);
		result.setSponsorships(sponsorships);
		result.setPhone(this.configurationSystemService.findOne().getDefaultPhone());
		result.setSuspicious(false);

		return result;
	}
	public Sponsor findOne(final int idSponsor) {
		Assert.isTrue(idSponsor != 0);
		Sponsor result;
		result = this.sponsorRepository.findOne(idSponsor);
		return result;

	}
	public Collection<Sponsor> findAll() {
		Collection<Sponsor> result;
		result = this.sponsorRepository.findAll();
		Assert.notNull(result);
		return result;

	}
	public Sponsor save(final Sponsor sponsor) {
		Assert.notNull(sponsor);
		final Sponsor result;
		final Md5PasswordEncoder encoder;
		final String passwordHash;

		encoder = new Md5PasswordEncoder();
		passwordHash = encoder.encodePassword(sponsor.getUserAccount().getPassword(), null);
		sponsor.getUserAccount().setPassword(passwordHash);
		result = this.sponsorRepository.save(sponsor);

		Assert.notNull(result);

		return result;

	}
	public void delete(final Sponsor sponsor) {
		Assert.notNull(sponsor);
		Assert.isTrue(sponsor.getId() != 0);
		this.sponsorRepository.delete(sponsor);

	}
	// Other business methods -------------------------------------------------

	public Sponsor findByPrincipal() {

		Sponsor result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.sponsorRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	public void checkPrincipal() {

		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		final Collection<Authority> authorities = userAccount.getAuthorities();
		Assert.notNull(authorities);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.SPONSOR);

		Assert.isTrue(authorities.contains(auth));
	}

	public Boolean sponsorIsSpam(final Sponsor sponsor) {
		Boolean result;
		result = false;

		result = this.actorService.actorIsSpam(sponsor);
		if (result == true) {
			sponsor.setSuspicious(result);
			return result;
		}

		return result;
	}
}
