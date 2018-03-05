
package controllers.manager;

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

import services.ManagerService;
import services.RangerService;
import services.TagService;
import services.TripService;
import services.ValueService;
import controllers.AbstractController;
import domain.Manager;
import domain.Ranger;
import domain.Tag;
import domain.Trip;
import domain.Value;

@Controller
@RequestMapping("/trip/manager_")
public class TripManagerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private TripService		tripService;

	@Autowired
	private ManagerService	managerService;

	@Autowired
	private RangerService	rangerService;

	@Autowired
	private TagService		tagService;

	@Autowired
	private ValueService	valueService;


	// Constructors -----------------------------------------------------------
	public TripManagerController() {

	}

	// Listing ----------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		double price;
		Collection<Trip> trips;
		Manager manager;

		manager = this.managerService.findByPrincipal();

		trips = this.tripService.findAll();
		price = this.tripService.setPriceTrip(trips);
		Date date;
		date = new Date();

		result = new ModelAndView("trip/list");
		result.addObject("trips", trips);
		result.addObject("manager", manager);
		result.addObject("date", date);
		result.addObject("price", price);
		result.addObject("requestURI", "trip/manager_/list.do");
		result.addObject("requestURISearch", "trip/search.do");

		return result;
	}

	// Creation ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Trip trip;
		Manager manager;
		manager = this.managerService.findByPrincipal();
		trip = this.tripService.create(manager);
		result = this.createEditModelAndView(trip);

		return result;
	}
	// Edition ----------------------------------------------------------------

	//Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int tripId) {
		ModelAndView result;
		Trip trip;

		trip = this.tripService.findOneToEdit(tripId);
		Assert.notNull(trip);
		result = this.createEditModelAndView(trip);

		return result;
	}

	//Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Trip trip, final BindingResult binding) {
		ModelAndView result;
		Date dateNow;
		dateNow = new Date();
		if (binding.hasErrors())
			result = this.createEditModelAndView(trip, "trip.save.commit.error");
		else
			try {
				if ((trip.getReasonWhy().trim().isEmpty()) || trip.getReasonWhy() == null)
					Assert.isTrue(trip.getPublicationDate().after(dateNow));
				if (trip.getId() != 0)
					Assert.isTrue((trip.getReasonWhy() == null) || (trip.getReasonWhy().trim().isEmpty()));
				this.tripService.save(trip);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(trip, "trip.commit.error");
			}
		return result;
	}

	//Delete
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Trip trip, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(trip);
		else
			try {
				this.tripService.delete(trip);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(trip, "trip.commit.error");
			}
		return result;
	}

	//Display

	// Ancillary methods ------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Trip trip) {
		ModelAndView result;

		result = this.createEditModelAndView(trip, null);
		return result;
	}

	private ModelAndView createEditModelAndView(final Trip trip, final String message) {
		ModelAndView result;
		Collection<Ranger> rangers;
		rangers = this.rangerService.findAll();
		Collection<Tag> tags;
		tags = new ArrayList<Tag>(this.tagService.findAll());
		result = new ModelAndView("trip/edit");
		result.addObject("trip", trip);
		result.addObject("tags", tags);
		result.addObject("message", message);
		result.addObject("rangers", rangers);
		return result;
	}

	//Cancel----------------------------------------------------------------

	@RequestMapping(value = "/cancelTrip", method = RequestMethod.GET)
	public ModelAndView cancel(@RequestParam final int tripId) {
		ModelAndView result;
		Trip trip;
		Manager manager;

		manager = this.managerService.findByPrincipal();
		trip = this.tripService.findOne(tripId);

		Assert.isTrue(trip.getManager().equals(manager), "Cannot commit this operation, because it's illegal");
		result = this.createCancelModelAndView(trip);
		result.addObject("trip", trip);

		return result;
	}

	@RequestMapping(value = "/cancelTrip", method = RequestMethod.POST, params = "save")
	public ModelAndView cancel(@Valid final Trip trip, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(trip);
		else
			try {

				Assert.isTrue(!trip.getReasonWhy().trim().isEmpty());
				this.tripService.cancel(trip);
				this.tripService.save(trip);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createCancelModelAndView(trip, "trip.cancel.error");
			}
		return result;
	}

	protected ModelAndView createCancelModelAndView(final Trip trip) {
		ModelAndView result;

		result = this.createCancelModelAndView(trip, null);
		return result;
	}

	protected ModelAndView createCancelModelAndView(final Trip trip, final String message) {
		ModelAndView result;

		result = new ModelAndView("trip/cancel");
		result.addObject("trip", trip);
		result.addObject("message", message);

		return result;
	}

	//Tags

	@RequestMapping(value = "/tag/list", method = RequestMethod.GET)
	public ModelAndView listTag(@RequestParam final int tripId) {

		ModelAndView result;
		//Trip trip;
		Collection<Tag> tags;

		//trip = this.tripService.findOne(tripId);
		tags = new ArrayList<>(this.tagService.findAllTagByTripId(tripId));

		result = new ModelAndView("tag/list");
		result.addObject("tags", tags);
		result.addObject("requestURI", "trip/manager_/tag/list.do");

		return result;

	}

	//Editing-----------------

	@RequestMapping(value = "/tag/edit", method = RequestMethod.GET)
	public ModelAndView editTag(@RequestParam final int tagId) {

		ModelAndView result;
		Tag tag;
		Collection<Value> values;

		tag = this.tagService.findOne(tagId);
		values = this.valueService.findAll();
		Assert.notNull(tag);

		result = this.createEditModelAndView(tag);
		result.addObject("values", values);

		return result;

	}
	//auxiliary------------------

	protected ModelAndView createEditModelAndView(final Tag tag) {

		Assert.notNull(tag);
		ModelAndView result;
		result = this.createEditModelAndView(tag, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Tag tag, final String messageCode) {
		assert tag != null;

		ModelAndView result;
		Collection<Tag> tags;

		tags = this.tagService.findAll();

		result = new ModelAndView("tag/edit");
		result.addObject("tag", tag);
		result.addObject("tags", tags);
		result.addObject("message", messageCode);

		return result;

	}

}
