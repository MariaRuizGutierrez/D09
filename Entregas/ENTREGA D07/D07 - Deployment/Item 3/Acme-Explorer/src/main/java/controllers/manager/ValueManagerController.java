
package controllers.manager;

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

import services.TagService;
import services.TripService;
import services.ValueService;
import domain.Tag;
import domain.Trip;
import domain.Value;

@Controller
@RequestMapping("/value/manager_")
public class ValueManagerController {

	// Services ---------------------------------------------------------------
	@Autowired
	private ValueService	valueService;
	@Autowired
	private TagService		tagService;
	@Autowired
	private TripService		tripService;

	public Trip				trip;
	public Tag				tag;


	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int tripId, @RequestParam final int tagId) {
		ModelAndView result;
		Value value = new Value();

		value = this.valueService.create();

		this.tag = this.tagService.findOne(tagId);
		this.trip = this.tripService.findOne(tripId);

		Assert.notNull(value);
		result = this.createEditModelAndView(value);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView enter(@Valid Value value, BindingResult binding) {
		ModelAndView result;
		Date now;

		now = new Date();
		if (binding.hasErrors())
			result = this.createEditModelAndView(value);
		else
			try {
				Trip trip1 = this.trip;
				Tag tag1 = this.tag;
				Assert.isTrue((trip1.getReasonWhy() == null) || (trip1.getReasonWhy().trim().isEmpty()));
				//Continua si la fecha de publicacion es posterior a la actual
				Assert.isTrue(trip1.getPublicationDate().after(now));
				this.valueService.save1(value, trip1, tag1);
				result = new ModelAndView("redirect:../../trip/manager_/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(value, "value.commit.error");
			}
		return result;
	}

	// Ancillary methods ------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Value value) {
		ModelAndView result;

		result = this.createEditModelAndView(value, null);
		return result;
	}

	private ModelAndView createEditModelAndView(final Value value, final String message) {
		ModelAndView result;
		String tripTtitle;
		String tagName;
		Integer tripId = this.trip.getId();
		tripTtitle = this.trip.getTitle();
		tagName = this.tag.getName();

		result = new ModelAndView("value/edit");
		result.addObject("value", value);
		result.addObject("message", message);
		result.addObject("tripTtitle", tripTtitle);
		result.addObject("tagName", tagName);
		result.addObject("tripId", tripId);
		result.addObject("RequestURI", "value/manager_/edit.do");

		return result;
	}
}
