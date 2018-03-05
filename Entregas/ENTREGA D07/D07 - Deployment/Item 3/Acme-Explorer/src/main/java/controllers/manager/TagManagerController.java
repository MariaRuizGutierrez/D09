
package controllers.manager;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.TagService;
import domain.Tag;

@Controller
@RequestMapping("/tag/manager_")
public class TagManagerController {

	// Services ---------------------------------------------------------------
	@Autowired
	private TagService	tagService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int tripId) {
		ModelAndView result;
		Collection<Tag> tags;
		Collection<Tag> tagsInTrip;

		tagsInTrip = new ArrayList<Tag>(this.tagService.findAllTagByTripId(tripId));
		tags = new ArrayList<Tag>(this.tagService.findAll());
		tags.removeAll(tagsInTrip);

		result = new ModelAndView("tag/list");
		result.addObject("tags", tags);
		result.addObject("tripId", tripId);
		result.addObject("requestURI", "tag/manager_/list.do");
		return result;
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int tagId) {
		ModelAndView result;
		Tag tag;

		tag = this.tagService.findOne(tagId);
		Assert.notNull(tag);
		result = this.createEditModelAndView(tag);
		result.addObject("tag", tag);

		return result;
	}

	// Ancillary methods ------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Tag tag) {
		ModelAndView result;

		result = this.createEditModelAndView(tag, null);
		return result;
	}

	private ModelAndView createEditModelAndView(final Tag tag, final String message) {
		ModelAndView result;
		Collection<Tag> tags;

		tags = new ArrayList<Tag>(this.tagService.findAll());
		result = new ModelAndView("tag/add");
		//result.addObject("trip", trip);
		result.addObject("tags", tags);
		result.addObject("message", message);

		return result;
	}

}
