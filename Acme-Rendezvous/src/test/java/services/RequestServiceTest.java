
package services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import utilities.AbstractTest;
import domain.CreditCard;
import domain.Rendezvouse;
import domain.Request;
import domain.ServiceOffered;
import domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class RequestServiceTest extends AbstractTest {

	// Supporting services ----------------------------------------------------
	@Autowired
	RequestService			requestService;

	@Autowired
	RendezvouseService		rendezvouseService;

	@Autowired
	ServiceOfferedService	serviceOfferedService;

	@Autowired
	UserService				userService;

	@PersistenceContext
	EntityManager			entityManager;


	// Test CreateAndSave ----------------------------------------------------------------------------------
	@Test
	public void driverCreateAndSave() {
		final Collection<CreditCard> listCreditCards = this.createAllCreditCardsForTesting();
		//final Collection<Date> listDates = this.createAllDatedForTesting();

		final Iterator<CreditCard> iterator = listCreditCards.iterator();
		//Iterator<Date> iterator2=listDates.iterator();
		final CreditCard creditCardOk = iterator.next();
		//Date date1=iterator2.next();
		final Object testingData[][] = {
			{
				//Se crea una Request correctamente
				"user1", creditCardOk, "serviceOffered5", "comentario test1", "2018/03/11 19:40", "rendezvouse1", null
			}
		/*
		 * , {
		 * //Se crea un Announcement para un User que no le pertenece ese Rendezvous (Hacking get)
		 * "user5", iterator.next(), "title test", "description test", IllegalArgumentException.class
		 * }, {
		 * //Se crea un Announcement con el title en null
		 * "user1", iterator.next(), null, "description test", javax.validation.ConstraintViolationException.class
		 * }, {
		 * //Se crea un Announcement con el title en blanco
		 * "user1", iterator.next(), "", "description test", javax.validation.ConstraintViolationException.class
		 * }, {
		 * //Se crea un Announcement con el description en null
		 * "user1", "rendezvouse1", "title test", null, javax.validation.ConstraintViolationException.class
		 * }, {
		 * //Se crea un Announcement con el description en blanco
		 * "user1", "rendezvouse1", "title test", "", javax.validation.ConstraintViolationException.class
		 * }
		 */
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreateAndSave(super.getEntityId((String) testingData[i][0]), (CreditCard) testingData[i][1], super.getEntityId((String) testingData[i][2]), (String) testingData[i][3], (String) testingData[i][4],
				super.getEntityId((String) testingData[i][5]), (Class<?>) testingData[i][6]);
	}

	private void templateCreateAndSave(final int usernameId, final CreditCard creditcard, final int serviceOfferedId, final String comment, final String requestMoment, final int rendezvouseId, final Class<?> expected) {
		final Rendezvouse rendezvouseForRequest;
		Request request;
		Date requestMomentDate;
		ServiceOffered serviceOffered;
		User user;
		final Date requestMoment2;
		Class<?> caught;

		caught = null;
		try {
			serviceOffered = this.serviceOfferedService.findOne(serviceOfferedId);
			user = this.userService.findOne(usernameId);
			super.authenticate(user.getName());
			rendezvouseForRequest = this.rendezvouseService.findOne(rendezvouseId);
			request = this.requestService.create(rendezvouseForRequest.getId());
			request.setCreditCard(creditcard);
			request.setComment(comment);
			request.setUser(user);
			request.setServiceOffered(serviceOffered);
			request.setRendezvousid(rendezvouseId);
			if (requestMoment != null)
				requestMomentDate = (new SimpleDateFormat("yyyy/MM/dd HH:mm")).parse(requestMoment);
			else
				requestMomentDate = null;
			request.setRequestMoment(requestMomentDate);

			request = this.requestService.save(request);
			this.requestService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();

		}

		this.checkExceptions(expected, caught);
		this.unauthenticate();

	}

	//	//Other Methods additionals---------------------------------------------------------------------------------------

	//Date since;

	//calendar = Calendar.getInstance();
	//calendar.add(Calendar.DAY_OF_MONTH, -31);
	//since = calendar.getTime();

	private Collection<Date> createAllDatedForTesting() {
		Date date1;
		Collection<Date> result;

		result = new ArrayList<>();

		Calendar calendar1;
		calendar1 = Calendar.getInstance();
		calendar1.add(Calendar.DAY_OF_MONTH, -31);
		date1 = calendar1.getTime();
		result.add(date1);

		return result;
	}

	private Collection<CreditCard> createAllCreditCardsForTesting() {
		final Collection<CreditCard> result;
		final CreditCard creditCardOK;
		final CreditCard creditcardHolderNameNull;
		final CreditCard creditCardBrandNameNull;
		final CreditCard creditCardNumberNull;
		final CreditCard creditCardExpirationMonthNull;
		final CreditCard creditCardExpirationMonthInvalid;
		final CreditCard creditCardExpirationYearNull;
		final CreditCard creditCardExpirationYearInvalid;
		final CreditCard creditCardCvvInvalid;

		result = new ArrayList<CreditCard>();

		creditCardOK = new CreditCard();
		creditCardOK.setHolderName("Jose Joaquin");
		creditCardOK.setBrandName("La caixa");
		creditCardOK.setNumber("4388576018410707");
		creditCardOK.setExpirationMonth("03");
		creditCardOK.setExpirationYear("20");
		creditCardOK.setCvv(102);
		result.add(creditCardOK);

		creditcardHolderNameNull = new CreditCard();
		creditcardHolderNameNull.setHolderName(null);
		creditcardHolderNameNull.setBrandName("La caixa");
		creditcardHolderNameNull.setNumber("4388576018410707");
		creditcardHolderNameNull.setExpirationMonth("03");
		creditcardHolderNameNull.setExpirationYear("20");
		creditcardHolderNameNull.setCvv(102);
		result.add(creditcardHolderNameNull);

		creditCardBrandNameNull = new CreditCard();
		creditCardBrandNameNull.setHolderName("Jose Joaquin");
		creditCardBrandNameNull.setBrandName(null);
		creditCardBrandNameNull.setNumber("4388576018410707");
		creditCardBrandNameNull.setExpirationMonth("03");
		creditCardBrandNameNull.setExpirationYear("20");
		creditCardBrandNameNull.setCvv(102);
		result.add(creditCardBrandNameNull);

		creditCardNumberNull = new CreditCard();
		creditCardNumberNull.setHolderName("Jose Joaquin");
		creditCardNumberNull.setBrandName("La caixa");
		creditCardNumberNull.setNumber(null);
		creditCardNumberNull.setExpirationMonth("03");
		creditCardNumberNull.setExpirationYear("20");
		creditCardNumberNull.setCvv(102);
		result.add(creditCardNumberNull);

		creditCardExpirationMonthNull = new CreditCard();
		creditCardExpirationMonthNull.setHolderName("Jose Joaquin");
		creditCardExpirationMonthNull.setBrandName("La caixa");
		creditCardExpirationMonthNull.setNumber("4388576018410707");
		creditCardExpirationMonthNull.setExpirationMonth(null);
		creditCardExpirationMonthNull.setExpirationYear("20");
		creditCardExpirationMonthNull.setCvv(102);
		result.add(creditCardExpirationMonthNull);

		creditCardExpirationMonthInvalid = new CreditCard();
		creditCardExpirationMonthInvalid.setHolderName("Jose Joaquin");
		creditCardExpirationMonthInvalid.setBrandName("La caixa");
		creditCardExpirationMonthInvalid.setNumber("4388576018410707");
		creditCardExpirationMonthInvalid.setExpirationMonth("13");
		creditCardExpirationMonthInvalid.setExpirationYear("20");
		creditCardExpirationMonthInvalid.setCvv(102);
		result.add(creditCardExpirationMonthInvalid);

		creditCardExpirationYearNull = new CreditCard();
		creditCardExpirationYearNull.setHolderName("Jose Joaquin");
		creditCardExpirationYearNull.setBrandName("La caixa");
		creditCardExpirationYearNull.setNumber("4388576018410707");
		creditCardExpirationYearNull.setExpirationMonth("03");
		creditCardExpirationYearNull.setExpirationYear(null);
		creditCardExpirationYearNull.setCvv(102);
		result.add(creditCardExpirationYearNull);

		creditCardExpirationYearInvalid = new CreditCard();
		creditCardExpirationYearInvalid.setHolderName("Jose Joaquin");
		creditCardExpirationYearInvalid.setBrandName("La caixa");
		creditCardExpirationYearInvalid.setNumber("4388576018410707");
		creditCardExpirationYearInvalid.setExpirationMonth("03");
		creditCardExpirationYearInvalid.setExpirationYear("200");
		creditCardExpirationYearInvalid.setCvv(102);
		result.add(creditCardExpirationYearInvalid);

		creditCardCvvInvalid = new CreditCard();
		creditCardCvvInvalid.setHolderName("Jose Joaquin");
		creditCardCvvInvalid.setBrandName("La caixa");
		creditCardCvvInvalid.setNumber("4388576018410707");
		creditCardCvvInvalid.setExpirationMonth("03");
		creditCardCvvInvalid.setExpirationYear("20");
		creditCardCvvInvalid.setCvv(6666);
		result.add(creditCardCvvInvalid);

		return result;
	}
}
