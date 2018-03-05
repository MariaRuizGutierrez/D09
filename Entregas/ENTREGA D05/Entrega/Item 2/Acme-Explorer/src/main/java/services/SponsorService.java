
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
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
	private SponsorRepository		sponsorRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private MessageFolderService	messageFolderService;


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

		messagesFolders.addAll(this.messageFolderService.createDefaultFolders());

		authority.setAuthority(Authority.SPONSOR);
		userAccount.addAuthority(authority);
		result.setUserAccount(userAccount);
		result.setMessagesFolders(messagesFolders);
		result.setSocialIdentities(socialIdentities);
		result.setSponsorships(sponsorships);

		return result;
	}
	public Sponsor findOne(int idSponsor) {
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
	public Sponsor save(Sponsor sponsor) {
		Assert.notNull(sponsor);
		Sponsor result;
		result = this.sponsorRepository.save(sponsor);

		Assert.notNull(result);

		return result;

	}
	public void delete(Sponsor sponsor) {
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

		UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		Collection<Authority> authorities = userAccount.getAuthorities();
		Assert.notNull(authorities);

		Authority auth = new Authority();
		auth.setAuthority(Authority.SPONSOR);

		Assert.isTrue(authorities.contains(auth));
	}
}
