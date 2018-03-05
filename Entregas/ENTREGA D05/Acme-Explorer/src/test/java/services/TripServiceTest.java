
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.ApplicationFor;
import domain.AuditRecord;
import domain.Auditor;
import domain.Explorer;
import domain.Manager;
import domain.Note;
import domain.Stage;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class TripServiceTest extends AbstractTest {

	//Service under test----------------------------------------------------------
	@Autowired
	private TripService		tripService;

	//	// Supporting services ----------------------------------------------------

	@Autowired
	private ManagerService	managerService;

	@Autowired
	private ExplorerService	explorerService;

	@Autowired
	private AuditorService	auditorService;


	@Test
	public void testCreate() {
		this.authenticate("manager1");
		Trip result;
		Manager manager;
		manager = this.managerService.findByPrincipal();
		result = this.tripService.create(manager);
		Assert.notNull(result);
	}

	@Test
	public void testFindAll() {
		Collection<Trip> result = this.tripService.findAll();
		Assert.notEmpty(result);
	}

	@Test
	public void testSave() {
		this.authenticate("manager1");
		Trip trip;
		Manager manager;

		String title;
		String description;
		Collection<String> requerimentsExplorers;
		Boolean cancelled;
		double price;

		Date startDate;
		Date finishDate;
		Calendar calendar1;
		Calendar calendar2;

		calendar1 = new GregorianCalendar();
		calendar1.set(2018, 0, 31, 12, 5, 0);
		startDate = calendar1.getTime();

		calendar2 = new GregorianCalendar();
		calendar2.set(2015, 0, 31, 12, 5, 0);
		finishDate = calendar2.getTime();

		manager = this.managerService.findByPrincipal();

		trip = this.tripService.create(manager);
		title = "trip1";
		description = "trip de test";
		requerimentsExplorers = new ArrayList<String>();
		cancelled = false;
		price = 400.;

		trip.setTitle(title);
		trip.setDescription(description);
		trip.setRequirementsExplorers(requerimentsExplorers);
		trip.setStartDate(startDate);
		trip.setFinishDate(finishDate);
		trip.setCancelled(cancelled);
		trip.setPrice(price);

		this.tripService.save(trip);

		this.authenticate(null);
	}

	@Test
	public void testFindOne() {
		Trip trip;

		trip = this.tripService.findOne(super.getEntityId("trip1"));
		Assert.notNull(trip);
	}

	@Test
	public void testDelete() {
		this.authenticate("manager1");

		Trip trip;
		Manager manager;
		Collection<ApplicationFor> applicationFor;
		Collection<AuditRecord> auditRecord;
		Collection<Note> notes;
		Collection<Stage> stages;

		String title;
		String description;
		Collection<String> requerimentsExplorers;
		Boolean cancelled;

		double price;

		Date startDate;
		Date finishDate;
		Calendar calendar1;
		Calendar calendar2;

		calendar1 = new GregorianCalendar();
		calendar1.set(2018, 0, 31, 12, 5, 0);
		startDate = calendar1.getTime();

		calendar2 = new GregorianCalendar();
		calendar2.set(2015, 0, 31, 12, 5, 0);
		finishDate = calendar2.getTime();

		manager = this.managerService.findOne(super.getEntityId("manager1"));
		applicationFor = new ArrayList<ApplicationFor>();
		auditRecord = new ArrayList<AuditRecord>();
		notes = new ArrayList<Note>();
		stages = new ArrayList<Stage>();

		trip = this.tripService.create(manager);
		manager.getTrips().contains(trip);
		title = "trip1";
		description = "trip de test";
		requerimentsExplorers = new ArrayList<String>();
		cancelled = false;
		price = 400.;

		trip.setTitle(title);
		trip.setDescription(description);
		trip.setRequirementsExplorers(requerimentsExplorers);
		trip.setStartDate(startDate);
		trip.setFinishDate(finishDate);
		trip.setCancelled(cancelled);
		trip.setPrice(price);
		trip.setApplicationsFor(applicationFor);
		trip.setAuditRecords(auditRecord);
		trip.setNotes(notes);
		trip.setStages(stages);

		trip = this.tripService.save(trip);

		this.tripService.delete(trip);
		Assert.isNull(this.tripService.findOne(trip.getId()));
		this.authenticate(null);
	}

	// Other business test methods -------------------------------------------------
	@Test
	public void testFindAllTripsNoAuthenticate() {
		Collection<Trip> trips;
		trips = new ArrayList<Trip>(this.tripService.findAllTripsNoAuthenticate());
		Assert.notNull(trips);
	}

	@Test
	public void testFindAllTripsPublishedNotStarted() {
		this.authenticate("manager1");
		Collection<Trip> trips;
		trips = new ArrayList<Trip>(this.tripService.findAllTripsPublishedNotStarted());
		Assert.notNull(trips);
		this.authenticate(null);
	}

	@Test
	public void testFindTripsWhitStatusAccepted() {
		this.authenticate("explorer1");
		Collection<Trip> trips;
		trips = new ArrayList<Trip>(this.tripService.findTripsWhitStatusAcceptedNotStarted());
		Assert.notNull(trips);
		this.authenticate(null);
	}

	@Test
	public void testFindOneToEdit() {
		this.authenticate("manager1");
		Trip trip;
		Trip tripEdit;
		trip = this.tripService.findOne(super.getEntityId("trip1"));
		trip.setDescription("Nueva descripción");
		tripEdit = this.tripService.findOneToEdit(trip.getId());
		Assert.notNull(tripEdit);
		this.authenticate(null);
	}

	@Test
	public void testFindOneToCancelManager() {
		this.authenticate("manager1");
		Trip trip;

		trip = this.tripService.findOne(super.getEntityId("trip2"));
		trip.setCancelled(true);
		trip.setReasonWhy("Aforo sobrepasa el limite");
		this.tripService.findOneToCancelManager(trip.getId());
		Assert.notNull(trip);
		this.authenticate(null);
	}

	@Test
	public void testFindOneToCancelExplorer() {
		this.authenticate("explorer3");
		Trip trip;
		Trip tripEdit;
		trip = this.tripService.findOne(super.getEntityId("trip3"));
		trip.setCancelled(true);
		trip.setReasonWhy("No hay fondos");
		tripEdit = this.tripService.findOneToCancelExplorer(trip.getId());
		Assert.notNull(tripEdit);
		this.authenticate(null);
	}

	@Test
	public void testFindAllTripsApplyByExplorerId() {
		Collection<Trip> trips;
		Explorer explorer;
		explorer = this.explorerService.findOne(super.getEntityId("explorer1"));
		trips = new ArrayList<Trip>(this.tripService.findAllTripsApplyByExplorerId(explorer.getId()));
		Assert.notNull(trips);
	}

	@Test
	public void testFindByAuditorId() {
		Collection<Trip> trips;
		Auditor auditor;
		auditor = this.auditorService.findOne(super.getEntityId("auditor1"));
		trips = new ArrayList<Trip>(this.tripService.findByAuditorId(auditor.getId()));
		Assert.notNull(trips);
	}

	@Test
	public void testFindAllTripsNotPublished() {
		Collection<Trip> trips;
		trips = new ArrayList<>(this.tripService.findAllTripsNotPublished());
		Assert.notNull(trips);
	}

	@Test
	public void testSearchingForTrips() {
		Collection<Trip> trips;
		String search;
		search = "description";
		trips = new ArrayList<>(this.tripService.searchingForTrips(search));
		Assert.notNull(trips);
	}

	@Test
	public void testFindPrice() {
		Trip trip;
		trip = this.tripService.findOne(super.getEntityId("trip1"));
		this.tripService.findPrice(trip.getId());
	}
	@Test
	public void testSetPriceOfTrip() {
		Trip trip;
		trip = this.tripService.findOne(super.getEntityId("trip1"));

		this.tripService.setPriceOfTrip(trip);
		Assert.isTrue(trip.getPrice() >= 0.0);

	}

	//	@Test
	//	public void testSetPriceOfAllTrips() {
	//		this.tripService.setPriceOfAllTrips();
	//	}

	@Test
	public void testGeneratedTicker() {
		String ticker;
		ticker = this.tripService.generatedTicker();
		Assert.notNull(ticker);
	}

}
