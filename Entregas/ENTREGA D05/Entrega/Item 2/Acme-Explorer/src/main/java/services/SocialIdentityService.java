
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SocialIdentityRepository;
import domain.SocialIdentity;

@Service
@Transactional
public class SocialIdentityService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private SocialIdentityRepository	socialIdentityRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------
	public SocialIdentityService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------

	public SocialIdentity create() {
		SocialIdentity socialIden;
		socialIden = new SocialIdentity();

		return socialIden;
	}

	public SocialIdentity findOne(int IdsocialIdentity) {
		Assert.isTrue(IdsocialIdentity != 0);
		SocialIdentity result;
		result = this.socialIdentityRepository.findOne(IdsocialIdentity);
		Assert.notNull(result);
		return result;
	}
	public Collection<SocialIdentity> findAll() {

		Collection<SocialIdentity> result;
		result = this.socialIdentityRepository.findAll();
		return result;

	}
	public SocialIdentity save(SocialIdentity socialIdentity) {
		Assert.notNull(socialIdentity);
		SocialIdentity result;
		result = this.socialIdentityRepository.save(socialIdentity);
		Assert.notNull(result);
		Assert.isTrue(result.getId() != 0);
		return result;

	}
	public void delete(SocialIdentity socialIdentity) {
		Assert.notNull(socialIdentity);
		Assert.notNull(this.socialIdentityRepository.findOne(socialIdentity.getId()));
		this.socialIdentityRepository.delete(socialIdentity);

	}

	// Other business methods -------------------------------------------------

}
