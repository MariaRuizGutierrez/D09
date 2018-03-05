
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RangerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.MessageFolder;
import domain.Ranger;
import domain.SocialIdentity;
import domain.Trip;

@Service
@Transactional
public class RangerService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RangerRepository		RangerRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private MessageFolderService	messageFolderService;


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

		messagesFolders.addAll(this.messageFolderService.createDefaultFolders());

		authority.setAuthority("RANGER");
		userAccount.addAuthority(authority);
		result.setUserAccount(userAccount);
		result.setMessagesFolders(messagesFolders);
		result.setSocialIdentities(socialIdentities);
		result.setTrips(trips);

		return result;
	}
	public Collection<Ranger> findAll() {

		Collection<Ranger> result;
		result = this.RangerRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Ranger findOne(int rangerId) {

		Assert.isTrue(rangerId != 0);
		Ranger result;
		result = this.RangerRepository.findOne(rangerId);

		return result;
	}

	public Ranger save(Ranger ranger) {

		Assert.notNull(ranger);
		Ranger result;
		result = this.RangerRepository.save(ranger);

		Assert.notNull(result);

		return result;
	}

	public void delete(Ranger ranger) {

		Assert.notNull(ranger);
		Assert.isTrue(ranger.getId() != 0);
		this.RangerRepository.delete(ranger);

	}

	// Other business methods----------------------------------

	public Ranger findByPrincipal() {

		Ranger result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.RangerRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}
	public void checkPrincipal() {

		UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		Collection<Authority> authorities = userAccount.getAuthorities();
		Assert.notNull(authorities);

		Authority auth = new Authority();
		auth.setAuthority("RANGER");

		Assert.isTrue(authorities.contains(auth));
	}

}
