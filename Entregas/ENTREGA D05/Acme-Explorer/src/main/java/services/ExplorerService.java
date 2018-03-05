
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ExplorerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.ApplicationFor;
import domain.ContactEmergency;
import domain.Explorer;
import domain.MessageFolder;
import domain.SocialIdentity;
import domain.Story;

@Service
@Transactional
public class ExplorerService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ExplorerRepository		explorerRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private MessageFolderService	messageFolderService;


	// Constructors -----------------------------------------------------------

	public ExplorerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Explorer create() {
		Explorer result;
		UserAccount userAccount;
		Authority authority;
		Collection<SocialIdentity> socialIdentities;
		Collection<MessageFolder> messagesFolders;
		Collection<Story> stories;
		Collection<ApplicationFor> applicationsFor;
		Collection<ContactEmergency> contactsEmergency;
		Collection<MessageFolder> defaultFolders;

		result = new Explorer();
		userAccount = new UserAccount();
		authority = new Authority();
		socialIdentities = new ArrayList<SocialIdentity>();
		messagesFolders = new ArrayList<MessageFolder>();
		stories = new ArrayList<Story>();
		applicationsFor = new ArrayList<ApplicationFor>();
		contactsEmergency = new ArrayList<ContactEmergency>();

		result.setSocialIdentities(socialIdentities);
		defaultFolders = this.messageFolderService.createDefaultFolders();
		messagesFolders.addAll(defaultFolders);
		result.setMessagesFolders(messagesFolders);
		authority.setAuthority(Authority.EXPLORER);
		userAccount.addAuthority(authority);
		result.setUserAccount(userAccount);
		result.setStories(stories);
		result.setApplicationsFor(applicationsFor);
		result.setContactsEmergency(contactsEmergency);

		return result;
	}

	public Collection<Explorer> findAll() {
		Collection<Explorer> result;

		Assert.notNull(this.explorerRepository);
		result = this.explorerRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Explorer findOne(final int explorerId) {
		Explorer result;

		result = this.explorerRepository.findOne(explorerId);
		Assert.notNull(result);

		return result;
	}

	public Explorer save(final Explorer explorer) {
		Explorer newExplorer;
		Assert.notNull(explorer);

		String password = explorer.getUserAccount().getPassword();
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		password = encoder.encodePassword(password, null);
		explorer.getUserAccount().setPassword(password);

		newExplorer = this.explorerRepository.save(explorer);
		//folderService.createDefaultFolders(newExplorer);
		return newExplorer;
	}

	public void delete(final Explorer explorer) {
		Assert.notNull(explorer);
		Assert.isTrue(explorer.getId() != 0);
		Assert.isTrue(this.explorerRepository.exists(explorer.getId()));

		this.explorerRepository.delete(explorer);
	}

	// Other business methods -------------------------------------------------

	public void checkPrincipal() {

		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		final Collection<Authority> authorities = userAccount.getAuthorities();
		Assert.notNull(authorities);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.EXPLORER);

		Assert.isTrue(authorities.contains(auth));
	}

	public Explorer findByPrincipal() {

		Explorer result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.explorerRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	public Explorer findByUserAccount(final UserAccount userAccount) {
		Explorer result;

		Assert.notNull(userAccount);
		result = this.explorerRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	//Para el checkPrincipalExplorer obtenemos el explorer.
	public Collection<Explorer> findExplorersByTripId(final int tripId) {
		Collection<Explorer> explorers;
		explorers = new ArrayList<Explorer>(this.explorerRepository.findExplorersByTripId(tripId));
		Assert.notNull(explorers);
		return explorers;
	}

	public Collection<Explorer> findExplorersByContactEmergencyId(final int contactEmergencyId) {
		Collection<Explorer> res;

		res = this.explorerRepository.findExplorersByContactEmergencyId(contactEmergencyId);
		Assert.notNull(res);

		return res;
	}
}
