
package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Explorer;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ExplorerServiceTest extends AbstractTest {

	//Service under test----------------------------------------------------------	

	@Autowired
	private ExplorerService	explorerService;
	@Autowired
	private TripService		tripService;


	// Supporting services ----------------------------------------------------

	@Test
	public void testCreatePositive() {
		Explorer explorer;
		explorer = this.explorerService.create();
		Assert.notNull(explorer);
		Assert.notEmpty(explorer.getMessagesFolders());
	}

	@Test
	public void testSavePositive() {
		Explorer explorer;
		explorer = this.explorerService.create();

		explorer.setName("name explorer test");
		explorer.setSurname("surname explorer test");
		explorer.setEmail("emailExplorerTest@email.com");
		explorer.setPhone("313(987)7121");
		explorer.setAddress("address explorer test");

		explorer = this.explorerService.save(explorer);
		Assert.notNull(explorer.getId());

	}

	@Test
	public void testDelete() {
		Explorer explorer;
		explorer = this.explorerService.create();

		explorer.setName("name explorer test");
		explorer.setSurname("surname explorer test");
		explorer.setEmail("emailExplorerTest@email.com");
		explorer.setPhone("313(987)7121");
		explorer.setAddress("address explorer test");

		explorer = this.explorerService.save(explorer);
		this.explorerService.delete(explorer);

	}

	@Test
	public void testFindAll() {
		Collection<Explorer> explorers;
		explorers = this.explorerService.findAll();
		Assert.notEmpty(explorers);
		Assert.notNull(explorers);
	}

	@Test
	public void testFindOne() {
		Explorer explorer;
		explorer = this.explorerService.findOne(super.getEntityId("explorer1"));
		Assert.notNull(explorer);
	}

	@Test
	public void testFindExplorersByTripId() {
		Collection<Explorer> explorers;
		Trip trip;
		trip = this.tripService.findOne(super.getEntityId("trip1"));
		explorers = this.explorerService.findExplorersByTripId(trip.getId());
		Assert.notNull(explorers);
	}

	@Test
	public void findExplorersByContactEmergencyId() {
		Collection<Explorer> res;
		res = this.explorerService.findExplorersByContactEmergencyId(super.getEntityId("contactEmergency1"));
		Assert.notNull(res);
	}

}
