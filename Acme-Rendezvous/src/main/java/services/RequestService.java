
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RequestRepository;
import domain.CreditCard;
import domain.Rendezvouse;
import domain.Request;
import domain.ServiceOffered;
import domain.User;

@Service
@Transactional
public class RequestService {

	@Autowired
	private RequestRepository		requestRepository;

	@Autowired
	private ServiceOfferedService	serviceOfferedService;

	@Autowired
	private UserService				userService;
	@Autowired
	private RendezvouseService		rendezvouseService;


	public RequestService() {
		super();

	}

	public Request create(Integer rendezvousId) {
		Request result;
		User userPrincipal;
		Collection<ServiceOffered> services;
		Date moment;

		moment = new Date();
		result = new Request();
		services = new ArrayList<ServiceOffered>();
		result.setRendezvousid(rendezvousId);
		userPrincipal = this.userService.findByPrincipal();
		Assert.notNull(userPrincipal);
		result.setUser(userPrincipal);
		result.setRequestMoment(moment);

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
		Request result;
		result = new Request();
		Date moment;

		rendezvous = this.rendezvouseService.findOne(request.getRendezvousid());
		moment = new Date(System.currentTimeMillis() - 1000);
		request.setRequestMoment(moment);
		rendezvous.getServicesOffered().add(request.getServiceOffered());
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
	//Other business methods-----------------------------------------------------

	Collection<CreditCard> findAllCreditCardsInDescendOrderByUser(int userId) {
		User user;
		Collection<CreditCard> cards;

		user = this.userService.findByPrincipal();
		cards = this.requestRepository.findAllCreditCardsInDescendOrderByUser(user.getId());

		return cards;
	}
}
