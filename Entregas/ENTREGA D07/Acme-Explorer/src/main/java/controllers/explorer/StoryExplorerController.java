
package controllers.explorer;

import java.util.Collection;

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

import services.ExplorerService;
import services.StoryService;
import services.TripService;
import controllers.AbstractController;
import domain.Attachment;
import domain.Explorer;
import domain.Story;
import domain.Trip;

@Controller
@RequestMapping("/story/explorer")
public class StoryExplorerController extends AbstractController {

	@Autowired
	private ExplorerService	explorerService;
	@Autowired
	private StoryService	storyService;
	@Autowired
	private TripService		tripService;


	public StoryExplorerController() {
		super();
	}

	//List---------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Explorer explorer;
		explorer = this.explorerService.findByPrincipal();

		result = new ModelAndView("story/list");
		result.addObject("stories", explorer.getStories());

		return result;

	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int tripId) {
		ModelAndView result;
		Story story;
		Explorer explorer;
		Trip trip;
		Collection<Trip> trips;

		trip = this.tripService.findOne(tripId);
		explorer = this.explorerService.findByPrincipal();
		trips = this.tripService.findTripsForStory();
		Assert.isTrue(trips.contains(trip), "Cannot commit this operation, because it's illegal");

		story = this.storyService.create(trip);
		result = this.createEditModelAndView(story);
		result.addObject("explorer", explorer);

		return result;
	}

	//Edition------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int storyId) {
		ModelAndView result;
		Story story;
		Explorer explorer;

		explorer = this.explorerService.findByPrincipal();
		story = this.storyService.findOne(storyId);
		Assert.notNull(story);
		Assert.isTrue(story.getExplorer().equals(explorer), "Cannot commit this operation, because it's illegal");
		result = this.createEditModelAndView(story);
		result.addObject("explorer", explorer);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Story story, final BindingResult bindingResult) {
		ModelAndView result;

		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(story, "storyInvalid.commit.error");
		else
			try {
				this.storyService.save(story);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(story, "story.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@ModelAttribute final Story story, final BindingResult bindingResult) {
		ModelAndView result;

		try {
			this.storyService.delete(story);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(story, "story.commit.error");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Story story) {

		ModelAndView result;
		result = this.createEditModelAndView(story, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Story story, final String message) {

		ModelAndView result;
		Collection<Attachment> attachments;

		attachments = this.storyService.urlAttachments(story);

		result = new ModelAndView("story/edit");
		result.addObject("story", story);
		result.addObject("attachments", attachments);
		result.addObject("message", message);

		return result;

	}

}
