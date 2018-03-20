
package services;

import java.text.SimpleDateFormat;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.UserAccount;
import utilities.AbstractTest;
import domain.ServiceOffered;
import domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class UserServiceTest extends AbstractTest {

	// Supporting services ----------------------------------------------------
	@Autowired
	private UserService				userService;
	@Autowired
	private QuestionService			questionService;
	@Autowired
	private ServiceOfferedService	serviceOfferedService;

	@PersistenceContext
	EntityManager					entityManager;


	@Test
	public void driverLoginUser() {

		final Object testingData[][] = {
			{
				//login usuario un usuario
				"user1", null

			}, {
				//login user no registrado
				"juanya", IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateLogin((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}
	private void templateLogin(final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			this.unauthenticate();
			this.questionService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();

		}

		this.checkExceptions(expected, caught);

	}
	//Test Create-------------------------------------------------
	// Caso de uso 4.1
	@Test
	public void driverCreateAndSave() {

		final Object testingData[][] = {
			{
				//Crear user correctamente
				"usertest1", "passwordtest1", "miguel", "ternero", "6676886", "Email@email.com", "1996/02/24", null

			}, {
				//Crear user sin fecha correcta
				"usertest2", "passwordtest2", "miguel", "ternero", "6676886", "Email@email.com", "", java.text.ParseException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateAndSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(Class<?>) testingData[i][7]);
	}
	private void templateCreateAndSave(final String username, final String password, final String name, final String surname, final String phone, final String mail, final String birt, final Class<?> expected) {
		Class<?> caught;
		User user;
		UserAccount userAccount;

		caught = null;
		try {
			user = this.userService.create();
			user.setName(name);
			user.setSurname(surname);
			user.setBirthDate((new SimpleDateFormat("yyyy/MM/dd")).parse(birt));
			user.setPhoneNumber(phone);
			user.setEmailAddress(mail);
			userAccount = user.getUserAccount();
			userAccount.setUsername(username);
			userAccount.setPassword(password);
			user.setUserAccount(userAccount);
			user = this.userService.save(user);
			this.userService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();

		}

		this.checkExceptions(expected, caught);

	}

	@Test
	public void driverEditUser() {

		final Object testingData[][] = {
			{
				//editar tus datos
				"user1", "miguel", "ternero", "6676886", "Email@email.com", null

			}, {
				//editar tus phone en blanco
				"user1", "miguel", "ternero", "", "Email@email.com", null

			}, {
				//editar email incorrecto
				"user1", "miguel", "ternero", "6676886", "no tengo email", ConstraintViolationException.class

			}, {
				//usuario edita y deja sin email
				"user1", "miguel", "ternero", "", "", ConstraintViolationException.class

			}, {
				//editar usuario y poner apellido en blanco
				"user1", "miguel", "", "6676886", "Email@email.com", ConstraintViolationException.class
			}, {
				//editar usuario y poner nombre en blanco
				"user1", "", "terneor", "6676886", "Email@email.com", ConstraintViolationException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateEditUser((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);
	}
	private void templateEditUser(final String username, final String name, final String surname, final String phone, final String mail, final Class<?> expected) {
		Class<?> caught;
		User user;
		user = this.userService.findOne(this.getEntityId("user1"));

		caught = null;
		try {
			super.authenticate(username);
			user.setName(name);
			user.setSurname(surname);
			user.setPhoneNumber(phone);
			user.setEmailAddress(mail);
			this.unauthenticate();
			this.questionService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();

		}

		this.checkExceptions(expected, caught);

	}
	//caso uso 4.2
	@Test
	public void driverListServiceAvaible() {

		final Object testingData[][] = {
			{
				//lista de servicios disponibles
				"user1", "serviceOffered2", null

			}, {
				//el servicio no esta disponible
				"user1", "serviceOffered1", IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateListServiceAvaible((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}
	private void templateListServiceAvaible(final String username, int Idserviceoffered, final Class<?> expected) {
		Class<?> caught;
		Collection<ServiceOffered> services;
		caught = null;
		try {
			super.authenticate(username);
			services = this.serviceOfferedService.AllServiceNotCancelled();
			Assert.isTrue(services.contains(this.serviceOfferedService.findOne(Idserviceoffered)));
			this.unauthenticate();
			this.questionService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();

		}

		this.checkExceptions(expected, caught);

	}

}
