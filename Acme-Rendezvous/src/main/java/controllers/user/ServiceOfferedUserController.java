
package controllers.user;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.RendezvouseService;
import services.ServiceOfferedService;
import services.UserService;
import controllers.AbstractController;
import domain.ServiceOffered;

@Controller
@RequestMapping("/serviceoffered/user")
public class ServiceOfferedUserController extends AbstractController {

	//service---------------------------------------------------------------------------

	@Autowired
	private ServiceOfferedService	serviceOfferedService;

	@Autowired
	private RendezvouseService		rendezvouseService;

	@Autowired
	private UserService				userService;


	//constructor-------------------------------------------------------------------------
	public ServiceOfferedUserController() {
		super();
	}
	//Listing-----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<ServiceOffered> serviceoffered;
		serviceoffered = this.serviceOfferedService.AllServiceNotCancelled();
		result = new ModelAndView("serviceoffered/list");
		result.addObject("serviceoffered", serviceoffered);
		result.addObject("requestURI", "serviceoffered/user/list.do");
		return result;

	}

	//ancially methods---------------------------------------------------------------------------

	//	protected ModelAndView createEditModelAndView(final Rendezvouse rendezvouse) {
	//		Assert.notNull(rendezvouse);
	//		ModelAndView result;
	//		result = this.createEditModelAndView(rendezvouse, null);
	//		return result;
	//
	//	}
	//
	//	protected ModelAndView createEditModelAndView(final Rendezvouse rendezvouse, final String message) {
	//		Assert.notNull(rendezvouse);
	//		Collection<Rendezvouse> similarRendezvouses;
	//		ModelAndView result;
	//		result = new ModelAndView("rendezvous/edit");
	//		similarRendezvouses = this.rendezvouseService.findAllRendezvousesNotDeletedExceptRendezvousId(rendezvouse.getId());
	//
	//		result.addObject("rendezvouse", rendezvouse);
	//		result.addObject("similarRendezvouses", similarRendezvouses);
	//		result.addObject("message", message);
	//		return result;
	//
	//	}

}
