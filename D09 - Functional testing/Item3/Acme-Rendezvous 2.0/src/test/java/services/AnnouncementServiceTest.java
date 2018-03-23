
package services;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

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

	@PersistenceContext
	EntityManager		entityManager;


	// Test CreateAndSave ----------------------------------------------------------------------------------
	// Caso de uso 16.3: Create an announcement regarding one of the rendezvouses that he or she's created previously.
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
				"user1", "rendezvouse1", "title test", null, javax.validation.ConstraintViolationException.class
			}, {
				//Se crea un Announcement con el description en blanco
				"user1", "rendezvouse1", "title test", "", javax.validation.ConstraintViolationException.class
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
			this.announcementService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();

		}

		this.checkExceptions(expected, caught);
		this.unauthenticate();

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
	// Se comprueba que solo el Admin es el unico que puede eliminar un Announcement
	// Caso de uso 17.1: Remove an announcement that he or she thinks is inappropriate.
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

	// Test findAnnouncementByUserIdForRendezvousesAssits
	// Se comprueba el list que devuelve todos los announcements de las rendezvouses a la que un user ha asistido en orden descendiente cronologicamente
	// Caso de uso 16.5: Display a stream of announcements that have been posted to the rendezvouses that he or she's RSVPd. The announcements must be listed chronologically in descending order.
	@Test
	public void driverFindAnnouncementByUserIdForRendezvousesAssits() {
		final Object testingData[][] = {
			{
				//El user 1 comprueba si asiste al announcement2 que aparece la primera en la lista al ser ordenada cronologicamente en orden descendiente
				"user1", "announcement2", null
			}, {
				//El user 1 comprueba si asiste announcement1 pero no aparece la primera en la lista al ser ordenada cronologicamente en orden descendiente
				"user1", "announcement1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateFindAnnouncementByUserIdForRendezvousesAssits((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}
	private void templateFindAnnouncementByUserIdForRendezvousesAssits(final String username, final int announcementId, final Class<?> expected) {
		final Collection<Announcement> listAnnouncements;
		Announcement announcement;
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			listAnnouncements = this.announcementService.findAnnouncementByUserIdForRendezvousesAssits();
			announcement = this.announcementService.findOne(announcementId);
			Assert.isTrue(listAnnouncements.iterator().next().equals(announcement));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}

	// Test findAnnouncementByRendezvousId -------------------------------
	//Caso de uso 15.1: List the announcements that are associated with each rendezvous.
	@Test
	public void driverFindAnnouncementByRendezvousId() {
		final Object testingData[][] = {
			{
				//Se comprueba si el Rendezvouse1 tiene el announcement2 y si la lista de announcements que tiene el rendezvouse1 es de 2
				"announcement2", "rendezvouse1", 2, null
			}, {
				//Se comprueba si el Rendezvouse1 tiene el announcement5 (que no lo tiene) y si la lista de announcements que tiene el rendezvouse1 es de 2
				"announcement5", "rendezvouse1", 2, IllegalArgumentException.class
			}, {
				//Se comprueba si el Rendezvouse1 tiene el announcement2 y si la lista de announcements que tiene el rendezvouse1 es de 1 (que no lo es)
				"announcement2", "rendezvouse1", 1, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateFindAnnouncementByRendezvousId(super.getEntityId((String) testingData[i][0]), super.getEntityId((String) testingData[i][1]), (int) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	private void templateFindAnnouncementByRendezvousId(final int announcementId, final int rendezvouseId, final int size, final Class<?> expected) {
		final Collection<Announcement> listAnnouncements;
		Announcement announcement;
		Class<?> caught;

		caught = null;
		try {
			listAnnouncements = this.announcementService.findAnnouncementByRendezvousId(rendezvouseId);
			announcement = this.announcementService.findOne(announcementId);
			Assert.isTrue(listAnnouncements.contains(announcement));
			Assert.isTrue(listAnnouncements.size() == size);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
