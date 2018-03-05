
package security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;

@Service
@Transactional
public class UserAccountService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private UserAccountRepository	userAccountRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public UserAccountService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public UserAccount findByActor(final Actor actor) {
		Assert.notNull(actor);

		UserAccount result;

		result = this.userAccountRepository.findByActorId(actor.getId());

		return result;
	}

	public UserAccount save(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		UserAccount result;

		result = this.userAccountRepository.save(userAccount);

		Assert.isTrue(result.getId() != 0);

		return result;
	}

	public void delete(final UserAccount userAccount) {
		Assert.isTrue(userAccount.getId() != 0);

		this.userAccountRepository.delete(userAccount);
	}

	// Other business methods -------------------------------------------------

}
