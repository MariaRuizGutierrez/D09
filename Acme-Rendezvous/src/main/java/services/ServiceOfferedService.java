
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
