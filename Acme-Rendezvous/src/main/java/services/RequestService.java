
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RequestRepository;
import domain.CreditCard;
import domain.Rendezvouse;
import domain.Request;
import domain.ServiceOffered;
import domain.User;

@Service
@Transactional
public class RequestService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private RequestRepository	requestRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private UserService			userService;

	@Autowired
	private RendezvouseService	rendezvouseService;

	//Importar la que pertenece a Spring
	@Autowired
	private Validator			validator;


	//Constructor------------------------------------------------------------
	public RequestService() {
		super();

	}

	// Simple CRUD Methods----------------------------------------------------------
	public Request create(Integer rendezvousId) {
		Request result;
		User userPrincipal;
		Date moment;
		Collection<CreditCard> cards;

		moment = new Date();
		result = new Request();

		result.setRendezvousid(rendezvousId);
		userPrincipal = this.userService.findByPrincipal();
		Assert.notNull(userPrincipal);
		result.setUser(userPrincipal);
		result.setRequestMoment(moment);
		cards = this.findAllCreditCardsInDescendOrderByUser(userPrincipal.getId());
		if (cards.size() > 0)
			result.setCreditCard(cards.iterator().next());

		return result;
	}

	public Request findOne(final int requestId) {
		Assert.isTrue(requestId != 0);
		Request result;
		result = this.requestRepository.findOne(requestId);
		return result;
	}

	public Collection<Request> findAll() {
		Collection<Request> result;
		result = this.requestRepository.findAll();
		return result;
	}
	public Request save(final Request request) {
		Assert.notNull(request);
		Rendezvouse rendezvous;
		User user;
		ServiceOffered serviceOffered;
		Request result;
		
		
		result = new Request();
		Date moment;
		user = this.userService.findByPrincipal();
		rendezvous = this.rendezvouseService.findOne(request.getRendezvousid());
		serviceOffered = request.getServiceOffered();
		Assert.isTrue(serviceOffered.getCategory()!=null);
		Assert.isTrue(user.getRendezvousesCreated().contains(rendezvous), "Cannot commit this operation, because that service doens't belong to one of your rendezvouses");

		moment = new Date(System.currentTimeMillis() - 1000);
		request.setRequestMoment(moment);
		rendezvous.getServicesOffered().add(request.getServiceOffered());
		Assert.isTrue(this.checkCreditCard(request.getCreditCard()), "Invalid credit card");
		result = this.requestRepository.save(request);
		Assert.notNull(result);
		return result;
	}

	public void delete(final Request request) {
		assert request != null;
		assert request.getId() != 0;
		Assert.isTrue(this.requestRepository.exists(request.getId()));
		this.requestRepository.delete(request);
	}
	
	public Collection<Request> findServiceOfferedOfServiceOfferedId(int serviceOfferedId){
		
		Collection<Request> result;
		
		result = this.requestRepository.findServiceOfferedOfServiceOfferedId(serviceOfferedId);
		
		return result;
	}
	
	//Other business methods-----------------------------------------------------

	public Collection<CreditCard> findAllCreditCardsInDescendOrderByUser(int userId) {
		User user;
		Collection<CreditCard> cards;

		user = this.userService.findByPrincipal();
		cards = this.requestRepository.findAllCreditCardsInDescendOrderByUser(user.getId());

		return cards;
	}

	public boolean checkCreditCard(final CreditCard creditCard) {
		boolean res;
		Calendar calendar;
		int actualYear;

		res = false;
		calendar = new GregorianCalendar();
		actualYear = calendar.get(Calendar.YEAR);
		actualYear = actualYear % 100;

		if (Integer.parseInt(creditCard.getExpirationYear()) > actualYear)
			res = true;
		else if (Integer.parseInt(creditCard.getExpirationYear()) == actualYear && Integer.parseInt(creditCard.getExpirationMonth()) >= calendar.get(Calendar.MONTH))
			res = true;

		return res;
	}

	public Collection<Request> findAllRequestByUser(int userId) {
		Collection<Request> result;
		User user;

		user = this.userService.findByPrincipal();
		result = this.requestRepository.findAllRequestByUser(user.getId());

		return result;
	}

	public Request reconstruct(final Request request, final BindingResult binding) {
		Request result;
		Request requestBD;
		User userPrincipal;
		if (request.getId() == 0) {

			Date moment;

			result = request;
			moment = new Date(System.currentTimeMillis() - 1000);

			userPrincipal = this.userService.findByPrincipal();
			result.setUser(userPrincipal);

			result.setRequestMoment(moment);
		} else {
			requestBD = this.requestRepository.findOne(request.getId());
			request.setId(requestBD.getId());
			request.setVersion(requestBD.getVersion());
			request.setUser(requestBD.getUser());

			request.setRequestMoment(requestBD.getRequestMoment());
			result = request;
		}
		this.validator.validate(result, binding);
		return result;
	}
	
	public void flush() {
		this.requestRepository.flush();
	}

}
