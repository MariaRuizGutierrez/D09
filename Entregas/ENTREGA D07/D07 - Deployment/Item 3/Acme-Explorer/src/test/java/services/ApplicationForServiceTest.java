
package services;

import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.ApplicationFor;
import domain.Manager;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ApplicationForServiceTest extends AbstractTest {

	//Service under test----------------------------------------------------------	

	@Autowired
	private ApplicationForService	applicationForService;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ManagerService			managerService;

	@Autowired
	private TripService				tripService;


	@Test
	public void testCreatePositive() {
		super.authenticate("explorer1");

		Trip trip;

		trip = this.tripService.findOne(super.getEntityId("trip1"));
		ApplicationFor applicationFor;
		applicationFor = this.applicationForService.create(trip);
		Assert.notNull(applicationFor);
		Assert.isTrue(applicationFor.getStatus() == "PENDING");
		super.unauthenticate();
	}
	@Test
	public void testSavePositive() {
		super.authenticate("explorer1");
		ApplicationFor applicationFor;
		Trip trip;

		trip = this.tripService.findOne(super.getEntityId("trip1"));
		applicationFor = this.applicationForService.create(trip);

		applicationFor.setMoment(new Date());
		applicationFor.setStatus("DUE");
		applicationFor.setCreditCard(this.applicationForService.findOne(super.getEntityId("applicationFor1")).getCreditCard());

		Assert.notNull(applicationFor.getId());
		Assert.notNull(applicationFor.getExplorer());
		Assert.notNull(applicationFor.getCreditCard());

		applicationFor = this.applicationForService.save(applicationFor);
		super.unauthenticate();

	}

	@Test
	public void testChangeStatus() {
		super.authenticate("manager1");
		Collection<ApplicationFor> applications;
		ApplicationFor applicationFor1 = null;

		applications = this.applicationForService.findAll();
		for (ApplicationFor a : applications)
			if (a.getStatus().equals("PENDIG")) {
				applicationFor1 = a;
				this.applicationForService.changeStatus(a, "DUE");
				Assert.isTrue(applicationFor1.getStatus().equals("DUE"));
			}

		super.unauthenticate();
	}
	@Test
	public void testFindAllPositive() {
		Collection<ApplicationFor> applicationFors;
		applicationFors = this.applicationForService.findAll();
		Assert.notEmpty(applicationFors);
	}

	@Test
	public void testFindOne() {
		ApplicationFor applicationFor;
		applicationFor = this.applicationForService.findOne(super.getEntityId("applicationFor2"));
		Assert.notNull(applicationFor);
	}

	@Test
	public void testFindAllByManagerId() {
		super.authenticate("manager1");
		Manager managerPrincipal;
		Collection<ApplicationFor> result;

		managerPrincipal = this.managerService.findByPrincipal();
		result = this.applicationForService.findAllByManagerId(managerPrincipal.getId());
		Assert.notNull(result);

		super.unauthenticate();
	}

	@Test
	public void testDelete() {
		ApplicationFor applicationFor;

		applicationFor = this.applicationForService.findOne(super.getEntityId("applicationFor3"));

		this.applicationForService.delete(applicationFor);
	}
}
