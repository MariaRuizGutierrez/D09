
package controllers.user;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.RendezvouseService;
import services.RequestService;
import services.ServiceOfferedService;
import services.UserService;
import controllers.AbstractController;
import domain.Rendezvouse;
import domain.Request;
import domain.ServiceOffered;
import domain.User;

@Controller
@RequestMapping("/request/user")
public class RequestUserController extends AbstractController {

	//service---------------------------------------------------------------------------

	@Autowired
	private RequestService			requestService;

	@Autowired
	private RendezvouseService		rendezvouseService;

	@Autowired
	private UserService				userService;

	@Autowired
	private ServiceOfferedService	serviceOfferedService;


	public RequestUserController() {
		super();
	}

	//Creation---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int rendezvouseId) {
		ModelAndView result;
		Request request;
		User user;
		Rendezvouse rendezvous;

		user = this.userService.findByPrincipal();
		rendezvous = this.rendezvouseService.findOne(rendezvouseId);
		Assert.isTrue(user.getRendezvousesCreated().contains(rendezvous), "Cannot commit this operation, because that service doens't belong to one of your rendezvouses");
		request = this.requestService.create(rendezvouseId);
		result = this.createEditModelAndView(request);

		return result;
	}

	//Edition--------------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Request request, final BindingResult bindingResult) {
		ModelAndView result;

		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(request);
		else
			try {
				this.requestService.save(request);
				result = new ModelAndView("redirect:/rendezvous/user/list.do?d-16544-p=1");
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("could not execute statement; SQL [n/a]; constraint [null]" + "; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement"))
					result = this.createEditModelAndView(request, "request.services");
				else if (oops.getMessage().equals("Invalid credit card"))
					result = this.createEditModelAndView(request, "request.invalid.card");

				else
					result = this.createEditModelAndView(request, "request.commit.error");
			}

		return result;
	}

	//ancially methods---------------------------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Request request) {
		Assert.notNull(request);
		ModelAndView result;
		result = this.createEditModelAndView(request, null);
		return result;

	}

	protected ModelAndView createEditModelAndView(final Request request, final String message) {
		Assert.notNull(request);
		ModelAndView result;
		Collection<ServiceOffered> serviceOffered;
		serviceOffered = this.serviceOfferedService.AllServiceNotCancelledAveibleForRendezvouse(request.getRendezvousid());
		result = new ModelAndView("request/edit");
		result.addObject("request", request);
		result.addObject("message", message);
		result.addObject("serviceOffered", serviceOffered);
		return result;

	}

}
