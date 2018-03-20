
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.RendezvouseService;
import controllers.AbstractController;
import domain.Rendezvouse;

@Controller
@RequestMapping("/rendezvous/administrator")
public class RendezvousAdministratorController extends AbstractController {

	// Services---------------------------------------------------------

	@Autowired
	private RendezvouseService	rendezvouseService;


	//constructor-------------------------------------------------------------------------
	public RendezvousAdministratorController() {
		super();
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
