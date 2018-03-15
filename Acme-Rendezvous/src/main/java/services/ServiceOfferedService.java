
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ServiceOfferedRepository;
import domain.Manager;
import domain.Rendezvouse;
import domain.Request;
import domain.ServiceOffered;

@Service
@Transactional
public class ServiceOfferedService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ServiceOfferedRepository	serviceOfferedRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ManagerService				managerService;
	@Autowired
	private AdministratorService		administratorService;

	@Autowired
	private RequestService				requestService;


	// Constructors------------------------------------------------------------
	public ServiceOfferedService() {
		super();
	}

	// Simple CRUD methods-----------------------------------------------------

	//Create
	public ServiceOffered create() {
		ServiceOffered result;
		Collection<Rendezvouse> rendezvous;

		rendezvous = new ArrayList<Rendezvouse>();
		result = new ServiceOffered();
		result.setRendezvouses(rendezvous);
		result.setCancelled(false);

		return result;
	}
	//Save
	public ServiceOffered save(final ServiceOffered serviceoffered) {
		Assert.notNull(serviceoffered);
		ServiceOffered result;
		Manager principal;
		principal = this.managerService.findByPrincipal();
		result = new ServiceOffered();
		result = this.serviceOfferedRepository.save(serviceoffered);

		if (serviceoffered.getId() == 0)
			principal.getServicesOffered().add(result);
		Assert.notNull(result);
		return result;
	}

	//Delete
	public void delete(ServiceOffered serviceOffered) {

		Assert.notNull(serviceOffered);
		Assert.isTrue(serviceOffered.getId() != 0);

		Manager manager;
		Collection<Request> requests;
		Collection<Rendezvouse> rendezvouses;

		manager = this.managerService.findManagerByServiceOffered(serviceOffered.getId());
		requests = this.requestService.findServiceOfferedOfServiceOfferedId(serviceOffered.getId());
		rendezvouses = serviceOffered.getRendezvouses();

		manager.getServicesOffered().remove(serviceOffered);

		for (Request r : requests)
			this.requestService.delete(r);

		for (Rendezvouse re : rendezvouses)
			re.getServicesOffered().remove(serviceOffered);

		Assert.isTrue(serviceOffered.getRendezvouses().isEmpty());
		this.serviceOfferedRepository.delete(serviceOffered);

	}

	//Cancel
	public void cancel(final ServiceOffered serviceOffered) {
		Assert.notNull(serviceOffered);
		Assert.isTrue(serviceOffered.getId() != 0);
		this.administratorService.checkPrincipal();
		serviceOffered.setCancelled(true);
	}

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

	public void flush() {
		this.serviceOfferedRepository.flush();

	}
	//Other method

	public Collection<ServiceOffered> AllServiceNotCancelled() {
		Collection<ServiceOffered> result;
		result = this.serviceOfferedRepository.AllServiceNotCancelled();
		return result;
	}

	public Collection<ServiceOffered> AllServiceNotCancelledAveibleForRendezvouse(final int id) {
		Collection<ServiceOffered> result;
		result = this.serviceOfferedRepository.AllServiceNotCancelledAveibleForRendezvouse(id);

		return result;
	}

	public Collection<ServiceOffered> ServiceByCategoryName(final int id) {
		Collection<ServiceOffered> result;
		result = this.serviceOfferedRepository.ServiceByCategoryName(id);

		return result;
	}

}
