
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
import domain.Administrator;
import domain.Announcement;
import domain.Comment;
import domain.Manager;
import domain.Rendezvouse;
import domain.ServiceOffered;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	// Supporting services ----------------------------------------------------

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private CommentService			commentService;

	@Autowired
	private RendezvouseService		rendezvousService;

	@Autowired
	private AnnouncementService		announcementService;

	@PersistenceContext
	EntityManager					entityManager;


	@Test
	public void driveLoginAdministrator() {

		final Object testingData[][] = {
			//admin está registrado
			{
				"admin", null
			}, {
				"adminNoRegistrado", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateLoginAdminsitrator((String) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	public void templateLoginAdminsitrator(final String username, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);
	}

	@Test
	public void driveEditNameAdministrator() {

		final Object testingData[][] = {
			//admin está registrado
			{
				"administrator", "admin", "adminPrueba", "surnamePrueba", null, null, "prueba@gmail.com", null
			}, {
				"administrator", "admin", "adminPrueba", "surnamePrueba", "c/prueba", null, "prueba@gmail.com", null
			}, {
				"administrator", "admin", "adminPrueba", "surnamePrueba", "c/prueba", "+34657896576", "prueba@gmail.com", null
			}, {
				"administrator", "admin", "", "surnamePrueba", null, null, "prueba@gmail.com", javax.validation.ConstraintViolationException.class
			}, {
				"administrator", "admin", null, "surnamePrueba", null, null, "prueba@gmail.com", javax.validation.ConstraintViolationException.class
			}, {
				"administrator", "admin", "adminPrueba", "surnamePrueba", null, null, "prueba@gmail.com", null
			}, {
				"administrator", "admin", "adminPrueba", "", null, null, "prueba@gmail.com", javax.validation.ConstraintViolationException.class
			}, {
				"administrator", "admin", "adminPrueba", null, null, null, "prueba@gmail.com", javax.validation.ConstraintViolationException.class
			}, {
				"administrator", "admin", "adminPrueba", null, null, null, "pruebagmail.com", javax.validation.ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEditAdministrator((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(Class<?>) testingData[i][7]);

	}

	public void templateEditAdministrator(final String entity, final String username, final String name, final String surname, final String postalAddress, final String phoneNumber, final String emailAddress, final Class<?> expected) {

		Class<?> caught;
		Administrator admin;

		caught = null;
		admin = this.administratorService.findOne(super.getEntityId(entity));

		try {
			super.authenticate(username);
			admin.setName(name);
			admin.setSurname(surname);
			admin.setPostalAddress(postalAddress);
			admin.setPhoneNumber(phoneNumber);
			admin.setEmailAddress(emailAddress);
			this.administratorService.save(admin);
			this.unauthenticate();
			this.administratorService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

	}

	@Test
	public void driveRemoveCommentAdministrator() {

		final Object testingData[][] = {
			//admin está registrado
			{
				"admin", "comment5", null
			}, {
				"user1", "comment5", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateRemoveCommentAdministrator((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}

	public void templateRemoveCommentAdministrator(final String username, final String commentId, final Class<?> expected) {

		Class<?> caught;
		Comment comment;

		caught = null;
		comment = this.commentService.findOne(super.getEntityId(commentId));

		try {
			super.authenticate(username);
			this.commentService.delete(comment);
			this.unauthenticate();
			this.commentService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

	}

	@Test
	public void driveRemoveRendezvousAdministrator() {

		final Object testingData[][] = {
			//admin está registrado
			{
				"admin", "rendezvouse1", null
			}, {
				"user2", "rendezvouse4", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateRemoveRendezvousAdministrator((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}

	public void templateRemoveRendezvousAdministrator(final String username, final String rendezvousId, final Class<?> expected) {

		Class<?> caught;
		Rendezvouse rendezvous;

		caught = null;
		rendezvous = this.rendezvousService.findOne(super.getEntityId(rendezvousId));

		try {
			super.authenticate(username);
			this.rendezvousService.delete(rendezvous);
			this.unauthenticate();
			this.rendezvousService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

	}

	@Test
	public void driveRemoveAnnouncementAdministrator() {

		final Object testingData[][] = {
			//admin está registrado
			{
				"admin", "announcement3", null
			}, {
				"user1", "announcement3", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateRemoveAnnouncementAdministrator((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}

	public void templateRemoveAnnouncementAdministrator(final String username, final String aunocementId, final Class<?> expected) {

		Class<?> caught;
		Announcement announcement;

		caught = null;
		announcement = this.announcementService.findOne(super.getEntityId(aunocementId));

		try {
			super.authenticate(username);
			this.announcementService.delete(announcement);
			this.unauthenticate();
			this.announcementService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

	}
	
	@Test
	public void driveFindAvgStddevOfTheNumOfRendezvouseCreatedPerUserAdministrator() {

		final Object testingData[][] = {
			//admin está registrado
			{
				"admin", 2, 2.2, 1.6, null
			}, {
				"user1", 2, 2.2, 1.6, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateFindAvgStddevOfTheNumOfRendezvouseCreatedPerUserAdministrator((String) testingData[i][0], (int) testingData[i][1], (double) testingData[i][2], (double) testingData[i][3], (Class<?>) testingData[i][4]);

	}
	
	public void templateFindAvgStddevOfTheNumOfRendezvouseCreatedPerUserAdministrator(final String username, int num, double num1, double num2, final Class<?> expected) {

		Class<?> caught;
		Double[] objetos;
		
		caught = null;
		

		try {
			super.authenticate(username);
			objetos = this.administratorService.findAvgStddevOfTheNumOfRendezvouseCreatedPerUser();
			Assert.isTrue(objetos.length == num);
			Assert.isTrue(objetos[0]==num1);
			Assert.isTrue(objetos[1]==num2);
			this.administratorService.flush();
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

	}
	
	@Test
	public void driveFindRatioUsersWithRendezvousesAndNotRendezvousesAdministrator() {

		final Object testingData[][] = {
			//admin está registrado
			{
				"admin", 0.0, null
			}, {
				"user1", 0.0, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateFindRatioUsersWithRendezvousesAndNotRendezvousesAdministrator((String) testingData[i][0], (double) testingData[i][1], (Class<?>) testingData[i][2]);

	}
	
	public void templateFindRatioUsersWithRendezvousesAndNotRendezvousesAdministrator(final String username, double num, final Class<?> expected) {

		Class<?> caught;
		Double result;
		
		caught = null;
		

		try {
			super.authenticate(username);
			result = this.administratorService.findRatioUsersWithRendezvousesAndNotRendezvouses();
			Assert.isTrue(result == num);
			this.administratorService.flush();
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

	}
	
	@Test
	public void driveFindAvgStddevOfTheNumOfAssistansPerRendezvouseAdministrator() {

		final Object testingData[][] = {
			//admin está registrado
			{
				"admin", 2, 2.2727, 1.7628, null
			}, {
				"user1", 2, 2.2727, 1.7628, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateFindAvgStddevOfTheNumOfAssistansPerRendezvouseAdministrator((String) testingData[i][0], (int) testingData[i][1], (double) testingData[i][2], (double) testingData[i][3], (Class<?>) testingData[i][4]);

	}
	
	public void templateFindAvgStddevOfTheNumOfAssistansPerRendezvouseAdministrator(final String username, int num, double num1, double num2, final Class<?> expected) {

		Class<?> caught;
		Double[] result;
		
		caught = null;
		

		try {
			super.authenticate(username);
			result = this.administratorService.findAvgStddevOfTheNumOfAssistansPerRendezvouse();
			Assert.isTrue(result.length == num);
			Assert.isTrue(result[0] == num1);
			Assert.isTrue(result[1] == num2);
			this.administratorService.flush();
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

	}
	
	@Test
	public void driveFindAvgStddevOfTheNumOfRendezvouseAssitedPerUserAdministrator() {

		final Object testingData[][] = {
			//admin está registrado
			{
				"admin", 2, 5.0, 1.4142, null
			}, {
				"user1", 2, 5.0, 1.4142, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateFindAvgStddevOfTheNumOfRendezvouseAssitedPerUserAdministrator((String) testingData[i][0], (int) testingData[i][1], (double) testingData[i][2], (double) testingData[i][3], (Class<?>) testingData[i][4]);

	}
	
	public void templateFindAvgStddevOfTheNumOfRendezvouseAssitedPerUserAdministrator(final String username, int num, double num1, double num2, final Class<?> expected) {

		Class<?> caught;
		Double[] result;
		
		caught = null;
		

		try {
			super.authenticate(username);
			result = this.administratorService.findAvgStddevOfTheNumOfRendezvouseAssitedPerUser();
			Assert.isTrue(result.length == num);
			Assert.isTrue(result[0] == num1);
			Assert.isTrue(result[1] == num2);
			this.administratorService.flush();
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

	}
	
	@Test
	public void drivefindTop10RendezvouseWithRSVPdAdministrator() {

		final Object testingData[][] = {
			//admin está registrado
			{
				"admin", 10, null
			}, {
				"user1", 10, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templatefindTop10RendezvouseWithRSVPdAdministrator((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);

	}
	
	public void templatefindTop10RendezvouseWithRSVPdAdministrator(final String username, int num, final Class<?> expected) {

		Class<?> caught;
		Collection<Rendezvouse> result;
		
		caught = null;
		

		try {
			super.authenticate(username);
			result = this.administratorService.findTop10RendezvouseWithRSVPd();
			Assert.isTrue(result.size() == num);
			this.administratorService.flush();
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

	}
	
	@Test
	public void drivefindAvgStddevOfTheNumOfAnnouncementsPerRendezvousAdministrator() {

		final Object testingData[][] = {
			//admin está registrado
			{
				"admin", 2, 0.4545, 0.6556, null
			}, {
				"user1", 2, 0.4545, 0.6556, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templatefindAvgStddevOfTheNumOfAnnouncementsPerRendezvousAdministrator((String) testingData[i][0], (int) testingData[i][1], (double) testingData[i][2], (double) testingData[i][3], (Class<?>) testingData[i][4]);

	}
	
	public void templatefindAvgStddevOfTheNumOfAnnouncementsPerRendezvousAdministrator(final String username, int num, double num1, double num2, final Class<?> expected) {

		Class<?> caught;
		Double[] result;
		
		caught = null;
		

		try {
			super.authenticate(username);
			result = this.administratorService.findAvgStddevOfTheNumOfAnnouncementsPerRendezvous();
			Assert.isTrue(result.length == num);
			Assert.isTrue(result[0] == num1);
			Assert.isTrue(result[1] == num2);
			this.administratorService.flush();
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

	}
	
	@Test
	public void drivefindRendezvousesWithMore75PerCentAdministrator() {

		final Object testingData[][] = {
			//admin está registrado
			{
				"admin", 4, null
			}, {
				"user1", 4, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templatefindRendezvousesWithMore75PerCentAdministrator((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);

	}
	
	public void templatefindRendezvousesWithMore75PerCentAdministrator(final String username, int num, final Class<?> expected) {

		Class<?> caught;
		Collection<Rendezvouse> result;
		
		caught = null;
		

		try {
			super.authenticate(username);
			result = this.administratorService.findRendezvousesWithMore75PerCent();
			Assert.isTrue(result.size() == num);
			this.administratorService.flush();
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

	}
	
	@Test
	public void drivefindRendezvousesWithAreLinkedAdministrator() {

		final Object testingData[][] = {
			//admin está registrado
			{
				"admin", 1, null
			}, {
				"user1", 1, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templatefindRendezvousesWithAreLinkedAdministrator((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);

	}
	
	public void templatefindRendezvousesWithAreLinkedAdministrator(final String username, int num, final Class<?> expected) {

		Class<?> caught;
		Collection<Rendezvouse> result;
		
		caught = null;
		

		try {
			super.authenticate(username);
			result = this.administratorService.findRendezvousesWithAreLinked();
			Assert.isTrue(result.size() == num);
			this.administratorService.flush();
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

	}
	
	
	@Test
	public void driveFindAvgStddevOfTheNumOfQuestionsPerRendezvousAdministrator() {

		final Object testingData[][] = {
			//admin está registrado
			{
				"admin", 2, 0.45454545454545453, 0.7820295697311479, null
			}, {
				"user1", 2, 0.45454545454545453, 0.7820295697311479, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateFindAvgStddevOfTheNumOfQuestionsPerRendezvousAdministrator((String) testingData[i][0], (int) testingData[i][1], (double) testingData[i][2], (double) testingData[i][3], (Class<?>) testingData[i][4]);

	}
	
	public void templateFindAvgStddevOfTheNumOfQuestionsPerRendezvousAdministrator(final String username, int num, double num1, double num2, final Class<?> expected) {

		Class<?> caught;
		Double[] result;
		
		caught = null;
		

		try {
			super.authenticate(username);
			result = this.administratorService.findAvgStddevOfTheNumOfQuestionsPerRendezvous();
			Assert.isTrue(result.length == num);
			Assert.isTrue(result[0] == num1);
			Assert.isTrue(result[1] == num2);
			this.administratorService.flush();
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

	}
	
	@Test
	public void drivefindAvgStddevOfTheNumOfAnswerToQuestionsPerRendezvousAdministrator() {

		final Object testingData[][] = {
			//admin está registrado
			{
				"admin", 2, 0.45454545454545453, 0.9875254992000193, null
			}, {
				"user1", 2, 0.45454545454545453, 0.9875254992000193, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templatefindAvgStddevOfTheNumOfAnswerToQuestionsPerRendezvousAdministrator((String) testingData[i][0], (int) testingData[i][1], (double) testingData[i][2], (double) testingData[i][3], (Class<?>) testingData[i][4]);

	}
	
	public void templatefindAvgStddevOfTheNumOfAnswerToQuestionsPerRendezvousAdministrator(final String username, int num, double num1, double num2, final Class<?> expected) {

		Class<?> caught;
		Double[] result;
		
		caught = null;
		

		try {
			super.authenticate(username);
			result = this.administratorService.findAvgStddevOfTheNumOfAnswerToQuestionsPerRendezvous();
			Assert.isTrue(result.length == num);
			Assert.isTrue(result[0] == num1);
			Assert.isTrue(result[1] == num2);
			this.administratorService.flush();
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

	}
	
	@Test
	public void drivefindAvgStddevOfTheNumOfRepliesPerCommentAdministrator() {

		final Object testingData[][] = {
			//admin está registrado
			{
				"admin", 2, 0.2, 0.4, null
			}, {
				"user1", 2, 0.2, 0.4, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templatefindAvgStddevOfTheNumOfRepliesPerCommentAdministrator((String) testingData[i][0], (int) testingData[i][1], (double) testingData[i][2], (double) testingData[i][3], (Class<?>) testingData[i][4]);

	}
	
	public void templatefindAvgStddevOfTheNumOfRepliesPerCommentAdministrator(final String username, int num, double num1, double num2, final Class<?> expected) {

		Class<?> caught;
		Double[] result;
		
		caught = null;
		

		try {
			super.authenticate(username);
			result = this.administratorService.findAvgStddevOfTheNumOfRepliesPerComment();
			Assert.isTrue(result.length == num);
			Assert.isTrue(result[0] == num1);
			Assert.isTrue(result[1] == num2);
			this.administratorService.flush();
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

	}
	
	@Test
	public void drivebestSellingServicesAdministrator() {

		final Object testingData[][] = {
			//admin está registrado
			{
				"admin", 1, null
			}, {
				"user1", 1, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templatebestSellingServicesAdministrator((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);

	}
	
	public void templatebestSellingServicesAdministrator(final String username, int num, final Class<?> expected) {

		Class<?> caught;
		Collection<ServiceOffered> result;
		
		caught = null;
		

		try {
			super.authenticate(username);
			result = this.administratorService.bestSellingServices();
			Assert.isTrue(result.size() == num);
			this.administratorService.flush();
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

	}
	
	@Test
	public void drivemanagerProvidesMoreServicesThanAverageAdministrator() {

		final Object testingData[][] = {
			//admin está registrado
			{
				"admin", 1, null
			}, {
				"user1", 1, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templatemanagerProvidesMoreServicesThanAverageAdministrator((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);

	}
	
	public void templatemanagerProvidesMoreServicesThanAverageAdministrator(final String username, int num, final Class<?> expected) {

		Class<?> caught;
		Collection<Manager> result;
		
		caught = null;
		

		try {
			super.authenticate(username);
			result = this.administratorService.managerProvidesMoreServicesThanAverage();
			Assert.isTrue(result.size() == num);
			this.administratorService.flush();
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

	}
	
	@Test
	public void drivemanagersWhohaveMoreServicesCancelledAdministrator() {

		final Object testingData[][] = {
			//admin está registrado
			{
				"admin", 2, null
			}, {
				"user1", 2, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateManagersWhohaveMoreServicesCancelledAdministrator((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);

	}
	
	public void templateManagersWhohaveMoreServicesCancelledAdministrator(final String username, int num, final Class<?> expected) {

		Class<?> caught;
		Collection<Manager> result;
		
		caught = null;
		

		try {
			super.authenticate(username);
			result = this.administratorService.managersWhohaveMoreServicesCancelled();
			Assert.isTrue(result.size() == num);
			this.administratorService.flush();
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

	}
	
	@Test
	public void drivefindAvgNumOfCategoriesPerRendezvousAdministrator() {

		final Object testingData[][] = {
			//admin está registrado
			{
				"admin", 2.3636, null
			}, {
				"user1", 2.3636, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateFindAvgNumOfCategoriesPerRendezvousAdministrator((String) testingData[i][0], (double) testingData[i][1], (Class<?>) testingData[i][2]);

	}
	
	public void templateFindAvgNumOfCategoriesPerRendezvousAdministrator(final String username, double num, final Class<?> expected) {

		Class<?> caught;
		Double result;
		
		caught = null;
		

		try {
			super.authenticate(username);
			result = this.administratorService.findAvgNumOfCategoriesPerRendezvous();
			Assert.isTrue(result == num);
			this.administratorService.flush();
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

	}
	
	
	@Test
	public void driveFindAvgNumOfServicesPerCategoriesAdministrator() {

		final Object testingData[][] = {
			//admin está registrado
			{
				"admin", 1.0, null
			}, {
				"user1", 1.0, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateFindAvgNumOfServicesPerCategoriesAdministrator((String) testingData[i][0], (double) testingData[i][1], (Class<?>) testingData[i][2]);

	}
	
	public void templateFindAvgNumOfServicesPerCategoriesAdministrator(final String username, double num, final Class<?> expected) {

		Class<?> caught;
		Double result;
		
		caught = null;
		

		try {
			super.authenticate(username);
			result = this.administratorService.findAvgNumOfServicesPerCategories();
			Assert.isTrue(result == num);
			this.administratorService.flush();
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

	}
	
	@Test
	public void driveFindAvgMinMaxStddevOfTheNumOfRequestedPerRendezvouseAdministrator() {

		final Object testingData[][] = {
			//admin está registrado
			{
				"admin", 4, 2.3636, 0.0, 4.0, 1.4316, null
			}, {
				"user1", 4, 2.3636, 0.0, 4.0, 1.4316, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateFindAvgMinMaxStddevOfTheNumOfRequestedPerRendezvouseAdministrator((String) testingData[i][0], (int) testingData[i][1], (double) testingData[i][2], 
				 (double) testingData[i][3], (double) testingData[i][4], (double) testingData[i][5], (Class<?>) testingData[i][6]);

	}
	
	public void templateFindAvgMinMaxStddevOfTheNumOfRequestedPerRendezvouseAdministrator(final String username, int num, double num1, double num2, 
		double num3, double num4, final Class<?> expected) {

		Class<?> caught;
		Double[] result;
		
		caught = null;
		

		try {
			super.authenticate(username);
			result = this.administratorService.findAvgMinMaxStddevOfTheNumOfRequestedPerRendezvouse();
			Assert.isTrue(result.length == num);
			Assert.isTrue(result[0] == num1);
			Assert.isTrue(result[1] == num2);
			Assert.isTrue(result[2] == num3);
			Assert.isTrue(result[3] == num4);
			this.administratorService.flush();
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

	}
	
	@Test
	public void driveFindTop5ServicesAdministrator() {

		final Object testingData[][] = {
			//admin está registrado
			{
				"admin", 4, null
			}, {
				"user1", 4, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateFindTop5ServicesAdministrator((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);

	}
	
	public void templateFindTop5ServicesAdministrator(final String username, int num, final Class<?> expected) {

		Class<?> caught;
		Collection<Rendezvouse> result;
		
		caught = null;
		

		try {
			super.authenticate(username);
			result = this.administratorService.findTop5Services();
			Assert.isTrue(result.size() == num);
			this.administratorService.flush();
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

	}

	
	
}
