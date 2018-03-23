
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AnnouncementService;
import services.QuestionService;
import services.RendezvouseService;
import controllers.AbstractController;
import domain.Announcement;
import domain.Question;
import domain.Rendezvouse;
import domain.ServiceOffered;

@Controller
@RequestMapping("/rendezvous/administrator")
public class RendezvousAdministratorController extends AbstractController {

	// Services---------------------------------------------------------

	@Autowired
	private RendezvouseService	rendezvouseService;

	@Autowired
	private QuestionService		questionService;
	@Autowired
	private AnnouncementService	announcementService;


	//constructor-------------------------------------------------------------------------
	public RendezvousAdministratorController() {
		super();
	}

	//Display-----------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int rendezvousId) {
		ModelAndView result;
		Rendezvouse ren = new Rendezvouse();
		Collection<Announcement> announcements;
		Collection<ServiceOffered> services;
		Collection<Question> questions;
		Collection<Rendezvouse> similarRendezvouses;

		ren = this.rendezvouseService.findOne(rendezvousId);
		questions = this.questionService.findAllQuestionsByRendezvous(rendezvousId);
		services = this.rendezvouseService.findAllServicesByRendezvous(rendezvousId);
		announcements = this.announcementService.findAnnouncementByRendezvousId(rendezvousId);
		similarRendezvouses = ren.getSimilarRendezvouses();

		result = new ModelAndView("rendezvous/display");
		result.addObject("rendezvous", ren);
		result.addObject("announcements", announcements);
		result.addObject("services", services);
		result.addObject("questions", questions);
		result.addObject("similarRendezvouses", similarRendezvouses);
		result.addObject("requestURI", "rendezvous/administrator/display.do");

		return result;
	}

	//Listing-----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Rendezvouse> rendezvous;
		rendezvous = this.rendezvouseService.findAll();
		result = new ModelAndView("rendezvous/list");
		result.addObject("rendezvous", rendezvous);
		result.addObject("requestURI", "/rendezvous/administrator/list.do");
		return result;

	}

	//Delete---------------------------------------------------------------------
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(final int rendezvousId) {
		ModelAndView result;
		final Rendezvouse rendezvouse;

		rendezvouse = this.rendezvouseService.findOne(rendezvousId);
		Assert.notNull(rendezvouse);
		try {
			this.rendezvouseService.delete(rendezvouse);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.listWithMessage("rendezvouse.commit.error");
		}

		return result;
	}

	//ancially methods---------------------------------------------------------------------------

	protected ModelAndView listWithMessage(final String message) {
		final ModelAndView result;
		Collection<Rendezvouse> rendezvous;
		rendezvous = this.rendezvouseService.findAll();
		result = new ModelAndView("rendezvous/list");
		result.addObject("rendezvous", rendezvous);
		result.addObject("requestURI", "/rendezvous/administrator/list.do");
		result.addObject("message", message);
		return result;

	}
}
