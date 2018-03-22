
package services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import security.UserAccount;
import utilities.AbstractTest;
import domain.Manager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ManagerServiceTest extends AbstractTest {

	// Supporting services ----------------------------------------------------
	@Autowired
	private ManagerService	managerService;
	@Autowired
	private QuestionService	questionService;

	@PersistenceContext
	EntityManager			entityManager;


	// Test LoginManager ------------------------------------------------------
	@Test
	public void driverLoginManager() {

		final Object testingData[][] = {
			{
				//login usuario un usuario
				"manager1", null

			}, {
				//login manager no registrado
				"juanya", IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateLogin((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}
	private void templateLogin(final String managername, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(managername);
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
	// Caso de uso 3.1: Register to the system as a manager.
	@Test
	public void driverCreateAndSave() {

		final Object testingData[][] = {
			{
				//Crear Manager correctamente
				"managertest1", "passwordtest1", "123--WW", "miguel", "ternero", "6676886", "Email@email.com", null

			}, {
				//Crear Manager con el vat incorrecto
				"managertest2", "passwordtest2", "123--WW  ", "miguel", "ternero", "6676886", "Email@email.com", javax.validation.ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateAndSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(Class<?>) testingData[i][7]);
	}
	private void templateCreateAndSave(final String username, final String password, final String vat, final String name, final String surname, final String phone, final String mail, final Class<?> expected) {
		Class<?> caught;
		Manager manager;
		UserAccount userAccount;

		caught = null;
		try {
			manager = this.managerService.create();
			manager.setVat(vat);
			manager.setName(name);
			manager.setSurname(surname);
			manager.setPhoneNumber(phone);
			manager.setEmailAddress(mail);
			userAccount = manager.getUserAccount();
			userAccount.setUsername(username);
			userAccount.setPassword(password);
			manager.setUserAccount(userAccount);
			manager = this.managerService.save(manager);
			this.managerService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();

		}

		this.checkExceptions(expected, caught);

	}

	@Test
	public void driverEditManager() {

		final Object testingData[][] = {
			{
				//editar tus datos
				"manager1", "miguel", "ternero", "6676886", "Email@email.com", null

			}, {
				//editar tus phone en blanco
				"manager1", "miguel", "ternero", "", "Email@email.com", null

			}, {
				//editar email incorrecto
				"manager1", "miguel", "ternero", "6676886", "no tengo email", javax.validation.ConstraintViolationException.class

			}, {
				//usuario edita y deja sin email
				"manager1", "miguel", "ternero", "", "", javax.validation.ConstraintViolationException.class

			}, {
				//editar usuario y poner apellido en blanco
				"manager1", "miguel", "", "6676886", "Email@email.com", javax.validation.ConstraintViolationException.class
			}, {
				//editar usuario y poner nombre en blanco
				"manager1", "", "terneor", "6676886", "Email@email.com", javax.validation.ConstraintViolationException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateEditManager((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);
	}
	private void templateEditManager(final String managername, final String name, final String surname, final String phone, final String mail, final Class<?> expected) {
		Class<?> caught;
		Manager manager;
		manager = this.managerService.findOne(this.getEntityId("manager1"));

		caught = null;
		try {
			super.authenticate(managername);
			manager.setName(name);
			manager.setSurname(surname);
			manager.setPhoneNumber(phone);
			manager.setEmailAddress(mail);
			this.unauthenticate();
			this.managerService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();

		}

		this.checkExceptions(expected, caught);
	}
}
