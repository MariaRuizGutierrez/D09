
package controllers;

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
import services.ServiceOfferedService;
import services.UserService;
import domain.Announcement;
import domain.Question;
import domain.Rendezvouse;
import domain.ServiceOffered;
import domain.User;

@Controller
@RequestMapping("/rendezvous")
public class RendezvousController extends AbstractController {

	// Services---------------------------------------------------------

	@Autowired
	private RendezvouseService		rendezvouseService;

	@Autowired
	private UserService				userService;

	@Autowired
	private AnnouncementService		announcementService;

	@Autowired
	private QuestionService			questionService;

	@Autowired
	private ServiceOfferedService	serviceOfferedService;


	//Constructor--------------------------------------------------------

	public RendezvousController() {
		super();
	}

	//Listing-----------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int userId) {

		ModelAndView result;
		Collection<Rendezvouse> rens;

		rens = this.rendezvouseService.findRendezvousesAssitedByUser2(userId);

		result = new ModelAndView("rendezvous/list");
		result.addObject("rendezvous", rens);
		result.addObject("requestURI", "rendezvous/list.do");

		return result;

	}

	@RequestMapping(value = "/listAssistant", method = RequestMethod.GET)
	public ModelAndView listAssistant(@RequestParam int userId) {

		ModelAndView result;
		Collection<Rendezvouse> rens;

		rens = this.rendezvouseService.ListOfRendezvousAssistantUserId(userId);

		result = new ModelAndView("rendezvous/list");
		result.addObject("rendezvous", rens);
		result.addObject("requestURI", "rendezvous/list.do");

		return result;

	}

	@RequestMapping(value = "/listSimilar", method = RequestMethod.GET)
	public ModelAndView listSimilar(@RequestParam int rendezvousId) {

		ModelAndView result;
		Collection<Rendezvouse> rendezvouses;

		rendezvouses = this.rendezvouseService.findAllSimilarForNoAuthenticathed(rendezvousId);

		result = new ModelAndView("rendezvous/list");
		result.addObject("rendezvous", rendezvouses);
		result.addObject("requestURI", "rendezvous/list.do");

		return result;

	}

	@RequestMapping(value = "/list-unregister", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<Rendezvouse> rendezvous;

		rendezvous = this.rendezvouseService.findAllMinusAdultAndFinalMode();

		result = new ModelAndView("rendezvous/list");
		result.addObject("rendezvous", rendezvous);
		result.addObject("requestURI", "rendezvous/list-unregister.do");

		return result;

	}

	@RequestMapping(value = "/listAssistants", method = RequestMethod.GET)
	public ModelAndView list2(@RequestParam int rendezvousId) {

		ModelAndView result;
		Collection<User> assistants;

		assistants = this.rendezvouseService.findAllAssistantsByRendezvous(rendezvousId);

		result = new ModelAndView("user/list");
		result.addObject("users", assistants);
		result.addObject("requestURI", "rendezvous/listAssistants.do");

		return result;

	}

	@RequestMapping(value = "/listMaker", method = RequestMethod.GET)
	public ModelAndView list3(@RequestParam int rendezvousId) {

		ModelAndView result;
		User user;

		user = this.userService.findUserByRendezvousId(rendezvousId);

		result = new ModelAndView("user/display");
		result.addObject("user", user);
		result.addObject("requestUri", "rendezvous/listMaker.do");

		return result;

	}

	@RequestMapping(value = "/category/list", method = RequestMethod.GET)
	public ModelAndView listCategory(@RequestParam final int categoryId) {

		ModelAndView result;
		Collection<Rendezvouse> rendezvouses;

		rendezvouses = this.rendezvouseService.findRendezvousByCategory(categoryId);

		result = new ModelAndView("rendezvous/list");
		result.addObject("rendezvous", rendezvouses);
		result.addObject("requestURI", "rendezvous/category/list.do");

		return result;
	}

	// Display ----------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int rendezvousId) {
		ModelAndView result;

		Collection<Announcement> announcements;
		Collection<ServiceOffered> services;
		Collection<Question> questions;
		Collection<Rendezvouse> similarRendezvouses;

		Rendezvouse ren;

		services = this.serviceOfferedService.findAllServicesAvailableByRendezvous(rendezvousId);

		announcements = this.announcementService.findAnnouncementByRendezvousId(rendezvousId);
		questions = this.questionService.findAllQuestionsByRendezvous(rendezvousId);
		ren = this.rendezvouseService.findOne(rendezvousId);
		Assert.isTrue(ren.isForAdult() == false);

		similarRendezvouses = this.rendezvouseService.findAllSimilarForNoAuthenticathed(rendezvousId);

		result = new ModelAndView("rendezvous/display");
		result.addObject("rendezvous", ren);
		result.addObject("announcements", announcements);
		result.addObject("services", services);
		result.addObject("similarRendezvouses", similarRendezvouses);
		result.addObject("questions", questions);

		result.addObject("requestURI", "rendezvous/user/display.do");

		return result;
	}

}
