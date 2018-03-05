
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
import domain.Manager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ManagerServiceTest extends AbstractTest {

	// Service under test ---------------------------------
	@Autowired
	private ManagerService	managerService;


	// Tests ----------------------------------------------

	@Test
	public void testCreate() {

		Manager manager;
		manager = this.managerService.create();

		Assert.notNull(manager);
	}

	@Test
	public void testFindAll() {
		Collection<Manager> managers;
		managers = this.managerService.findAll();
		Assert.notEmpty(managers);
		Assert.notNull(managers);

	}

	@Test
	public void testFindOne() {
		Collection<Manager> managers;
		int idManager;
		Manager manager;

		managers = this.managerService.findAll();

		idManager = managers.iterator().next().getId();

		manager = this.managerService.findOne(idManager);

		Assert.notNull(manager);

	}

	@Test
	public void testSave() {

		Manager manager;
		Manager managerSaved;

		manager = this.managerService.create();

		manager.setName("name 1");
		manager.setSurname("manager 1");
		manager.setEmail("manager1@gmail.com");

		managerSaved = this.managerService.save(manager);

		Assert.notNull(managerSaved);

	}

	@Test
	public void testDelete() {

		Manager manager;
		manager = this.managerService.create();

		manager.setName("manager1");
		manager.setSurname("surname");
		manager.setEmail("email@gmail.com");
		manager.setPhone("31333");
		manager.setAddress("address");

		manager = this.managerService.save(manager);
		Assert.notNull(manager.getId());

		this.managerService.delete(manager);

	}

}
