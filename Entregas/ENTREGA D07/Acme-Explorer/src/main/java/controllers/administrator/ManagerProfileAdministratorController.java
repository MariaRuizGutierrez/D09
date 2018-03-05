
package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ManagerService;
import controllers.AbstractController;
import domain.Manager;

@Controller
@RequestMapping("/managerProfile/administrator")
public class ManagerProfileAdministratorController extends AbstractController {

	// Services---------------------------------------------------------

	@Autowired
	private ManagerService	managerService;


	//Constructor--------------------------------------------------------

	public ManagerProfileAdministratorController() {
		super();
	}

	//Edition------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Manager manager;

		manager = this.managerService.create();
		Assert.notNull(manager);
		result = this.createEditModelAndView(manager);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Manager manager, final BindingResult bindingResult) {
		ModelAndView result;

		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(manager);
		else
			try {
				this.managerService.save(manager);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("could not execute statement; SQL [n/a]; constraint [null]" + "; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement"))
					result = this.createEditModelAndView(manager, "manager.commit.error.duplicateProfile");
				else
					result = this.createEditModelAndView(manager, "manager.commit.error");
			}

		return result;
	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Manager manager) {

		ModelAndView result;
		result = this.createEditModelAndView(manager, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Manager manager, final String message) {

		ModelAndView result;

		result = new ModelAndView("manager/edit");
		result.addObject("manager", manager);
		result.addObject("message", message);
		result.addObject("RequestURI", "managerProfile/administrator/edit.do");

		return result;

	}
}
