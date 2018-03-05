
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.MessageFolder;
import domain.SocialIdentity;
import domain.Trip;

@Service
@Transactional
public class AdministratorService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private AdministratorRepository		administratorRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private MessageFolderService		messageFolderService;

	@Autowired
	private ActorService				actorService;

	@Autowired
	private ConfigurationSystemService	configurationSystemService;


	// Constructors-------------------------------------------------------

	public AdministratorService() {
		super();
	}

	// Simple CRUD methods------------------------------------------------

	public Administrator create() {
		Administrator result;
		UserAccount userAccount;
		Authority authority;
		Collection<SocialIdentity> socialIdentities;
		Collection<MessageFolder> messagesFolders;

		result = new Administrator();
		userAccount = new UserAccount();
		authority = new Authority();
		socialIdentities = new ArrayList<SocialIdentity>();
		messagesFolders = new ArrayList<>();

		messagesFolders.addAll(this.messageFolderService.createDefaultFoldersForRegister(result));
		authority.setAuthority(Authority.ADMINISTRATOR);
		userAccount.addAuthority(authority);
		userAccount.setActivated(true);
		result.setUserAccount(userAccount);
		result.setSocialIdentities(socialIdentities);
		result.setMessagesFolders(messagesFolders);
		result.setPhone(this.configurationSystemService.findOne().getDefaultPhone());
		result.setSuspicious(false);

		return result;
	}

	public Collection<Administrator> findAll() {
		Collection<Administrator> result;
		result = this.administratorRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Administrator findOne(final int administratorId) {
		Assert.isTrue(administratorId != 0);
		Administrator result;
		result = this.administratorRepository.findOne(administratorId);
		return result;
	}

	public Administrator save(final Administrator administrator) {

		Assert.notNull(administrator);
		final Administrator result;
		final Md5PasswordEncoder encoder;
		final String passwordHash;

		if (administrator.getId() == 0) {
			final String password = administrator.getUserAccount().getPassword();
			encoder = new Md5PasswordEncoder();
			passwordHash = encoder.encodePassword(password, null);
			administrator.getUserAccount().setPassword(passwordHash);
		}
		result = this.administratorRepository.save(administrator);

		Assert.notNull(result);

		return result;
	}

	public void delete(final Administrator administrator) {
		Assert.notNull(administrator);
		Assert.isTrue(administrator.getId() != 0);
		this.administratorRepository.delete(administrator);
	}

	// Other business methods------------------------------------------------------

	public Administrator findByPrincipal() {
		Administrator result;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.administratorRepository.findByUserAccountId(userAccount.getId());
		return result;
	}

	public void checkPrincipal() {

		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		final Collection<Authority> authorities = userAccount.getAuthorities();
		Assert.notNull(authorities);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMINISTRATOR);

		Assert.isTrue(authorities.contains(auth));
	}

	public Double[] findAvgMinMaxStddevOfTheNumOfApplicationsPerTrip() {
		Double[] result;
		result = this.administratorRepository.findAvgMinMaxStddevOfTheNumOfApplicationsPerTrip();
		Assert.notNull(result);
		return result;
	}

	public Double[] findAvgMinMaxStddevOfTheNumOfTripsPerManager() {
		Double[] result;
		result = this.administratorRepository.findAvgMinMaxStddevOfTheNumOfTripsPerManager();
		Assert.notNull(result);
		return result;
	}

	public Double[] findAvgMinMaxStddevOfThePriceOfTheTrips() {
		Double[] result;
		result = this.administratorRepository.findAvgMinMaxStddevOfThePriceOfTheTrips();
		Assert.notNull(result);
		return result;
	}

	public Double[] findAvgMinMaxStddevOfTheNumTripsPerRanger() {
		Double[] result;
		result = this.administratorRepository.findAvgMinMaxStddevOfTheNumTripsPerRanger();
		Assert.notNull(result);
		return result;
	}

	public Double findRatOfApplicationsPending() {
		Double result;
		result = this.administratorRepository.findRatOfApplicationsPending();
		return result;
	}

	public Double findRatioOfApplicationsDue() {
		Double result;
		result = this.administratorRepository.findRatioOfApplicationsDue();
		return result;
	}

	public Double findRatOfApplicationsAccepted() {
		Double result;
		result = this.administratorRepository.findRatOfApplicationsAccepted();
		return result;
	}

	public Double findRatOfApplicationsCancelled() {
		Double result;
		result = this.administratorRepository.findRatOfApplicationsCancelled();
		return result;
	}

	public Double findRatOfTheTripsCancelledvsTripsOrganised() {
		Double result;
		result = this.administratorRepository.findRatOfTheTripsCancelledvsTripsOrganised();
		return result;
	}

	public Collection<Trip> findTrips10porcentMoreApplicationsThanAvg() {
		Collection<Trip> result;
		result = this.administratorRepository.findTrips10porcentMoreApplicationsThanAvg();
		return result;
	}

	public Collection<String> titlesOfLegalText() {
		Collection<String> result;
		result = this.administratorRepository.titlesOfLegalText();
		return result;
	}

	public Collection<Integer> findNumOfReferencesOfLegalText() {
		Collection<Integer> result;
		result = this.administratorRepository.findNumOfReferencesOfLegalText();
		return result;
	}

	public Double[] findMinMaxAvgStddevOfTheNumOfNotesPerTrip() {
		Double[] result;
		result = this.administratorRepository.findMinMaxAvgStddevOfTheNumOfNotesPerTrip();
		Assert.notNull(result);
		return result;
	}

	public Double[] findMinMaxAvgStddevOfTheNumOfAuditRecordsPerTrip() {
		Double[] result;
		result = this.administratorRepository.findMinMaxAvgStddevOfTheNumOfAuditRecordsPerTrip();
		Assert.notNull(result);
		return result;
	}

	public Double findTheRatOfTripsWihoutAnAuditRecord() {
		Double result;
		result = this.administratorRepository.findTheRatOfTripsWihoutAnAuditRecord();
		return result;
	}

	public Double findTheRatOfRangersWhoHaveRegisteredCurricula() {
		Double result;
		result = this.administratorRepository.findTheRatOfRangersWhoHaveRegisteredCurricula();
		return result;
	}

	public Double findTheRatOfRangersWhoseCurrIsEndorsed() {
		Double result;
		result = this.administratorRepository.findTheRatOfRangersWhoseCurrIsEndorsed();
		return result;
	}

	public Double findTheRatOFSuspiciousManagers() {
		Double result;
		result = this.administratorRepository.findTheRatOFSuspiciousManagers();
		return result;
	}

	public Double findTheRatOFSuspiciousRangers() {
		Double result;
		result = this.administratorRepository.findTheRatOFSuspiciousRangers();
		return result;
	}

	public Boolean administratorIsSpam(final Administrator administrator) {
		Boolean result;
		result = false;

		result = this.actorService.actorIsSpam(administrator);
		if (result == true) {
			administrator.setSuspicious(result);
			return result;
		}

		return result;

	}
}
