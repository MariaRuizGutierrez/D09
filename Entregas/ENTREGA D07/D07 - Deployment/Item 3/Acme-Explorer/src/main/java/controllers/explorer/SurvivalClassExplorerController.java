
package controllers.explorer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ExplorerService;
import services.SurvivalClassService;
import services.TripService;
import controllers.AbstractController;
import domain.Explorer;
import domain.SurvivalClass;
import domain.Trip;

@Controller
@RequestMapping("/survivalClass/explorer")
public class SurvivalClassExplorerController extends AbstractController {

	@Autowired
	SurvivalClassService	survivalClassService;
	@Autowired
	TripService				tripService;
	@Autowired
	ExplorerService			explorerService;


	//notEnrol-----------------------------------------------------------
	@RequestMapping(value = "/notEnrol", method = RequestMethod.GET)
	public ModelAndView notEnrol(@RequestParam final int survivalClassId) {
		ModelAndView result;
		Trip trip;

		trip = this.survivalClassService.findByPrincipalSurvivalClassTripId(survivalClassId);
		result = new ModelAndView("redirect:/survivalClass/explorer/list-enrol.do?tripId=" + trip.getId());
		try {
			this.survivalClassService.notEnrolExplorerPrincipal(survivalClassId);
		} catch (final Throwable oops) {
			result.addObject("message", "endorserRecord.commit.error");
		}
		return result;
	}
	//enrol-----------------------------------------------------------
	@RequestMapping(value = "/enrol", method = RequestMethod.GET)
	public ModelAndView enrol(@RequestParam final int survivalClassId) {
		ModelAndView result;
		Trip trip;

		trip = this.survivalClassService.findByPrincipalSurvivalClassTripId(survivalClassId);
		result = new ModelAndView("redirect:/survivalClass/explorer/list-not-enrol.do?tripId=" + trip.getId());
		try {
			this.survivalClassService.enrolExplorerPrincipal(survivalClassId);
		} catch (final Throwable oops) {
			result.addObject("message", "endorserRecord.commit.error");
		}
		return result;
	}
	//List---------------------------------------------------------------

	@RequestMapping(value = "/list-enrol", method = RequestMethod.GET)
	public ModelAndView listEnrol(@RequestParam final int tripId) {

		ModelAndView result;
		Explorer explorer;
		final Collection<SurvivalClass> survivalClasses;

		explorer = this.explorerService.findByPrincipal();
		survivalClasses = this.survivalClassService.findAllByTripIdEnrol(tripId, explorer.getId());
		result = new ModelAndView("survivalClass/list");
		result.addObject("survivalClasses", survivalClasses);
		result.addObject("enrol", true);
		result.addObject("tripId", tripId);

		return result;

	}

	@RequestMapping(value = "/list-not-enrol", method = RequestMethod.GET)
	public ModelAndView listNotEnrol(@RequestParam final int tripId) {

		ModelAndView result;
		Explorer explorer;
		final Collection<SurvivalClass> survivalClasses;

		explorer = this.explorerService.findByPrincipal();
		survivalClasses = this.survivalClassService.findAllByTripIdNotEnrol(tripId, explorer.getId());
		result = new ModelAndView("survivalClass/list");
		result.addObject("survivalClasses", survivalClasses);
		result.addObject("enrol", false);
		result.addObject("tripId", tripId);

		return result;

	}

	// Display ----------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int survivalClassId) {
		ModelAndView result;
		SurvivalClass survivalClass;

		survivalClass = this.survivalClassService.findOne(survivalClassId);
		result = new ModelAndView("survivalClass/display");
		result.addObject("requestURI", "survivalClass/explorer/display.do");
		result.addObject("survivalClass", survivalClass);

		return result;
	}

}
