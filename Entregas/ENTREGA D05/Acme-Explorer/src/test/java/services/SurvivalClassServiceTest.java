
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Administrator;
import domain.Explorer;
import domain.GPS;
import domain.Manager;
import domain.SurvivalClass;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SurvivalClassServiceTest extends AbstractTest {

	// Service under test ---------------------------------

	@Autowired
	private SurvivalClassService	survivalClassService;

	@Autowired
	private ManagerService			managerService;

	@Autowired
	private TripService				tripService;

	@Autowired
	private AdministratorService	adminsitratorService;


	// Tests ----------------------------------------------

	@Test
	public void testCreate() {
		this.authenticate("manager1");
		SurvivalClass result;
		Administrator administrator;

		administrator = this.adminsitratorService.findByPrincipal();

		result = this.survivalClassService.create(administrator);

		Assert.notNull(result);
	}

	@Test
	public void testFindAll() {

		this.authenticate("manager1");

		Collection<SurvivalClass> result;

		result = this.survivalClassService.findAll();

		Assert.notNull(result);
		Assert.notEmpty(result);

	}

	@Test
	public void testFindOne() {

		Collection<SurvivalClass> survivalClasses;
		SurvivalClass result;
		int idSurvivalClass;

		survivalClasses = this.survivalClassService.findAll();
		idSurvivalClass = survivalClasses.iterator().next().getId();
		result = this.survivalClassService.findOne(idSurvivalClass);

		Assert.notNull(result);

	}

	@Test
	public void testSave() {

		this.authenticate("manager1");

		SurvivalClass result;
		Manager manager;
		Trip trip;
		GPS location;
		SurvivalClass resultSaved;
		Administrator administrator;

		administrator = this.adminsitratorService.findByPrincipal();

		result = this.survivalClassService.create(administrator);

		manager = this.managerService.findByPrincipal();
		trip = this.tripService.findOne(super.getEntityId("trip1"));

		location = new GPS();
		location.setLatitude(22.4);
		location.setLongitude(45.7);
		location.setName("coordinate test");

		result.setTitle("title 1");
		result.setDescription("description 1");
		result.setLocation(location);
		result.setManager(manager);
		result.setTrip(trip);

		resultSaved = this.survivalClassService.save(result);

		Assert.notNull(resultSaved);

	}

	@Test
	public void testDelete() {

		this.authenticate("manager1");

		SurvivalClass result;
		Collection<Explorer> explorers;

		result = this.survivalClassService.findOne(super.getEntityId("survivalClass1"));
		explorers = result.getExplorers();

		explorers.removeAll(explorers);

		this.survivalClassService.delete(result);

		Assert.isTrue(!this.survivalClassService.findAll().contains(result));

	}
}
