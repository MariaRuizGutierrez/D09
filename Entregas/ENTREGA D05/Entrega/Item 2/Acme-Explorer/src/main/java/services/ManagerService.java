
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
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
	private ManagerRepository		managerRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private MessageFolderService	messageFolderService;


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

		messagesFolders.addAll(this.messageFolderService.createDefaultFolders());

		authority.setAuthority(Authority.MANAGER);
		userAccount.addAuthority(authority);
		result.setUserAccount(userAccount);
		result.setMessagesFolders(messagesFolders);
		result.setSocialIdentities(socialIdentities);
		result.setTrips(trips);
		result.setApplicationsFor(applicationsFor);
		result.setSuspicious(false);

		return result;

	}
	public Collection<Manager> findAll() {

		Collection<Manager> result;
		result = this.managerRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Manager findOne(int managerId) {

		Assert.isTrue(managerId != 0);
		Manager result;
		result = this.managerRepository.findOne(managerId);

		return result;
	}

	public Manager save(Manager manager) {

		Assert.notNull(manager);
		Manager result;
		result = this.managerRepository.save(manager);

		Assert.notNull(result);

		return result;
	}

	public void delete(Manager manager) {

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

		UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		Collection<Authority> authorities = userAccount.getAuthorities();
		Assert.notNull(authorities);

		Authority auth = new Authority();
		auth.setAuthority(Authority.MANAGER);

		Assert.isTrue(authorities.contains(auth));
	}

}
