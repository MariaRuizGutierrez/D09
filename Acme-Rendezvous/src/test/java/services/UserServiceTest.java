
package services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class UserServiceTest extends AbstractTest {

	// Supporting services ----------------------------------------------------
	@Autowired
	private UserService		userService;
	@Autowired
	private QuestionService	questionService;

	@PersistenceContext
	EntityManager			entityManager;


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
}
