
package services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.GPS;
import domain.Rendezvouse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class RendezvouseServiceTest extends AbstractTest {

	// Supporting services ----------------------------------------------------
	@Autowired
	RendezvouseService	rendezvouseService;


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
		}

		this.checkExceptions(expected, caught);

		super.unauthenticate();
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
