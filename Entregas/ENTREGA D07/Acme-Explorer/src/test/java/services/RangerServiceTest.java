
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
import domain.Ranger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class RangerServiceTest extends AbstractTest {

	// Service under test ---------------------------------

	@Autowired
	private RangerService	rangerService;


	// Test -----------------------------------------------

	@Test
	public void testCreate() {

		Ranger ranger;
		ranger = this.rangerService.create();
		Assert.notNull(ranger);

	}

	@Test
	public void testFindAll() {

		Collection<Ranger> rangers;
		rangers = this.rangerService.findAll();
		Assert.notNull(rangers);
		Assert.notEmpty(rangers);
	}

	@Test
	public void testFindOne() {

		Collection<Ranger> rangers;
		Ranger ranger;

		rangers = this.rangerService.findAll();
		Assert.notNull(rangers);
		Assert.notEmpty(rangers);

		ranger = this.rangerService.findOne(rangers.iterator().next().getId());

		Assert.notNull(ranger);
	}

	@Test
	public void testSave() {

		Ranger ranger;
		Ranger rangerSaved;

		ranger = this.rangerService.create();

		ranger.setName("name 1");
		ranger.setSurname("surname 1");
		ranger.setEmail("ranger1@gmail.com");

		rangerSaved = this.rangerService.save(ranger);
		Assert.notNull(rangerSaved);

	}

	@Test
	public void testDelete() {

		Ranger ranger;
		ranger = this.rangerService.create();

		ranger.setName("ranger 1");
		ranger.setSurname("surname");
		ranger.setEmail("email@gmail.com");
		ranger.setPhone("31333");
		ranger.setAddress("address");

		ranger = this.rangerService.save(ranger);
		Assert.notNull(ranger.getId());

		this.rangerService.delete(ranger);

	}

}
