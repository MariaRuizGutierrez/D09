
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.ServiceOfferedService;
import controllers.AbstractController;
import domain.ServiceOffered;

@Controller
@RequestMapping("/serviceOffered/administrator")
public class ServiceOfferedAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private ServiceOfferedService	serviceOfferedService;


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
