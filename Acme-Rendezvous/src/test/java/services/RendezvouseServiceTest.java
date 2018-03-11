
package services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

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
import domain.GPS;
import domain.Rendezvouse;
import domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class RendezvouseServiceTest extends AbstractTest {

	// Supporting services ----------------------------------------------------
	@Autowired
	RendezvouseService	rendezvouseService;

	@Autowired
	UserService			userService;

	@PersistenceContext
	EntityManager		entityManager;


	// Test CreateAndSave ----------------------------------------------------------------------------------
	@Test
	public void driverCreateAndSave() {
		final Collection<GPS> listGPS = this.createAllGPSForTesting();
		final Iterator<GPS> iterator = listGPS.iterator();
		final GPS gpsOk = iterator.next();
		final Object testingData[][] = {
			{
				//Se crea un Rendezvouse correctamente
				"user1", "name test", "description", "2019/03/03", "http://www.test.com", gpsOk, true, false, false, null
			}, {
				//Se crea un Rendezvouse correctamente con Gps con latitude null
				"user1", "name test", "description", "2019/03/03", "http://www.test.com", iterator.next(), true, false, false, null
			}, {
				//Se crea un Rendezvouse correctamente con Gps con longitude null
				"user1", "name test", "description", "2019/03/03", "http://www.test.com", iterator.next(), true, false, false, null
			}, {
				//Se crea un Rendezvouse incorrectamente con Gps con OutOfRangeLatitudeMax
				"user1", "name test", "description", "2019/03/03", "http://www.test.com", iterator.next(), true, false, false, javax.validation.ConstraintViolationException.class
			}, {
				//Se crea un Rendezvouse incorrectamente con Gps con OutOfRangeLatitudeMin
				"user1", "name test", "description", "2019/03/03", "http://www.test.com", iterator.next(), true, false, false, javax.validation.ConstraintViolationException.class
			}, {
				//Se crea un Rendezvouse incorrectamente con Gps con OutOfRangeLongitudeMax
				"user1", "name test", "description", "2019/03/03", "http://www.test.com", iterator.next(), true, false, false, javax.validation.ConstraintViolationException.class
			}, {
				//Se crea un Rendezvouse incorrectamente con Gps con OutOfRangeLongitudeMin
				"user1", "name test", "description", "2019/03/03", "http://www.test.com", iterator.next(), true, false, false, javax.validation.ConstraintViolationException.class
			}, {
				//Se crea un Rendezvouse incorrectamente con title en blank
				"user5", "", "description", "2019/03/03", "http://www.test.com", gpsOk, true, false, false, javax.validation.ConstraintViolationException.class
			}, {
				//Se crea un Rendezvouse incorrectamente con description en blank
				"user5", "name test", "", "2019/03/03", "http://www.test.com", gpsOk, true, false, false, javax.validation.ConstraintViolationException.class
			}, {
				//Se crea un Rendezvouse incorrectamente con organisedMoment en null
				//Salta un NullPointerException en vez de javax.validation porque salta el Assert.isTrue que comprueba que la fecha introducida este en futuro
				"user1", "name test", "description", null, "http://www.test.com", gpsOk, true, false, false, NullPointerException.class
			}, {
				//Se crea un Rendezvouse correctamente con picture en null
				"user1", "name test", "description", "2019/03/03", null, gpsOk, true, false, false, null
			}, {
				//Se crea un Rendezvouse incorrectamente con picture con url malamente
				"user1", "name test", "description", "2019/03/03", "estoNoEsUnaURL", gpsOk, true, false, false, javax.validation.ConstraintViolationException.class
			}, {
				//Un user menor de edad crea un Rendezvouse para mayores de edad
				"user5", "name test", "description", "2019/03/03", "http://www.test.com", gpsOk, true, false, true, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreateAndSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (GPS) testingData[i][5], (boolean) testingData[i][6],
				(boolean) testingData[i][7], (boolean) testingData[i][8], (Class<?>) testingData[i][9]);
	}
	private void templateCreateAndSave(final String username, final String name, final String description, final String organisedMoment, final String picture, final GPS gps, final boolean draftMode, final boolean deleted, final boolean forAdult,
		final Class<?> expected) {
		Rendezvouse rendezvouse;
		final Date organisedMomentDate;
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			rendezvouse = this.rendezvouseService.create();
			rendezvouse.setName(name);
			rendezvouse.setDescription(description);
			if (organisedMoment != null)
				organisedMomentDate = (new SimpleDateFormat("yyyy/MM/dd")).parse(organisedMoment);
			else
				organisedMomentDate = null;
			rendezvouse.setOrganisedMoment(organisedMomentDate);
			rendezvouse.setPicture(picture);
			rendezvouse.setGps(gps);
			rendezvouse.setDraftMode(draftMode);
			rendezvouse.setDeleted(deleted);
			rendezvouse.setForAdult(forAdult);
			rendezvouse = this.rendezvouseService.save(rendezvouse);
			this.rendezvouseService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

		super.unauthenticate();
	}

	// Test Edit ----------------------------------------------------------------------------------

	@Test
	public void driverEdit() {
		final Object testingData[][] = {
			{
				//Se edita el rendezvouse1 por el user que la ha creado
				"user1", "rendezvouse1", null
			}
		// Se contempla la opcion de que solo se puede editar una rendezvouse en modo no final en el controlador
		//Se contempla la opcion de que solo se puede editar un rendezvouse que ha creado el usuario de ese rendezvouse en el controlador
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateEdit((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}
	private void templateEdit(final String username, final int rendezvouseId, final Class<?> expected) {
		Rendezvouse rendezvouse;
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			rendezvouse = this.rendezvouseService.findOne(rendezvouseId);
			rendezvouse.setName("Editing test name");
			rendezvouse = this.rendezvouseService.save(rendezvouse);
			this.unauthenticate();
			this.rendezvouseService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

	}

	// Test Delete Virtual----------------------------------------------------------------------------------

	@Test
	public void driverDeleteVirtual() {
		final Object testingData[][] = {
			{
				//Se elimina el rendezvouse1 por el user que la ha creado pero con draftMode en true
				"user1", "rendezvouse1", null
			}, {
				//Se elimina el rendezvouse1 por el user que la ha creado pero con draftMode en false
				"user2", "rendezvouse2", IllegalArgumentException.class
			}, {
				//Se elimina el rendezvouse1 por el user que NO la ha creado(Hacking get)
				"user5", "rendezvouse1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDeleteVirtual((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}
	private void templateDeleteVirtual(final String username, final int rendezvouseId, final Class<?> expected) {
		Rendezvouse rendezvouse;
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			rendezvouse = this.rendezvouseService.findOne(rendezvouseId);
			this.rendezvouseService.deletevirtual(rendezvouse);
			this.unauthenticate();
			this.rendezvouseService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

	}

	// Test Delete ----------------------------------------------------------------------------------

	@Test
	public void driverDelete() {
		final Object testingData[][] = {
			{
				//Se elimina el rendezvouse1 por el user que la ha creado (Solo puede eliminarlas el admin)
				"user1", "rendezvouse1", IllegalArgumentException.class
			}, {
				//Se elimina el rendezvouse1 por el admin
				"admin", "rendezvouse1", null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDelete((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}
	private void templateDelete(final String username, final int rendezvouseId, final Class<?> expected) {
		Rendezvouse rendezvouse;
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			rendezvouse = this.rendezvouseService.findOne(rendezvouseId);
			this.rendezvouseService.delete(rendezvouse);
			this.unauthenticate();
			this.rendezvouseService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

	}

	// Test Assist ----------------------------------------------------------------------------------

	@Test
	public void driverAssist() {
		final Object testingData[][] = {
			{
				//El user 1 asiste al rendezvouse1 (ya asistia de antes asi que no tendria que duplicarse su presencia en la lista de asistentes del rendezvouse
				"user1", "rendezvouse1", null
			}, {
				//El user 2 asiste al rendezvouse1
				"user2", "rendezvouse1", null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateAssist((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}
	private void templateAssist(final String username, final int rendezvouseId, final Class<?> expected) {
		final Rendezvouse rendezvouse;
		User userPrincipal;
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			this.rendezvouseService.assist(rendezvouseId);
			this.rendezvouseService.flush();
			rendezvouse = this.rendezvouseService.findOne(rendezvouseId);
			userPrincipal = this.userService.findByPrincipal();
			Assert.isTrue(rendezvouse.getAssistants().contains(userPrincipal));
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

		this.unauthenticate();

	}

	// Test Not-Assist ----------------------------------------------------------------------------------

	@Test
	public void driverNotAssist() {
		final Object testingData[][] = {
			{
				//El user 5 no asiste al rendezvouse1
				"user5", "rendezvouse1", null
			}, {
				//El user 2 no asiste al rendezvouse1
				"user2", "rendezvouse1", null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateNotAssist((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}
	private void templateNotAssist(final String username, final int rendezvouseId, final Class<?> expected) {
		Rendezvouse rendezvouse;
		User userPrincipal;
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			this.rendezvouseService.unassist(rendezvouseId);
			this.rendezvouseService.flush();
			rendezvouse = this.rendezvouseService.findOne(rendezvouseId);
			userPrincipal = this.userService.findByPrincipal();
			Assert.isTrue(!rendezvouse.getAssistants().contains(userPrincipal));
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

		this.unauthenticate();

	}

	// Test LinkSimilar ----------------------------------------------------------------------------------

	@SuppressWarnings("unchecked")
	@Test
	public void driverLinkSimilar() {
		Collection<Rendezvouse> similarRendezvousesForTesting;

		similarRendezvousesForTesting = new ArrayList<Rendezvouse>();
		similarRendezvousesForTesting.addAll(this.rendezvouseService.findOne(super.getEntityId("rendezvouse2")).getSimilarRendezvouses());
		final Object testingData[][] = {
			{
				//El user1 que ha creado la Rendezvouse 1 cambia las similar rendezvouses
				"user1", "rendezvouse1", similarRendezvousesForTesting, null
			}, {
				//El user2 que NO ha creado la Rendezvouse 1 cambia las similar rendezvouses
				"user2", "rendezvouse1", similarRendezvousesForTesting, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateLinkSimilar((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Collection<Rendezvouse>) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	private void templateLinkSimilar(final String username, final int rendezvouseId, final Collection<Rendezvouse> similarRendezvousesForTesting, final Class<?> expected) {
		final Rendezvouse rendezvouse;
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			rendezvouse = this.rendezvouseService.findOne(rendezvouseId);
			rendezvouse.setSimilarRendezvouses(similarRendezvousesForTesting);
			this.rendezvouseService.linkSimilar(rendezvouse);
			this.rendezvouseService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

		this.unauthenticate();

	}

	// Test UnLinkSimilar ----------------------------------------------------------------------------------

	@SuppressWarnings("unchecked")
	@Test
	public void driverUnLinkSimilar() {
		Collection<Rendezvouse> similarRendezvousesForTesting;

		similarRendezvousesForTesting = new ArrayList<Rendezvouse>();
		similarRendezvousesForTesting.addAll(this.rendezvouseService.findOne(super.getEntityId("rendezvouse2")).getSimilarRendezvouses());
		final Object testingData[][] = {
			{
				//El user1 que ha creado la Rendezvouse 1 cambia las similar rendezvouses
				"user1", "rendezvouse1", similarRendezvousesForTesting, null
			}, {
				//El user2 que NO ha creado la Rendezvouse 1 cambia las similar rendezvouses
				"user2", "rendezvouse1", similarRendezvousesForTesting, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateUnLinkSimilar((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Collection<Rendezvouse>) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	private void templateUnLinkSimilar(final String username, final int rendezvouseId, final Collection<Rendezvouse> similarRendezvousesForTesting, final Class<?> expected) {
		final Rendezvouse rendezvouse;
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			rendezvouse = this.rendezvouseService.findOne(rendezvouseId);
			rendezvouse.setSimilarRendezvouses(similarRendezvousesForTesting);
			this.rendezvouseService.unlinkSimilar(rendezvouse);
			this.rendezvouseService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

		this.unauthenticate();

	}

	//Other Methods additionals---------------------------------------------------------------------------------------

	private Collection<GPS> createAllGPSForTesting() {
		final Collection<GPS> result;
		final GPS gpsOk;
		final GPS gpsNullLatitude;
		final GPS gpsNullLongitude;
		final GPS gpsOutOfRangeLatitudeMax;
		final GPS gpsOutOfRangeLatitudeMin;
		final GPS gpsOutOfRangeLongitudeMax;
		final GPS gpsOutOfRangeLongitudeMin;

		result = new ArrayList<GPS>();

		gpsOk = new GPS();
		gpsOk.setLatitude(-89.5);
		gpsOk.setLongitude(179.0);
		result.add(gpsOk);

		gpsNullLatitude = new GPS();
		gpsNullLatitude.setLatitude(null);
		gpsNullLatitude.setLongitude(-179.0);
		result.add(gpsNullLatitude);

		gpsNullLongitude = new GPS();
		gpsNullLongitude.setLatitude(89.0);
		gpsNullLongitude.setLongitude(null);
		result.add(gpsNullLongitude);

		gpsOutOfRangeLatitudeMax = new GPS();
		gpsOutOfRangeLatitudeMax.setLatitude(95.0);
		gpsOutOfRangeLatitudeMax.setLongitude(0.0);
		result.add(gpsOutOfRangeLatitudeMax);

		gpsOutOfRangeLatitudeMin = new GPS();
		gpsOutOfRangeLatitudeMin.setLatitude(-91.0);
		gpsOutOfRangeLatitudeMin.setLongitude(0.0);
		result.add(gpsOutOfRangeLatitudeMin);

		gpsOutOfRangeLongitudeMax = new GPS();
		gpsOutOfRangeLongitudeMax.setLatitude(0.0);
		gpsOutOfRangeLongitudeMax.setLongitude(181.0);
		result.add(gpsOutOfRangeLongitudeMax);

		gpsOutOfRangeLongitudeMin = new GPS();
		gpsOutOfRangeLongitudeMin.setLatitude(0.0);
		gpsOutOfRangeLongitudeMin.setLongitude(-181.0);
		result.add(gpsOutOfRangeLongitudeMin);

		return result;
	}

}
