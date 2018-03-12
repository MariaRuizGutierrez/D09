
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.ConfigurationSystem;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ConfigurationSystemServiceTest extends AbstractTest {

	// Supporting services ----------------------------------------------------

	@Autowired
	ConfigurationSystemService	configurationSystemService;


	// Test Edit ----------------------------------------------------------------------------------

	@Test
	public void driverEdit() {
		final Object testingData[][] = {
			{
				//Se edita el configurationSystem por un admin
				"admin", "name1", "https://tinyurl.com/adventure-meetup", "hello", "hola", null
			}, {
				//Se crea el configurationSystem con un nombre vacío
				"admin", "", "https://tinyurl.com/adventure-meetup", "hello", "hola", javax.validation.ConstraintViolationException.class
			}, {
				//Se crea el configurationSystem con un banner que no está en forma URL
				"admin", "name1", "hola", "hello", "hola", javax.validation.ConstraintViolationException.class
			}, {
				//Se crea el configuration para un usuario que no le pertenece editar el configurationSystem (Hacking get)
				"user1", "name1", "https://tinyurl.com/adventure-meetup", "hello", "hola", javax.validation.ConstraintViolationException.class
			}, {
				//Se crea el configuration con los dos mensajes vacíos
				"user1", "name1", "https://tinyurl.com/adventure-meetup", "", "", javax.validation.ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateEdit((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);
	}
	private void templateEdit(final String username, String name, String banner, String englishWelcomeMessage, String spanishWelcomeMessage, final Class<?> expected) {
		ConfigurationSystem configurationSystem;
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			configurationSystem = this.configurationSystemService.findOne();
			configurationSystem.setEnglishWelcomeMessage(englishWelcomeMessage);
			configurationSystem.setSpanishWelcomeMessage(spanishWelcomeMessage);
			configurationSystem.setName(name);
			configurationSystem.setBanner(banner);
			configurationSystem = this.configurationSystemService.save(configurationSystem);
			this.unauthenticate();
			this.configurationSystemService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}
}
