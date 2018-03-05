
package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.LegalTextService;
import services.TripService;
import controllers.AbstractController;
import domain.LegalText;
import domain.Trip;

@Controller
@RequestMapping("/legalText/administrator")
public class LegalTextAdministratorController extends AbstractController {

	//Services---------------------

	@Autowired
	private LegalTextService	legalTextService;

	@Autowired
	private TripService			tripService;


	//Listing---------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<LegalText> legalTexts;

		legalTexts = this.legalTextService.findAll();

		result = new ModelAndView("legalText/list");
		result.addObject("legalTexts", legalTexts);

		return result;

	}

	//Displaying----------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int legalTextId) {

		ModelAndView result;
		LegalText legalText;

		legalText = this.legalTextService.findOne(legalTextId);

		result = new ModelAndView("legalText/display");
		result.addObject("legalText", legalText);

		return result;
	}

	//Creating----------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		LegalText legalText;
		Collection<Trip> trips;

		trips = this.tripService.findAll();

		legalText = this.legalTextService.create();
		result = this.createEditModelAndView(legalText);
		result.addObject("trips", trips);

		return result;

	}
	//Editing------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int legalTextId) {

		ModelAndView result;
		LegalText legalText;

		legalText = this.legalTextService.findOne(legalTextId);

		Assert.notNull(legalText);

		result = this.createEditModelAndView(legalText);

		return result;

	}

	//Saving--------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid LegalText legalText, BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(legalText);
		else
			try {
				this.legalTextService.save(legalText);
				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(legalText, "legalText.commit.error");
			}

		return result;

	}

	//Deleting-----------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(LegalText legalText, BindingResult binding) {

		ModelAndView result;

		try {
			this.legalTextService.delete(legalText);
			result = new ModelAndView("redirect:list.do");
		} catch (Throwable oops) {
			result = this.createEditModelAndView(legalText, "legalText.commit.error");
		}

		return result;
	}

	//Auxiliary-----------------------

	protected ModelAndView createEditModelAndView(LegalText legalText) {

		Assert.notNull(legalText);
		ModelAndView result;
		result = this.createEditModelAndView(legalText, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(LegalText legalText, String messageCode) {
		assert legalText != null;

		ModelAndView result;
		Collection<Trip> trips;

		trips = this.tripService.findAll();

		result = new ModelAndView("legalText/edit");
		result.addObject("legalText", legalText);
		result.addObject("trips", trips);
		result.addObject("message", messageCode);

		return result;

	}

	//Association trip

	@RequestMapping(value = "/trip", method = RequestMethod.GET)
	public ModelAndView trip(@RequestParam int legalTextId) {
		ModelAndView result;
		LegalText legalText;

		legalText = this.legalTextService.findOne(legalTextId);

		result = this.createEditModelAndViewTrip(legalText);

		return result;
	}

	@RequestMapping(value = "/trip", method = RequestMethod.POST, params = "saveTrip")
	public ModelAndView saveTrip(@Valid LegalText legalText, BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndViewTrip(legalText);
		else
			try {
				this.legalTextService.addTrip(legalText);
				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				if (oops.getMessage().equals("could not execute statement; SQL [n/a]; constraint [null]" + "; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement"))
					result = this.createEditModelAndView(legalText, "legalText.duplicate");
				else
					result = this.createEditModelAndViewTrip(legalText, "legalText.commit.error");
			}

		return result;

	}

	protected ModelAndView createEditModelAndViewTrip(LegalText legalText) {

		Assert.notNull(legalText);
		ModelAndView result;
		result = this.createEditModelAndViewTrip(legalText, null);
		return result;
	}

	protected ModelAndView createEditModelAndViewTrip(LegalText legalText, String messageCode) {
		assert legalText != null;

		ModelAndView result;
		Collection<Trip> trips;

		trips = this.tripService.findAllTripsPublished();

		result = new ModelAndView("legalText/editTrip");
		result.addObject("legalText", legalText);
		result.addObject("trips", trips);
		result.addObject("message", messageCode);

		return result;

	}

}
