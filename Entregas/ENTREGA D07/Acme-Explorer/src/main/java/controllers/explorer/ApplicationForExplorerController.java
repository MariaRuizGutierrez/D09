
package controllers.explorer;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationForService;
import services.ExplorerService;
import services.TripService;
import controllers.AbstractController;
import domain.ApplicationFor;
import domain.Explorer;
import domain.Trip;

@Controller
@RequestMapping("/applicationFor/explorer")
public class ApplicationForExplorerController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private TripService				tripService;
	@Autowired
	private ApplicationForService	applicationForService;
	@Autowired
	private ExplorerService			explorerService;


	//public Trip						tripAsociado;

	// Constructors -----------------------------------------------------------
	public ApplicationForExplorerController() {

	}

	// Listing ----------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listApplied() {
		ModelAndView result;
		//Collection<Trip> trips;
		Collection<ApplicationFor> applicationFor;
		Explorer explorer;
		final Date date = new Date();
		explorer = this.explorerService.findByPrincipal();
		applicationFor = new ArrayList<ApplicationFor>(explorer.getApplicationsFor());
		//trips = this.tripService.findAllTripsApplyByExplorerId(explorer.getId());

		result = new ModelAndView("applicationFor/list");
		result.addObject("applicationFor", applicationFor);
		result.addObject("date", date);
		//result.addObject("apply", true);
		result.addObject("requestURI", "applicationFor/explorer/list.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int tripId) {
		ModelAndView result;
		Trip trip;
		ApplicationFor applicationFor;

		trip = this.tripService.findOne(tripId);
		Assert.notNull(trip);
		applicationFor = this.applicationForService.create(trip);
		result = this.createEditModelAndView(applicationFor);
		result.addObject("trip", trip);

		return result;
	}

	//Display--------------------------------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int applicationforId) throws ParseException {
		ModelAndView result;
		ApplicationFor applicationfor = new ApplicationFor();
		boolean morethirtyDays;

		applicationfor = this.applicationForService.findOne(applicationforId);
		morethirtyDays = this.applicationForService.moreThanThirtyDays(applicationfor.getTrip().getStartDate());

		result = new ModelAndView("applicationFor/display");
		result.addObject("applicationFor", applicationfor);
		result.addObject("morethirtyDays", morethirtyDays);

		return result;
	}

	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView cancel(@RequestParam final int applicationForId) {
		ModelAndView result;
		ApplicationFor applicationFor;

		Explorer explorer;

		explorer = this.explorerService.findByPrincipal();

		applicationFor = this.applicationForService.findOne(applicationForId);

		Assert.isTrue(applicationFor.getExplorer().equals(explorer), "Cannot commit this opetation, because it's illegal");
		applicationFor = this.applicationForService.findOne(applicationForId);
		result = this.createCancelModelAndView(applicationFor);
		result.addObject("applicationFor", applicationFor);
		final Trip trip = applicationFor.getTrip();
		result.addObject("trip", trip);

		return result;
	}

	@RequestMapping(value = "/enter", method = RequestMethod.GET)
	public ModelAndView enter(@RequestParam final int applicationForId) {
		ModelAndView result;
		ApplicationFor applicationFor;
		applicationFor = this.applicationForService.findOne(applicationForId);
		result = this.createEnterModelAndView(applicationFor);
		result.addObject("applicationFor", applicationFor);
		final Trip trip = applicationFor.getTrip();
		result.addObject("trip", trip);

		return result;
	}

	@RequestMapping(value = "/enter", method = RequestMethod.POST, params = "enter")
	public ModelAndView enter(@Valid final ApplicationFor applicationFor, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndViewCreditCard(applicationFor, null);
		else
			try {
				this.applicationForService.enter(applicationFor);

				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndViewCreditCard(applicationFor, "applicationforcreditError.commit.error");
			}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ApplicationFor applicationFor, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(applicationFor);
		else
			try {
				//Comprobar que esa application esta realizada aun, si la Collection esta vacia significa que no se ha hecho
				final Collection<ApplicationFor> duplicatedApplies = this.applicationForService.checkDuplicatedApply(applicationFor.getExplorer().getId(), applicationFor.getTrip().getId());
				Assert.isTrue(duplicatedApplies.isEmpty(), "duplicatedApply");

				this.applicationForService.save(applicationFor);
				Explorer explorer;
				explorer = this.explorerService.findByPrincipal();
				explorer.getApplicationsFor().add(applicationFor);

				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("duplicatedApply"))
					result = this.createEditModelAndView(applicationFor, "applicationfor.duplicatedApply.error");
				else
					result = this.createEditModelAndView(applicationFor, "applicationfor.commit.error");
			}
		return result;
	}
	@RequestMapping(value = "/cancel", method = RequestMethod.POST, params = "cancel")
	public ModelAndView cancel(@Valid final ApplicationFor applicationFor, final BindingResult binding) {
		ModelAndView result;
		//Collection<ApplicationFor> applicationsFor;	
		if (binding.hasErrors())
			result = this.createEditModelAndView(applicationFor);
		else
			try {
				this.applicationForService.cancel(applicationFor);
				this.applicationForService.save(applicationFor);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createCancelModelAndView(applicationFor, "applicationfor.cancel.error");
			}
		return result;
	}

	protected ModelAndView createCancelModelAndView(final ApplicationFor applicationFor) {
		ModelAndView result;

		result = this.createCancelModelAndView(applicationFor, null);
		return result;
	}

	private ModelAndView createCancelModelAndView(final ApplicationFor applicationFor, final String message) {
		ModelAndView result;

		result = new ModelAndView("applicationFor/cancel");
		result.addObject("applicationFor", applicationFor);
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView createEnterModelAndView(final ApplicationFor applicationFor) {
		ModelAndView result;

		result = this.createEnterModelAndView(applicationFor, null);
		return result;
	}

	private ModelAndView createEnterModelAndView(final ApplicationFor applicationFor, final String message) {
		ModelAndView result;

		result = new ModelAndView("applicationFor/enter");
		result.addObject("applicationFor", applicationFor);
		result.addObject("message", message);

		return result;
	}
	protected ModelAndView createEditModelAndView(final ApplicationFor applicationFor) {
		ModelAndView result;

		result = this.createEditModelAndView(applicationFor, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final ApplicationFor applicationFor, final String message) {
		ModelAndView result;

		result = new ModelAndView("applicationFor/edit");
		result.addObject("applicationFor", applicationFor);
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView createEditModelAndViewCreditCard(final ApplicationFor applicationFor, final String message) {
		ModelAndView result;

		result = new ModelAndView("applicationFor/enter");
		result.addObject("applicationFor", applicationFor);
		result.addObject("message", message);

		return result;
	}

}
