
package services;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Explorer;
import domain.Finder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class FinderServiceTest extends AbstractTest {

	//Service under test----------------------------------------------------------

	@Autowired
	private FinderService	finderService;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ExplorerService	explorerService;

	@PersistenceContext
	private EntityManager	entityManager;


	// Test ------------------------------------------------------------------

	@Test
	public void testCreate() {
		Finder finder;

		finder = this.finderService.create();
		Assert.isTrue(finder != null);

	}

	@Test
	public void testSave() {
		Explorer explorer;

		//Lo hago desde explorer porque tengo puesto el create de finder en el create de explorer
		explorer = this.explorerService.create();
		explorer.setName("testFinder");
		explorer.setSurname("test finder surname");
		explorer.setEmail("test@test.com");
		explorer.setAddress("address test finder");
		explorer.setPhone("+34456454545");
		explorer.getUserAccount().setUsername("findertest1");
		explorer.getUserAccount().setPassword("findertest1");

		explorer = this.explorerService.save(explorer);
		this.entityManager.flush();

	}

	@Test
	public void testFindOne() {
		Finder finder;
		finder = this.finderService.findOne(super.getEntityId("finder1"));
		Assert.notNull(finder);
	}

	@Test
	public void testFindAll() {
		Collection<Finder> finders;
		finders = this.finderService.findAll();
		Assert.notNull(finders);
	}

}
