
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ManagerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.ApplicationFor;
import domain.Manager;
import domain.MessageFolder;
import domain.SocialIdentity;
import domain.Trip;

@Service
@Transactional
public class ManagerService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ManagerRepository			managerRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private MessageFolderService		messageFolderService;

	@Autowired
	private ActorService				actorService;

	@Autowired
	private SurvivalClassService		survivalClassService;

	@Autowired
	private ConfigurationSystemService	configurationSystemService;


	// Constructors-------------------------------------------------------

	public ManagerService() {
		super();
	}

	// Simple CRUD methods------------------------------------------------

	public Manager create() {

		Manager result;

		UserAccount userAccount;
		Authority authority;
		Collection<SocialIdentity> socialIdentities;
		Collection<MessageFolder> messagesFolders;
		Collection<Trip> trips;
		Collection<ApplicationFor> applicationsFor;

		result = new Manager();
		userAccount = new UserAccount();
		authority = new Authority();
		socialIdentities = new ArrayList<>();
		messagesFolders = new ArrayList<>();
		trips = new ArrayList<>();
		applicationsFor = new ArrayList<>();

		messagesFolders.addAll(this.messageFolderService.createDefaultFoldersForRegister(result));

		authority.setAuthority(Authority.MANAGER);
		userAccount.setActivated(true);
		userAccount.addAuthority(authority);
		result.setUserAccount(userAccount);
		result.setMessagesFolders(messagesFolders);
		result.setSocialIdentities(socialIdentities);
		result.setTrips(trips);
		result.setApplicationsFor(applicationsFor);
		result.setSuspicious(false);
		result.setPhone(this.configurationSystemService.findOne().getDefaultPhone());

		return result;

	}
	public Collection<Manager> findAll() {

		Collection<Manager> result;
		result = this.managerRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Manager findOne(final int managerId) {

		Assert.isTrue(managerId != 0);
		Manager result;
		result = this.managerRepository.findOne(managerId);

		return result;
	}

	public Manager save(final Manager manager) {

		Assert.notNull(manager);
		final Manager result;
		final Md5PasswordEncoder encoder;
		final String passwordHash;

		encoder = new Md5PasswordEncoder();
		passwordHash = encoder.encodePassword(manager.getUserAccount().getPassword(), null);
		manager.getUserAccount().setPassword(passwordHash);
		result = this.managerRepository.save(manager);

		Assert.notNull(result);

		return result;
	}

	public void delete(final Manager manager) {

		Assert.notNull(manager);
		Assert.isTrue(manager.getId() != 0);
		this.managerRepository.delete(manager);

	}

	// Other business methods----------------------------------

	public Manager findByPrincipal() {

		Manager result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.managerRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	public void checkPrincipal() {

		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		final Collection<Authority> authorities = userAccount.getAuthorities();
		Assert.notNull(authorities);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.MANAGER);

		Assert.isTrue(authorities.contains(auth));
	}

	public Boolean managerIsSpam(final Manager manager) {
		Boolean result;
		result = false;

		result = this.actorService.actorIsSpam(manager);
		if (result == true) {
			manager.setSuspicious(result);
			return result;
		}
		result = this.survivalClassService.survivalClassContainsSpam(manager);
		if (result == true) {
			manager.setSuspicious(result);
			return result;

		}
		return result;
	}

}
