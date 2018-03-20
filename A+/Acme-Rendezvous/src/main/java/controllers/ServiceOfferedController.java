package controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.ServiceOffered;

import services.ServiceOfferedService;

@Controller
@RequestMapping("/serviceOffered")
public class ServiceOfferedController extends AbstractController{
	
	// Services---------------------------------------------------------

		@Autowired
		private ServiceOfferedService		serviceOfferedService;
		
		
	//Constructor--------------------------------------------------------

		public ServiceOfferedController() {
			super();
		}

	// Display ----------------------------------------------------------------

		@RequestMapping(value = "/display", method = RequestMethod.GET)
		public ModelAndView display(@RequestParam int serviceOfferedId) {
			ModelAndView result;
			ServiceOffered serviceOffered;

			serviceOffered = this.serviceOfferedService.findOne(serviceOfferedId);

			result = new ModelAndView("serviceoffered/display");
			result.addObject("serviceOffered", serviceOffered);
			result.addObject("requestURI", "serviceOffered/display.do");

			return result;
		}
}
