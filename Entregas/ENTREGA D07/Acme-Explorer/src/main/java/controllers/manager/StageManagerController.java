
package controllers.manager;

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

import services.ManagerService;
import services.StageService;
import services.TripService;
import controllers.AbstractController;
import domain.Manager;
import domain.Stage;
import domain.Trip;

@Controller
@RequestMapping(value = "/stage/manager")
public class StageManagerController extends AbstractController {

	//Services------------------------

	@Autowired
	private StageService	stageService;

	@Autowired
	private TripService		tripService;

	@Autowired
	private ManagerService	mangerService;


	//Listing--------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int tripId) {

		ModelAndView result;
		Collection<Stage> stages;
		double totalPrice;

		totalPrice = 0;

		stages = this.stageService.stagesOfTrip(tripId);

		for (Stage stage : stages) {
			stage = this.stageService.findOne(stage.getId());
			this.stageService.setTotalPriceStage(stage);
			totalPrice = stage.getTotalPrice();
		}

		result = new ModelAndView("stage/list");
		result.addObject("requestURI", "stage/manager/list.do");
		result.addObject("stages", stages);
		result.addObject("totalPrice", totalPrice);
		result.addObject("tripId", tripId);

		return result;

	}
	// Creation ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int tripId) {
		ModelAndView result;
		Stage stage;
		Trip trip;
		Manager managerPrincipal;
		Manager managerStage;

		trip = this.tripService.findOne(tripId);
		stage = this.stageService.create();
		stage.setTrip(trip);
		managerPrincipal = this.mangerService.findByPrincipal();
		managerStage = trip.getManager();

		Assert.isTrue(managerPrincipal.equals(managerStage), "Cannot commit this operation because it's ilegal");

		result = this.createEditModelAndView(stage);

		return result;
	}

	//Editing------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int stageId) {

		ModelAndView result;
		Stage stage;

		stage = this.stageService.findOne(stageId);

		Assert.notNull(stage);

		result = this.createEditModelAndView(stage);

		return result;

	}

	//Saving---------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Stage stage, BindingResult binding) {
		ModelAndView result;
		Date now;

		now = new Date();
		if (binding.hasErrors())
			result = this.createEditModelAndView(stage);
		else
			try {
				Assert.isTrue(!stage.getTrip().getPublicationDate().before(now));
				this.stageService.save(stage);
				result = new ModelAndView("redirect:list.do?tripId=" + stage.getTrip().getId());
			} catch (Throwable oops) {
				if (oops.getMessage().equals("The number of this stage must be different"))
					result = this.createEditModelAndView(stage, "stage.commit.error.duplicateNumber");
				else
					result = this.createEditModelAndView(stage, "stage.commit.error");
			}

		return result;

	}

	//Delete 

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Stage stage, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(stage);
		else
			try {
				this.stageService.delete(stage);

				result = new ModelAndView("redirect:list.do?tripId=" + stage.getTrip().getId());
			} catch (Throwable oops) {
				result = this.createEditModelAndView(stage, "stage.commit.error");
			}

		return result;

	}

	//auxiliares--------

	protected ModelAndView createEditModelAndView(Stage stage) {

		Assert.notNull(stage);
		ModelAndView result;
		result = this.createEditModelAndView(stage, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(Stage stage, String messageCode) {
		assert stage != null;

		ModelAndView result;

		result = new ModelAndView("stage/edit");
		result.addObject("stage", stage);
		result.addObject("message", messageCode);

		return result;

	}

}
