
package controllers.user;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.RendezvouseService;
import services.RequestService;
import services.UserService;
import controllers.AbstractController;
import domain.Rendezvouse;
import domain.User;

@Controller
@RequestMapping("/request/user")
public class RequestUserController extends AbstractController {

	//service---------------------------------------------------------------------------

	@Autowired
	private RequestService		requestService;

	@Autowired
	private RendezvouseService	rendezvouseService;

	@Autowired
	private UserService			userService;


	public RequestUserController() {
		super();
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
				result = this.createEditModelAndView(rendezvous, "rendezvouse.commit.error");
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
		result = new ModelAndView("rendezvous/edit");
		similarRendezvouses = this.rendezvouseService.findAllRendezvousesNotDeletedExceptRendezvousId(rendezvouse.getId());

		result.addObject("rendezvouse", rendezvouse);
		result.addObject("similarRendezvouses", similarRendezvouses);
		result.addObject("message", message);
		return result;

	}
}
