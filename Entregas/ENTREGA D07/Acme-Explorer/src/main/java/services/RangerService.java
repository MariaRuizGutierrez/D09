
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RangerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Curricula;
import domain.MessageFolder;
import domain.Ranger;
import domain.SocialIdentity;
import domain.Trip;

@Service
@Transactional
public class RangerService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RangerRepository			rangerRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private MessageFolderService		messageFolderService;

	@Autowired
	private CurriculaService			curriculaService;

	@Autowired
	private ActorService				actorService;

	@Autowired
	private ConfigurationSystemService	configurationSystemService;


	// Constructors-------------------------------------------------------

	public RangerService() {
		super();
	}

	// Simple CRUD methods------------------------------------------------

	public Ranger create() {

		Ranger result;
		UserAccount userAccount;
		Authority authority;
		Collection<SocialIdentity> socialIdentities;
		Collection<MessageFolder> messagesFolders;
		Collection<Trip> trips;

		result = new Ranger();
		userAccount = new UserAccount();
		authority = new Authority();
		socialIdentities = new ArrayList<>();
		messagesFolders = new ArrayList<>();
		trips = new ArrayList<>();

		messagesFolders.addAll(this.messageFolderService.createDefaultFoldersForRegister(result));

		authority.setAuthority("RANGER");
		userAccount.addAuthority(authority);
		userAccount.setActivated(true);
		result.setUserAccount(userAccount);
		result.setMessagesFolders(messagesFolders);
		result.setSocialIdentities(socialIdentities);
		result.setTrips(trips);
		result.setPhone(this.configurationSystemService.findOne().getDefaultPhone());
		result.setSuspicious(false);

		return result;
	}
	public Collection<Ranger> findAll() {

		Collection<Ranger> result;
		result = this.rangerRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Ranger findOne(final int rangerId) {

		Assert.isTrue(rangerId != 0);
		Ranger result;
		result = this.rangerRepository.findOne(rangerId);

		return result;
	}

	public Ranger save(final Ranger ranger) {

		Assert.notNull(ranger);
		final Ranger result;
		final Md5PasswordEncoder encoder;
		final String passwordHash;

		if (ranger.getId() == 0) {
			final String password = ranger.getUserAccount().getPassword();
			encoder = new Md5PasswordEncoder();
			passwordHash = encoder.encodePassword(password, null);
			ranger.getUserAccount().setPassword(passwordHash);
		}
		result = this.rangerRepository.save(ranger);

		Assert.notNull(result);

		return result;
	}
	public void delete(final Ranger ranger) {

		Assert.notNull(ranger);
		Assert.isTrue(ranger.getId() != 0);
		this.rangerRepository.delete(ranger);

	}

	// Other business methods----------------------------------

	public Ranger findByPrincipal() {

		Ranger result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.rangerRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}
	public void checkPrincipal() {

		final UserAccount userAccount = LoginService.getPrincipal();

		Assert.notNull(userAccount);

		final Collection<Authority> authorities = userAccount.getAuthorities();
		Assert.notNull(authorities);

		final Authority auth = new Authority();
		auth.setAuthority("RANGER");

		Assert.isTrue(authorities.contains(auth));
	}

	public Boolean rangerIsSpam(final Ranger ranger) {
		Boolean result;
		Collection<Curricula> curricula;
		result = false;

		curricula = this.curriculaService.findAll();

		for (final Curricula c : curricula)
			if (c.getRanger().equals(ranger)) {
				result = this.curriculaService.curriculaContainsSpam(ranger);
				if (result == true) {
					ranger.setSuspicious(result);
					return result;
				}
			}

		result = this.actorService.actorIsSpam(ranger);
		if (result == true) {
			ranger.setSuspicious(result);
			return result;
		}
		return result;
	}

	public Ranger findRangerByTripId(final int tripId) {
		Ranger result;
		result = this.rangerRepository.findRangerByTripId(tripId);
		return result;
	}
}
