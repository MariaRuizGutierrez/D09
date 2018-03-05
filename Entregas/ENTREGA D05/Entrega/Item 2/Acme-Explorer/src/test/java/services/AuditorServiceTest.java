
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
import domain.Auditor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class AuditorServiceTest extends AbstractTest {

	// Service under test ---------------------------------
	@Autowired
	private AuditorService	auditorService;


	// Tests ----------------------------------------------

	@Test
	public void testCreate() {
		Auditor auditor;

		auditor = this.auditorService.create();

		Assert.notNull(auditor);
	}

	@Test
	public void testSave() {
		Auditor auditor;

		auditor = this.auditorService.create();

		auditor.setName("name");
		auditor.setSurname("surname");
		auditor.setEmail("email@gmail.com");
		auditor.setPhone("31333");
		auditor.setAddress("address");

		auditor = this.auditorService.save(auditor);
		Assert.notNull(auditor.getId());

	}
	@Test
	public void testFindAll() {
		Collection<Auditor> auditors;

		auditors = this.auditorService.findAll();

		Assert.notEmpty(auditors);
		Assert.notNull(auditors);
	}

	@Test
	public void testDelete() {
		Auditor auditor;

		auditor = this.auditorService.create();

		auditor.setName("name2");
		auditor.setSurname("surname2");
		auditor.setEmail("email2@gmail.com");
		auditor.setPhone("31333222");
		auditor.setAddress("address2");

		auditor = this.auditorService.save(auditor);
		Assert.notNull(auditor.getId());
		this.auditorService.delete(auditor);

	}

	@Test
	public void testFindOne() {
		Auditor auditor;
		auditor = this.auditorService.findOne(super.getEntityId("auditor1"));
		Assert.notNull(auditor);
	}
}
