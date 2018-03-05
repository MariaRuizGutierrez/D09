
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ApplicationForRepository;
import domain.ApplicationFor;
import domain.CreditCard;
import domain.Explorer;
import domain.Manager;
import domain.Trip;

@Service
@Transactional
public class ApplicationForService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ApplicationForRepository	applicationForRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ExplorerService				explorerService;
	@Autowired
	private ManagerService				managerService;
	@Autowired
	private TripService					tripService;


	// Constructors -----------------------------------------------------------

	public ApplicationForService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public ApplicationFor create() {
		ApplicationFor result;
		String status;
		Date moment;
		Collection<String> comments;
		Explorer explorerPrincipal;

		result = new ApplicationFor();
		moment = new Date();
		status = "PENDING";
		comments = new ArrayList<String>();
		explorerPrincipal = this.explorerService.findByPrincipal();
		Assert.notNull(explorerPrincipal);

		result.setMoment(moment);
		result.setStatus(status);
		result.setComments(comments);
		result.setExplorer(explorerPrincipal);

		return result;
	}
	public Collection<ApplicationFor> findAll() {
		Collection<ApplicationFor> result;

		Assert.notNull(this.applicationForRepository);
		result = this.applicationForRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public ApplicationFor findOne(final int applicationForId) {
		ApplicationFor result;

		result = this.applicationForRepository.findOne(applicationForId);
		Assert.notNull(result);

		return result;
	}

	public ApplicationFor save(final ApplicationFor applicationFor) {
		final Date moment;
		Assert.notNull(applicationFor);

		ApplicationFor result;

		if (applicationFor.getStatus().equals("DUE") && this.checkCreditCard(applicationFor.getCreditCard()))
			applicationFor.setStatus("ACCEPTED");

		if (applicationFor.getId() == 0) {
			//Solo se cambia el moment la primera vez que se crea, si se actualiza no se cambia su moment
			moment = new Date(System.currentTimeMillis() - 1000);
			applicationFor.setMoment(moment);
		}
		result = this.applicationForRepository.save(applicationFor);

		return result;
	}
	public void delete(final ApplicationFor applicationFor) {
		Assert.notNull(applicationFor != null);
		Assert.isTrue(applicationFor.getId() != 0);
		Assert.isTrue(this.applicationForRepository.exists(applicationFor.getId()));
		Collection<Trip> trips;

		trips = this.tripService.findAllTripsByApplicationForId(applicationFor.getId());
		for (final Trip t : trips)
			t.getApplicationsFor().remove(applicationFor);
		this.applicationForRepository.delete(applicationFor);
	}

	// Other business methods------------------------------------------------------
	private boolean checkCreditCard(final CreditCard creditCard) {
		boolean res;
		Calendar calendar;
		int actualYear;

		res = false;
		calendar = new GregorianCalendar();
		actualYear = calendar.get(Calendar.YEAR);
		actualYear = actualYear % 100;

		if (Integer.parseInt(creditCard.getExpirationYear()) > actualYear)
			res = true;
		else if (Integer.parseInt(creditCard.getExpirationYear()) == actualYear && Integer.parseInt(creditCard.getExpirationMonth()) >= calendar.get(Calendar.MONTH))
			res = true;

		return res;
	}
	public ApplicationFor changeStatus(final ApplicationFor applicationFor, final String newStatus) {
		Manager managerPrincipal;

		Assert.notNull(applicationFor);
		Assert.notNull(newStatus);
		managerPrincipal = this.managerService.findByPrincipal();
		Assert.isTrue(managerPrincipal.getApplicationsFor().contains(applicationFor));
		Assert.isTrue(applicationFor.getStatus().equals("PENDING"));
		Assert.isTrue((newStatus.equals("REJECTED")) || (newStatus.equals("DUE")));
		applicationFor.setStatus(newStatus);

		return applicationFor;
	}

	public Collection<ApplicationFor> findAllByManagerId(final int managerId) {
		Collection<ApplicationFor> result;
		Manager managerPrincipal;

		managerPrincipal = this.managerService.findByPrincipal();
		Assert.notNull(managerPrincipal);
		result = this.applicationForRepository.findAllByManagerId(managerPrincipal.getId());
		Assert.notNull(result);

		return result;

	}
}
