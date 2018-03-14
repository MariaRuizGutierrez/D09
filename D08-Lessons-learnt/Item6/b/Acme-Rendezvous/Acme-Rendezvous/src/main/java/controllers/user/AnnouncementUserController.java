
package controllers.user;

import java.util.ArrayList;
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

import services.AnnouncementService;
import services.RendezvouseService;
import services.UserService;
import controllers.AbstractController;
import domain.Announcement;
import domain.Rendezvouse;

@Controller
@RequestMapping("/announcement/user")
public class AnnouncementUserController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private AnnouncementService	announcementService;

	@Autowired
	private RendezvouseService	rendezvouseService;

	@Autowired
	private UserService			userService;


	// Create -----------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int rendezvousId) {
		ModelAndView result;
		Rendezvouse rendezvous;
		Announcement announcement;
		this.userService.checkPrincipal();

		rendezvous = this.rendezvouseService.findOne(rendezvousId);
		announcement = this.announcementService.create(rendezvous);

		result = this.createEditModelAndView(announcement);
		return result;
	}

	//Edition--------------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int announcementId) {
		ModelAndView result;
		Announcement announcement;

		announcement = this.announcementService.findOne(announcementId);

		//Un usuario solo podrá editar sus anuncios.
		if (announcement.getId() != 0) {
			Collection<Announcement> announcementsOfUser;
			announcementsOfUser = this.announcementService.findAnnouncementByUserId();
			Assert.isTrue(announcementsOfUser.contains(announcement), "Cannot commit this operation, because it's illegal");
		}

		Assert.notNull(announcement);
		result = this.createEditModelAndView(announcement);
		return result;
	}

	// Save -----------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Announcement announcement, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(announcement);
		else
			try {
				this.announcementService.save(announcement);
				result = new ModelAndView("redirect:list.do?rendezvousId=" + announcement.getRendezvouse().getId());
			} catch (Throwable oops) {
				result = this.createEditModelAndView(announcement, "announcement.commit.error");
			}
		return result;
	}

	// Listing ----------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int rendezvousId) {
		ModelAndView result;
		Collection<Announcement> announcements;

		announcements = new ArrayList<>(this.announcementService.findAnnouncementByRendezvousId(rendezvousId));

		result = new ModelAndView("announcement/list");
		result.addObject("announcements", announcements);
		result.addObject("editar", true);
		result.addObject("boton", true);
		result.addObject("requestURI", "announcement/user/list.do");

		return result;
	}

	@RequestMapping(value = "/listb", method = RequestMethod.GET)
	public ModelAndView listb(@RequestParam int rendezvousId) {
		ModelAndView result;
		Collection<Announcement> announcements;

		announcements = new ArrayList<>(this.announcementService.findAnnouncementByRendezvousId(rendezvousId));

		result = new ModelAndView("announcement/listb");
		result.addObject("announcements", announcements);
		result.addObject("editar", true);
		result.addObject("boton", true);
		result.addObject("requestURI", "announcement/user/listb.do");

		return result;
	}

	//announcement/user/listAll.do
	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Announcement> announcements;

		announcements = new ArrayList<>(this.announcementService.findAnnouncementByUserIdForRendezvousesAssits());

		result = new ModelAndView("announcement/list");
		result.addObject("announcements", announcements);
		result.addObject("boton", false);
		result.addObject("editar", false);
		result.addObject("requestURI", "announcement/user/listAll.do");

		return result;
	}

	//Auxiliares ---------------------------------------------------------

	protected ModelAndView createEditModelAndView(Announcement announcement) {

		Assert.notNull(announcement);
		ModelAndView result;
		result = this.createEditModelAndView(announcement, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(Announcement announcement, String messageCode) {
		assert announcement != null;

		ModelAndView result;

		result = new ModelAndView("announcement/edit");
		result.addObject("announcement", announcement);
		result.addObject("message", messageCode);
		return result;

	}
}
