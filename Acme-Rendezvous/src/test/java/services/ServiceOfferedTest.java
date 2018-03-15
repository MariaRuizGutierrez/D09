
package services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.ServiceOffered;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ServiceOfferedTest extends AbstractTest {

	// Supporting services ----------------------------------------------------
	@Autowired
	ServiceOfferedService	serviceOfferedService;

	@Autowired
	ManagerService			managerService;

	@PersistenceContext
	EntityManager			entityManager;


	//TEST CREATE--------------------------------------------------------------

	@Test
	public void driverCreate() {

		Object testingData[][] = {
			{
				//Se crea un Servicio correctamente
				"manager1", "name test", "description", "http://www.test.com", null
			}, {
				//Un user menor de edad crea un Rendezvouse para mayores de edad
				"manager1", "name test", "description", "http://www.test.com", null
			}, {
				//Se crea un Rendezvouse incorrectamente porque lo intenta crear un admin
				"admin", "name test", "description", "http://www.test.com", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	private void templateCreate(String username, String name, String description, String picture, final Class<?> expected) {

		ServiceOffered serviceOffered;
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			serviceOffered = this.serviceOfferedService.create();
			serviceOffered.setName(name);
			serviceOffered.setDescription(description);
			serviceOffered.setPicture(picture);
			//serviceOffered.setCancelled(cancelled);
			serviceOffered = this.serviceOfferedService.save(serviceOffered);
			this.serviceOfferedService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

		super.unauthenticate();
	}

	//TEST DELETE-----------------------------------------------------------

	@Test
	public void driverDelete() {
		final Object testingData[][] = {
			{
				//Se comprueba que no se puede eliminar el serviceOffered1 por el manager que la ha creado
				// ya que este ya ha sido requerido por una rendezvous.
				"manager1", "serviceOffered1", IllegalArgumentException.class
			}, {
				//Se elimina el serviceOffered1 por el admin (el cual no elimina, cancela)
				"admin", "serviceOffered1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDelete((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}

	private void templateDelete(String username, final int serviceOfferedId, final Class<?> expected) {
		ServiceOffered serviceOffered;
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			serviceOffered = this.serviceOfferedService.findOne(serviceOfferedId);
			this.serviceOfferedService.delete(serviceOffered);
			this.unauthenticate();
			this.serviceOfferedService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

	}

	//TEST CANCEL-----------------------------------------------------------

	@Test
	public void driverCancel() {
		final Object testingData[][] = {
			{
				//Se elimina el serviceOffered1 por el manager que la ha creado.
				"admin", "serviceOffered1", null
			}, {
				//Se elimina el serviceOffered1 por el admin (el cual no elimina, cancela)
				"manager1", "serviceOffered1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCancel((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}

	private void templateCancel(String username, final int serviceOfferedId, final Class<?> expected) {
		ServiceOffered serviceOffered;
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			serviceOffered = this.serviceOfferedService.findOne(serviceOfferedId);
			this.serviceOfferedService.cancel(serviceOffered);
			this.unauthenticate();
			this.serviceOfferedService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

	}
}
