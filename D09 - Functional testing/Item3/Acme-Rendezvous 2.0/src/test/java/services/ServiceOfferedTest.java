
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
	// Caso de uso 5.2: Manage his or her services, which includes listing them, creating them, updating them, and deleting them as long as they are not required by any rendezvouses.
	@Test
	public void driverCreate() {

		final Object testingData[][] = {
			{
				//Se crea un Servicio correctamente
				"manager1", "name test", "description", "http://www.test.com", null

			}, {
				//Se crea un Servicio incorrectamente porque lo intenta crear un admin
				"admin", "name test", "description", "http://www.test.com", IllegalArgumentException.class

			}, {
				//Se crea un Servicio con una descripción vacía
				"manager1", "name test", "", "http://www.test.com", javax.validation.ConstraintViolationException.class
			}, {
				//Se crea un Servicio con una URL incorrecta y el nombre en vaío
				"manager1", "", "description", "noEsUnaURL", javax.validation.ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	private void templateCreate(final String username, final String name, final String description, final String picture, final Class<?> expected) {

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
	//Caso de uso 5.2: Manage his or her services, which includes listing them, creating them, updating them, and deleting them as long as they are not required by any rendezvouses.
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
			}, {

				//Se elimina correctamente
				"manager4", "serviceOffered5", null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDelete((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}

	private void templateDelete(final String username, final int serviceOfferedId, final Class<?> expected) {
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
	//Caso de uso 6.1: Cancel a service that he or she finds inappropriate. Such services cannot be re-quested for any rendezvous.
	@Test
	public void driverCancel() {
		final Object testingData[][] = {
			{
				//Se cancela el serviceOffered1
				"admin", "serviceOffered1", null
			}, {
				//Se cancela el serviceOffered1 por el admin (el cual no elimina, cancela)
				"manager1", "serviceOffered1", IllegalArgumentException.class

			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCancel((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}

	private void templateCancel(final String username, final int serviceOfferedId, final Class<?> expected) {
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

	// Test listEdit ----------------------------------------------------------------------------------
	// Se listan todos los servicios creados por el manager logueado y de ellos se coge la pasada por parametro para cambiarles los valores
	//Caso de uso 5.2: Manage his or her services, which includes listing them, creating them, updating them, and deleting them as long as they are not required by any rendezvouses.
	@Test
	public void driverListEdit() {
		final Object testingData[][] = {
			{
				//Se edita un servicio incorrectamente ya que ya está asignado a una rendezvous
				"manager1", "name test", "description", "http://www.test.com", "serviceOffered1", IllegalArgumentException.class
			}, {
				//Se edita un servicio correctamente 
				"manager4", "name test", "description", "http://www.test.com", "serviceOffered5", null
			}, {
				//Editando un servicio que no es tuyo
				"manager1", "name test", "description", "http://www.test.com", "serviceOffered5", IllegalArgumentException.class
			}, {
				//Editando un servicio que no es tuyo
				"manager4", "", "description", "http://www.test.com", "serviceOffered5", javax.validation.ConstraintViolationException.class
			}, {
				//Editando un servicio que está cancelado
				"manager1", "", "description", "http://www.test.com", "serviceOffered1", IllegalArgumentException.class
			}

		// Se contempla la opcion de que solo se puede editar una rendezvouse en modo no final en el controlador
		//Se contempla la opcion de que solo se puede editar un rendezvouse que ha creado el usuario de ese rendezvouse en el controlador
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateListEdit((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], super.getEntityId((String) testingData[i][4]), (Class<?>) testingData[i][5]);
	}
	private void templateListEdit(final String username, final String name, final String description, final String picture, final int serviceOfferedId, final Class<?> expected) {
		ServiceOffered serviceOffered;
		List<ServiceOffered> services;
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			services = new ArrayList<ServiceOffered>(this.serviceOfferedService.findAllServicesByManager());
			serviceOffered = this.serviceOfferedService.findOne(serviceOfferedId);

			Assert.isTrue(services.contains(serviceOffered));
			serviceOffered.setName(name);
			serviceOffered.setDescription(description);
			serviceOffered.setPicture(picture);

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

	// Test List
	// Se comprueba el list de todas los servicios available
	// Caso de uso 4.2 y 5.1: List the services that are available in the system
	@Test
	public void driverServicesAvailable() {
		final Object testingData[][] = {
			{
				// Se comprueba que el servicio 2 aparece en la lista y que la lista tiene tamaño 3
				"serviceOffered2", 3, null
			}, {
				// Se comprueba que el servicio 1 no aparece en la lista
				"serviceOffered1", 3, IllegalArgumentException.class
			}, {
				// Se comprueba que el servicio 2 aparece en la lista pero que la lista no tiene tamaño 5
				"serviceOffered2", 5, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateServicesAvailable(super.getEntityId((String) testingData[i][0]), (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	private void templateServicesAvailable(final int serviceOfferedId, final int size, final Class<?> expected) {
		final Collection<ServiceOffered> services;
		ServiceOffered serviceOffered;
		Class<?> caught;

		caught = null;
		try {
			services = this.serviceOfferedService.AllServiceNotCancelled();
			serviceOffered = this.serviceOfferedService.findOne(serviceOfferedId);
			Assert.isTrue(services.contains(serviceOffered));
			Assert.isTrue(services.size() == size);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
