
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ServiceOfferedRepository;
import domain.ServiceOffered;

@Service
@Transactional
public class ServiceOfferedService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ServiceOfferedRepository	serviceOfferedRepository;


	// Supporting services ----------------------------------------------------

	// Constructors------------------------------------------------------------
	public ServiceOfferedService() {
		super();
	}

	// Simple CRUD methods-----------------------------------------------------

	//Create

	//Save

	//Delete

	public Collection<ServiceOffered> findAll() {
		Collection<ServiceOffered> result;
		result = new ArrayList<ServiceOffered>(this.serviceOfferedRepository.findAll());
		Assert.notNull(result);
		return result;
	}

	public ServiceOffered findOne(final int serviceOfferedId) {
		ServiceOffered result;
		Assert.isTrue(serviceOfferedId != 0);
		result = this.serviceOfferedRepository.findOne(serviceOfferedId);
		return result;
	}
}
