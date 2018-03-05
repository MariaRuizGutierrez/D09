
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
import domain.Actor;
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
	@Autowired
	private MessageService				messageService;
	@Autowired
	private ConfigurationSystemService	configurationSystemService;


	// Constructors -----------------------------------------------------------

	public ApplicationForService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public ApplicationFor create(final Trip trip) {
		ApplicationFor result;
		String status;
		Date moment;
		Collection<String> comments;
		Explorer explorerPrincipal;
		CreditCard creditCard;
		Manager manager;

		manager = trip.getManager();
		//Trip trip;
		//trip = this.tripService.findOne(tripId);

		creditCard = new CreditCard();
		creditCard.setBrandName("");
		creditCard.setHolderName("");
		creditCard.setExpirationMonth("");
		creditCard.setExpirationYear("");
		creditCard.setCvv(0);
		creditCard.setNumber("");

		result = new ApplicationFor();
		moment = new Date();
		status = "PENDING";
		comments = new ArrayList<String>();
		explorerPrincipal = this.explorerService.findByPrincipal();
		Assert.notNull(explorerPrincipal);
		result.setMoment(moment);
		result.setManager(manager);
		result.setStatus(status);
		result.setComments(comments);
		result.setExplorer(explorerPrincipal);
		result.setTrip(trip);
		result.setCreditCard(creditCard);

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

		if (applicationFor.getId() == 0) {
			//Solo se cambia el moment la primera vez que se crea, si se actualiza no se cambia su moment
			moment = new Date(System.currentTimeMillis() - 1000);
			applicationFor.setMoment(moment);
		}
		if (applicationFor.getCreditCard() == null)
			result = this.applicationForRepository.save(applicationFor);
		else

			result = this.applicationForRepository.save(applicationFor);
		if (applicationFor.getStatus().equals("PENDING") == true)
			this.messageService.messageForNotificationToStatusPending(applicationFor);
		else if (applicationFor.getStatus().equals("REJECTED") == true)
			this.messageService.messageForNotificationToStatusRejected(applicationFor);
		else if (applicationFor.getStatus().equals("DUE") == true)
			this.messageService.messageForNotificationToStatusDue(applicationFor);
		else if (applicationFor.getStatus().equals("ACCEPTED") == true)
			this.messageService.messageForNotificationToStatusAccepted(applicationFor);
		else
			this.messageService.messageForNotificationToStatusCancelled(applicationFor);

		return result;
	}
	public ApplicationFor saveReasonWhy(final ApplicationFor applicationFor) {
		final Date moment;
		Assert.notNull(applicationFor);

		ApplicationFor result;
		String reply;
		final int leng = applicationFor.getReasonWhy().trim().length();
		reply = applicationFor.getReasonWhy().trim();

		if (reply.equals("") && (leng == 0) && (applicationFor.getStatus().equals("REJECTED")))
			Assert.isTrue(4 == 3, "Status cannot be blank");

		if (applicationFor.getId() == 0) {
			//Solo se cambia el moment la primera vez que se crea, si se actualiza no se cambia su moment
			moment = new Date(System.currentTimeMillis() - 1000);
			applicationFor.setMoment(moment);
		}
		if (applicationFor.getCreditCard() == null)
			result = this.applicationForRepository.save(applicationFor);
		else

			result = this.applicationForRepository.save(applicationFor);
		if (applicationFor.getStatus().equals("PENDING") == true)
			this.messageService.messageForNotificationToStatusPending(applicationFor);
		else if (applicationFor.getStatus().equals("REJECTED") == true)
			this.messageService.messageForNotificationToStatusRejected(applicationFor);

		else if (applicationFor.getStatus().equals("DUE") == true)
			this.messageService.messageForNotificationToStatusDue(applicationFor);
		else if (applicationFor.getStatus().equals("ACCEPTED") == true)
			this.messageService.messageForNotificationToStatusAccepted(applicationFor);
		else
			this.messageService.messageForNotificationToStatusCancelled(applicationFor);

		return result;
	}
	public ApplicationFor enter(final ApplicationFor applicationFor) {
		Assert.notNull(applicationFor);
		Assert.isTrue(applicationFor.getStatus().equals("DUE"));
		ApplicationFor result;

		if (this.checkCreditCard(applicationFor.getCreditCard())) {
			applicationFor.setStatus("ACCEPTED");
			applicationFor.setReasonWhy("");
		} else {
			applicationFor.setStatus("REJECTED");
			applicationFor.setReasonWhy("Error");
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

	public void cancel(final ApplicationFor applicationFor) {
		Trip trip;
		Date date;
		trip = applicationFor.getTrip();
		date = new Date();
		Assert.isTrue(trip.getStartDate().after(date));
		Assert.notNull(applicationFor != null);
		Assert.isTrue(applicationFor.getId() != 0);
		Assert.isTrue(applicationFor.getStatus().equals("ACCEPTED"));
		Assert.isTrue(this.applicationForRepository.exists(applicationFor.getId()));
		applicationFor.setStatus("CANCELLED");
	}

	// Other business methods------------------------------------------------------
	public boolean checkCreditCard(final CreditCard creditCard) {
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

	public Collection<ApplicationFor> checkDuplicatedApply(final int explorerId, final int tripId) {
		//Si la lista esta vacia significa que no la contiene
		Collection<ApplicationFor> result;

		result = this.applicationForRepository.checkDuplicatedApply(explorerId, tripId);

		return result;
	}

	public Boolean applicationForContainsSpam(final Actor actor) {
		Collection<ApplicationFor> applicationForFromActor;
		Collection<String> words;
		Collection<String> spamWords;
		Boolean result;

		applicationForFromActor = this.applicationForRepository.applicationsForOfActor(actor.getId());
		result = false;
		words = new ArrayList<String>();
		spamWords = this.configurationSystemService.spamWord();

		for (final ApplicationFor application : applicationForFromActor) {
			words.add(application.getReasonWhy().toLowerCase());
			for (final String c : application.getComments())
				words.add(c.toLowerCase());
		}

		for (final String word : words)
			for (final String spam : spamWords)
				if (word.indexOf(spam) > -1) {
					result = true;
					break;
				}

		return result;
	}
	public boolean moreThanThirtyDays(final Date startdate) {
		boolean res = false;
		final Date dNow = new Date(System.currentTimeMillis());
		final long uno = dNow.getTime();
		final long dos = startdate.getTime();
		final double dias = ((dos - uno) * 1.0 / 86400000);
		if (dias > 30)
			res = true;
		return res;
	}
}
