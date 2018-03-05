
package controllers.manager;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ManagerService;
import services.SurvivalClassService;
import services.TripService;
import controllers.AbstractController;
import domain.Manager;
import domain.SurvivalClass;
import domain.Trip;

@Controller
@RequestMapping("/survivalClass/manager")
public class SurvivalClassManagerController extends AbstractController {

	// Services---------------------------------------------------------

	@Autowired
	private SurvivalClassService	survivalClassService;

	@Autowired
	private TripService				tripService;

	@Autowired
	private ManagerService			managerService;


	//Constructor--------------------------------------------------------

	public SurvivalClassManagerController() {
		super();
	}

	//Listing------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<SurvivalClass> survivalClass;

		survivalClass = this.survivalClassService.findSurvivalClassByManager();

		result = new ModelAndView("survivalClass/list");
		result.addObject("requestURI", "survivalClass/manager/list.do");
		result.addObject("survivalClasses", survivalClass);
		return result;

	}

	// Display ----------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int survivalClassId) {
		ModelAndView result;
		SurvivalClass survivalClass;

		survivalClass = this.survivalClassService.findOne(survivalClassId);
		result = new ModelAndView("survivalClass/display");
		result.addObject("requestURI", "survivalClass/manager/display.do");
		result.addObject("survivalClass", survivalClass);

		return result;
	}

	//Creation-----------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int tripId) {
		ModelAndView result;
		SurvivalClass survivalClass;
		Manager manager;
		Trip trip;
		Date date;

		trip = this.tripService.findOne(tripId);
		date = new Date();
		Assert.isTrue(trip.getFinishDate().after(date) && trip.isCancelled() == false);
		manager = this.managerService.findByPrincipal();
		Assert.isTrue(trip.getManager().equals(manager), "Cannot commit this operation, because it's illegal");
		survivalClass = this.survivalClassService.create(trip);

		result = this.createEditModelAndView(survivalClass);
		result.addObject("manager", manager);
		return result;

	}

	//Edition------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int survivalClassId) {
		ModelAndView result;
		SurvivalClass survivalClass;
		Manager manager;

		manager = this.managerService.findByPrincipal();
		survivalClass = this.survivalClassService.findOne(survivalClassId);
		Assert.notNull(survivalClass);
		Assert.isTrue(survivalClass.getManager().equals(manager), "Cannot commit this operation, because it's illegal");
		result = this.createEditModelAndView(survivalClass);
		result.addObject("manager", manager);
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final SurvivalClass survivalClass, final BindingResult bindingResult) {
		ModelAndView result;
		Date now;

		now = new Date();
		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(survivalClass, "survivalClass.save.commit.error");
		else
			try {
				Assert.isTrue(!survivalClass.getTrip().getPublicationDate().before(now));
				this.survivalClassService.save(survivalClass);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(survivalClass, "survivalClass.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@ModelAttribute final SurvivalClass survivalClass, final BindingResult bindingResult) {
		ModelAndView result;

		try {
			this.survivalClassService.delete(survivalClass);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(survivalClass, "survivalClass.commit.error");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final SurvivalClass survivalClass) {
		assert survivalClass != null;
		ModelAndView result;
		result = this.createEditModelAndView(survivalClass, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final SurvivalClass survivalClass, final String message) {

		assert survivalClass != null;
		ModelAndView result;

		result = new ModelAndView("survivalClass/edit");
		result.addObject("survivalClass", survivalClass);
		result.addObject("message", message);

		return result;

	}
	// Display ----------------------------------------------------------------
	@RequestMapping(value = "/displayTrip", method = RequestMethod.GET)
	public ModelAndView displayTrip(@RequestParam int survivalClassId) {
		ModelAndView result;
		Trip trip;
		SurvivalClass survivalClass;
		survivalClass = this.survivalClassService.findOne(survivalClassId);
		trip = this.survivalClassService.findByPrincipalSurvivalClassTrip(survivalClass);
		result = new ModelAndView("survivalClass/displayTrip");
		result.addObject("trip", trip);
		result.addObject("survivalClass", survivalClass);

		return result;
	}

}
