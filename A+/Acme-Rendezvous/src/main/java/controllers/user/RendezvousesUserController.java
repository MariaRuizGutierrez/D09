
package controllers.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AnnouncementService;
import services.QuestionService;
import services.RendezvouseService;
import services.UserService;
import controllers.AbstractController;
import domain.Announcement;
import domain.Question;
import domain.Rendezvouse;
import domain.ServiceOffered;
import domain.User;

@Controller
@RequestMapping("/rendezvous/user")
public class RendezvousesUserController extends AbstractController {

	//service---------------------------------------------------------------------------
	@Autowired
	private RendezvouseService	rendezvouseService;

	@Autowired
	private UserService			userService;

	@Autowired
	private AnnouncementService	announcementService;

	@Autowired
	private QuestionService		questionService;


	//constructor-------------------------------------------------------------------------
	public RendezvousesUserController() {
		super();
	}
	//Listing-----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		this.userService.checkPrincipal();
		final ModelAndView result;
		Collection<Rendezvouse> rendezvous;
		rendezvous = this.rendezvouseService.findRendezvousesCreatedByUser();
		result = new ModelAndView("rendezvous/list");
		result.addObject("rendezvous", rendezvous);
		result.addObject("requestURI", "rendezvous/user/list.do");
		return result;

	}

	@RequestMapping(value = "/listnotasis", method = RequestMethod.GET)
	public ModelAndView listasis() {
		final ModelAndView result;
		this.userService.checkPrincipal();
		Collection<Rendezvouse> rendezvous;
		User principal;
		principal = this.userService.findByPrincipal();
		rendezvous = this.rendezvouseService.CancelMyassistantToRendezvouse(principal);
		result = new ModelAndView("rendezvous/listasis");
		result.addObject("rendezvous", rendezvous);
		result.addObject("assist", true);
		result.addObject("requestURI", "rendezvous/user/listnotasis.do");
		return result;

	}

	@RequestMapping(value = "/listasis", method = RequestMethod.GET)
	public ModelAndView listnotasis() {
		this.userService.checkPrincipal();
		final ModelAndView result;
		Collection<Rendezvouse> rendezvous;
		User principal;
		principal = this.userService.findByPrincipal();
		if (this.rendezvouseService.calculateYearsOld(principal.getBirthDate()) < 18)
			rendezvous = this.rendezvouseService.assistantToRendezvouseNot18(principal);
		else
			rendezvous = this.rendezvouseService.assistantToRendezvouse(principal);
		result = new ModelAndView("rendezvous/listasis");
		result.addObject("rendezvous", rendezvous);
		result.addObject("assist", false);
		result.addObject("requestURI", "rendezvous/user/listasis.do");
		return result;

	}

	@RequestMapping(value = "/notassist", method = RequestMethod.GET)
	public ModelAndView notassist(@RequestParam final int rendezvousId) {
		ModelAndView result;

		try {
			this.rendezvouseService.unassist(rendezvousId);
			result = this.listnotasis();
			result.addObject("message", "rendezvous.commit.ok");
		} catch (final Throwable oops) {
			result = this.listasis();
			result.addObject("message", "rendezvous.commit.error");
		}

		return result;

	}

	@RequestMapping(value = "/assist", method = RequestMethod.GET)
	public ModelAndView assist(@RequestParam final int rendezvousId) {
		ModelAndView result;

		try {
			this.rendezvouseService.assist(rendezvousId);
			result = this.listasis();
			result.addObject("message", "rendezvous.commit.ok");
		} catch (final Throwable oops) {
			result = this.listnotasis();
			result.addObject("message", "rendezvous.commit.error");
		}

		return result;

	}

	//Listing deleted rendezvouses-----------------------------------------------------------

	@RequestMapping(value = "/list-deleted", method = RequestMethod.GET)
	public ModelAndView list2() {

		ModelAndView result;
		Collection<Rendezvouse> rens;
		User user;

		user = this.userService.findByPrincipal();
		rens = this.rendezvouseService.AllRendezvousesDeleted(user.getId());

		result = new ModelAndView("rendezvous/listasis");
		result.addObject("rendezvous", rens);
		result.addObject("requestURI", "rendezvous/user/list-deleted.do");

		return result;

	}

	// Display ----------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int rendezvousId) {
		ModelAndView result;
		Rendezvouse ren = new Rendezvouse();
		User userConnected;
		Collection<Announcement> announcements;
		Collection<ServiceOffered> services;
		Collection<Question> questions;
		Collection<Rendezvouse> similarRendezvouses;

		userConnected = this.userService.findByPrincipal();
		ren = this.rendezvouseService.findOne(rendezvousId);
		questions = this.questionService.findAllQuestionsByRendezvous(rendezvousId);
		services = this.rendezvouseService.findAllServicesByRendezvous(rendezvousId);
		announcements = this.announcementService.findAnnouncementByRendezvousId(rendezvousId);
		if (this.rendezvouseService.calculateYearsOld(userConnected.getBirthDate()) < 18)
			similarRendezvouses = this.rendezvouseService.findAllSimilarForNoAuthenticathed(rendezvousId);
		else
			similarRendezvouses = ren.getSimilarRendezvouses();

		if (ren.isForAdult() == true)
			Assert.isTrue(this.rendezvouseService.calculateYearsOld(userConnected.getBirthDate()) > 18);
		result = new ModelAndView("rendezvous/display");
		result.addObject("rendezvous", ren);
		result.addObject("announcements", announcements);
		result.addObject("services", services);
		result.addObject("questions", questions);
		result.addObject("similarRendezvouses", similarRendezvouses);
		result.addObject("requestURI", "rendezvous/user/display.do");

		return result;
	}

	//Creation---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Rendezvouse rendezvouse;

		rendezvouse = this.rendezvouseService.create();
		result = this.createEditModelAndView(rendezvouse);

		return result;
	}
	//Edition--------------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int rendezvouseId) {
		ModelAndView result;
		Rendezvouse rendezvouse;
		User user;

		user = this.userService.findByPrincipal();
		rendezvouse = this.rendezvouseService.findOne(rendezvouseId);
		Assert.isTrue(user.getRendezvousesCreated().contains(rendezvouse), "Cannot commit this operation, because it's illegal");
		Assert.isTrue(rendezvouse.isDraftMode() == true, "Cannot commit this operation, because it's illegal");
		Assert.isTrue(rendezvouse.isDeleted() == false, "Cannot commit this operation, because the rendezvous is deleted");
		Assert.notNull(rendezvouse);
		result = this.createEditModelAndView(rendezvouse);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Rendezvouse rendezvous, final BindingResult bindingResult) {
		ModelAndView result;

		rendezvous = this.rendezvouseService.reconstruct(rendezvous, bindingResult);
		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(rendezvous);
		else
			try {
				this.rendezvouseService.save(rendezvous);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("future"))
					result = this.createEditModelAndView(rendezvous, "rendezvous.future.error");
				else if (oops.getMessage().equals("user younger"))
					result = this.createEditModelAndView(rendezvous, "menor.error");
				else
					result = this.createEditModelAndView(rendezvous, "rendezvouse.commit.error");
			}

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@ModelAttribute final Rendezvouse rendezvouse, final BindingResult bindingResult) {
		ModelAndView result;

		try {
			this.rendezvouseService.delete(rendezvouse);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(rendezvouse, "rendezvouse.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "deletevirtual")
	public ModelAndView deletevirtual(@ModelAttribute Rendezvouse rendezvouse, final BindingResult bindingResult) {
		ModelAndView result;

		rendezvouse = this.rendezvouseService.reconstruct(rendezvouse, bindingResult);

		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(rendezvouse);
		else
			try {
				this.rendezvouseService.deletevirtual(rendezvouse);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(rendezvouse, "rendezvouse.commit.error");
			}

		return result;

	}

	@RequestMapping(value = "/editSimilar", method = RequestMethod.GET)
	public ModelAndView editSimilar(@RequestParam final int rendezvouseId) {
		ModelAndView result;
		Rendezvouse rendezvouse;
		User user;
		Date now;

		now = new Date();
		user = this.userService.findByPrincipal();
		rendezvouse = this.rendezvouseService.findOne(rendezvouseId);
		Assert.isTrue(user.getRendezvousesCreated().contains(rendezvouse), "Cannot commit this operation, because it's illegal");
		Assert.notNull(rendezvouse);
		Assert.isTrue(!rendezvouse.getOrganisedMoment().before(now), "Cannot commit this operation because it's illegal");
		result = this.createEditSimilarModelAndView(rendezvouse);
		return result;
	}

	@RequestMapping(value = "/editNotSimilar", method = RequestMethod.GET)
	public ModelAndView editNotSimilar(@RequestParam final int rendezvouseId) {
		ModelAndView result;
		Rendezvouse rendezvouse;
		User user;
		Date now;

		now = new Date();
		user = this.userService.findByPrincipal();
		rendezvouse = this.rendezvouseService.findOne(rendezvouseId);
		Assert.isTrue(user.getRendezvousesCreated().contains(rendezvouse), "Cannot commit this operation, because it's illegal");
		Assert.notNull(rendezvouse);
		Assert.isTrue(!rendezvouse.getOrganisedMoment().before(now), "Cannot commit this operation because it's illegal");
		result = this.createEditNotSimilarModelAndView(rendezvouse);
		return result;
	}

	@RequestMapping(value = "/editSimilar", method = RequestMethod.POST, params = "link")
	public ModelAndView editSimilarLink(Rendezvouse rendezvous, final BindingResult bindingResult) {
		ModelAndView result;

		rendezvous = this.rendezvouseService.reconstruct(rendezvous, bindingResult);
		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(rendezvous);
		else
			try {
				this.rendezvouseService.linkSimilar(rendezvous);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditSimilarModelAndView(rendezvous, "rendezvouse.commit.error");
			}
		return result;

	}

	@RequestMapping(value = "/editSimilar", method = RequestMethod.POST, params = "unlink")
	public ModelAndView editSimilarUnlink(Rendezvouse rendezvous, final BindingResult bindingResult) {
		ModelAndView result;

		rendezvous = this.rendezvouseService.reconstruct(rendezvous, bindingResult);
		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(rendezvous);
		else
			try {
				this.rendezvouseService.unlinkSimilar(rendezvous);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditSimilarModelAndView(rendezvous, "rendezvouse.commit.error");
			}
		return result;

	}

	//ancially methods---------------------------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Rendezvouse rendezvouse) {
		Assert.notNull(rendezvouse);
		ModelAndView result;
		result = this.createEditModelAndView(rendezvouse, null);
		return result;

	}

	protected ModelAndView createEditModelAndView(final Rendezvouse rendezvouse, final String message) {
		Assert.notNull(rendezvouse);
		Collection<Rendezvouse> similarRendezvouses;
		ModelAndView result;

		User user;

		user = this.userService.findByPrincipal();
		result = new ModelAndView("rendezvous/edit");

		if (this.rendezvouseService.calculateYearsOld(user.getBirthDate()) < 18)
			similarRendezvouses = this.rendezvouseService.findAllRendezvousesNotDeletedForMinorExceptRendezvousId(rendezvouse.getId());
		else
			similarRendezvouses = this.rendezvouseService.findAllRendezvousesNotDeletedExceptRendezvousId(rendezvouse.getId());
		result.addObject("rendezvouse", rendezvouse);
		result.addObject("similarRendezvouses", similarRendezvouses);
		result.addObject("message", message);
		return result;

	}

	protected ModelAndView createEditSimilarModelAndView(final Rendezvouse rendezvouse) {
		Assert.notNull(rendezvouse);
		ModelAndView result;
		result = this.createEditSimilarModelAndView(rendezvouse, null);
		return result;

	}

	protected ModelAndView createEditSimilarModelAndView(final Rendezvouse rendezvouse, final String message) {
		Assert.notNull(rendezvouse);
		Collection<Rendezvouse> notSimilarRendezvouses;
		Collection<Rendezvouse> similarRendezvouses;
		User user;
		ModelAndView result;

		user = this.userService.findByPrincipal();
		result = new ModelAndView("rendezvous/editNotSimilar");
		notSimilarRendezvouses = this.rendezvouseService.findAllRendezvousesNotDeletedExceptRendezvousId(rendezvouse.getId());
		notSimilarRendezvouses.removeAll(rendezvouse.getSimilarRendezvouses());
		similarRendezvouses = rendezvouse.getSimilarRendezvouses();
		if (this.rendezvouseService.calculateYearsOld(user.getBirthDate()) < 18)
			for (final Rendezvouse r : new ArrayList<Rendezvouse>(similarRendezvouses))
				if (r.isDeleted() == true || r.isDraftMode() == true || r.isForAdult() == true)
					similarRendezvouses.remove(r);

		result.addObject("rendezvouse", rendezvouse);
		result.addObject("similarRendezvouses", similarRendezvouses);
		result.addObject("notSimilarRendezvouses", notSimilarRendezvouses);
		result.addObject("message", message);
		return result;

	}

	protected ModelAndView createEditNotSimilarModelAndView(final Rendezvouse rendezvouse) {
		Assert.notNull(rendezvouse);
		ModelAndView result;
		result = this.createEditNotSimilarModelAndView(rendezvouse, null);
		return result;

	}

	protected ModelAndView createEditNotSimilarModelAndView(final Rendezvouse rendezvouse, final String message) {
		Assert.notNull(rendezvouse);
		Collection<Rendezvouse> notSimilarRendezvouses;
		User user;
		ModelAndView result;

		user = this.userService.findByPrincipal();
		result = new ModelAndView("rendezvous/editSimilar");
		notSimilarRendezvouses = this.rendezvouseService.findAllRendezvousesNotDeletedExceptRendezvousId(rendezvouse.getId());
		notSimilarRendezvouses.removeAll(rendezvouse.getSimilarRendezvouses());
		if (this.rendezvouseService.calculateYearsOld(user.getBirthDate()) < 18)
			for (final Rendezvouse r : new ArrayList<Rendezvouse>(notSimilarRendezvouses))
				if (r.isDeleted() == true || r.isDraftMode() == true || r.isForAdult() == true)
					notSimilarRendezvouses.remove(r);

		result.addObject("rendezvouse", rendezvouse);
		result.addObject("notSimilarRendezvouses", notSimilarRendezvouses);
		result.addObject("message", message);
		return result;

	}
}
