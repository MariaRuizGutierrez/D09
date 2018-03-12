
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ServiceOfferedService;
import controllers.AbstractController;
import domain.ServiceOffered;

@Controller
@RequestMapping("/serviceoffered/administrator")
public class ServiceOfferedAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private ServiceOfferedService	serviceOfferedService;


	//Listing -----------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<ServiceOffered> serviceoffered;

		serviceoffered = this.serviceOfferedService.findAll();

		result = new ModelAndView("serviceoffered/list");
		result.addObject("serviceoffered", serviceoffered);
		result.addObject("requestURI", "serviceoffered/administrator/list.do");

		return result;
	}

	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView delete(final int serviceOfferedId) {
		ModelAndView result;
		final ServiceOffered serviceOffered;

		serviceOffered = this.serviceOfferedService.findOne(serviceOfferedId);
		Assert.notNull(serviceOffered);
		try {
			this.serviceOfferedService.cancel(serviceOffered);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.listWithMessage("announcement.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int serviceOfferedId) {
		ModelAndView result;
		ServiceOffered serviceOffered;

		serviceOffered = this.serviceOfferedService.findOne(serviceOfferedId);
		result = new ModelAndView("serviceoffered/display");
		result.addObject("serviceOffered", serviceOffered);

		return result;
	}
	protected ModelAndView listWithMessage(final String message) {
		final ModelAndView result;
		Collection<ServiceOffered> servicesOffered;
		servicesOffered = this.serviceOfferedService.findAll();
		result = new ModelAndView("serviceoffered/list");
		result.addObject("servicesOffered", servicesOffered);
		result.addObject("requestURI", "/serviceoffered/administrator/list.do");
		result.addObject("message", message);
		return result;

	}

	protected ModelAndView createEditModelAndView(final ServiceOffered serviceOffered) {

		Assert.notNull(serviceOffered);
		ModelAndView result;
		result = this.createEditModelAndView(serviceOffered, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final ServiceOffered serviceOffered, final String messageCode) {
		assert serviceOffered != null;

		ModelAndView result;
		Collection<ServiceOffered> servicesOffered;

		servicesOffered = this.serviceOfferedService.findAll();

		result = new ModelAndView("serviceOffered/edit");
		result.addObject("serviceOffered", serviceOffered);
		result.addObject("servicesOffered", servicesOffered);
		result.addObject("message", messageCode);

		return result;

	}
}
