
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Announcement;
import domain.Rendezvouse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AnnouncementServiceTest extends AbstractTest {

	// Supporting services ----------------------------------------------------

	@Autowired
	AnnouncementService	announcementService;

	@Autowired
	UserService			userService;

	@Autowired
	RendezvouseService	rendezvouseService;


	// Test CreateAndSave ----------------------------------------------------------------------------------

	@Test
	public void driverCreateAndSave() {
		final Object testingData[][] = {
			{
				//Se crea un Announcement correctamente
				"user1", "rendezvouse1", "title test", "description test", null
			}, {
				//Se crea un Announcement para un User que no le pertenece ese Rendezvous (Hacking get)
				"user5", "rendezvouse1", "title test", "description test", IllegalArgumentException.class
			}, {
				//Se crea un Announcement con el title en null
				"user1", "rendezvouse1", null, "description test", javax.validation.ConstraintViolationException.class
			}, {
				//Se crea un Announcement con el title en blanco
				"user1", "rendezvouse1", "", "description test", javax.validation.ConstraintViolationException.class
			}, {
				//Se crea un Announcement con el description en null
				"user5", "rendezvouse1", "title test", null, IllegalArgumentException.class
			}, {
				//Se crea un Announcement con el description en blanco
				"user5", "rendezvouse1", "title test", "", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreateAndSave((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}
	private void templateCreateAndSave(final String username, final int rendezvouseId, final String title, final String description, final Class<?> expected) {
		final Rendezvouse rendezvouseForAnnouncement;
		Announcement announcement;
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			rendezvouseForAnnouncement = this.rendezvouseService.findOne(rendezvouseId);
			announcement = this.announcementService.create(rendezvouseForAnnouncement);
			announcement.setTitle(title);
			announcement.setDescription(description);
			announcement = this.announcementService.save(announcement);
			this.unauthenticate();
			this.announcementService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	// Test Edit ----------------------------------------------------------------------------------

	@Test
	public void driverEdit() {
		final Object testingData[][] = {
			{
				//Se edita el announcement1 por el user que la ha creado
				"user1", "announcement1", null
			}, {
				//Se edita el announcement1 por el user que NO la ha creado (Hacking get)
				"user5", "announcement1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateEdit((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}
	private void templateEdit(final String username, final int announcementId, final Class<?> expected) {
		Announcement announcement;
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			announcement = this.announcementService.findOne(announcementId);
			announcement.setTitle("Editing test title");
			announcement = this.announcementService.save(announcement);
			this.unauthenticate();
			this.announcementService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	// Test Delete ----------------------------------------------------------------------------------

	@Test
	public void driverDelete() {
		final Object testingData[][] = {
			{
				//Se elimina el announcement1 por el user que la ha creado (ningun user puede eliminar un announcement)
				"user1", "announcement1", IllegalArgumentException.class
			}, {
				//Se elimina el announcement1 por el user que NO la ha creado(Hacking get) (ningun user puede eliminar un announcement)
				"user5", "announcement1", IllegalArgumentException.class
			}, {
				//Se elimina el announcement1 por un admin cualquiera (Cualquier admin puede eliminar cualquier announcement)
				"admin", "announcement1", null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDelete((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}
	private void templateDelete(final String username, final int announcementId, final Class<?> expected) {
		Announcement announcement;
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			announcement = this.announcementService.findOne(announcementId);
			this.announcementService.delete(announcement);
			this.unauthenticate();
			this.announcementService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

}
