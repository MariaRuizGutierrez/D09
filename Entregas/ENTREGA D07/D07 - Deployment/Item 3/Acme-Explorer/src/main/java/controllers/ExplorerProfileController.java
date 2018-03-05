
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ExplorerService;
import domain.Explorer;

@Controller
@RequestMapping("/explorerProfile")
public class ExplorerProfileController extends AbstractController {

	// Services---------------------------------------------------------

	@Autowired
	private ExplorerService	explorerService;


	//Constructor--------------------------------------------------------

	public ExplorerProfileController() {
		super();
	}

	//Edition------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Explorer explorer;

		explorer = this.explorerService.create();
		Assert.notNull(explorer);
		result = this.createEditModelAndView(explorer);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Explorer explorer, final BindingResult bindingResult) {
		ModelAndView result;

		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(explorer);
		else
			try {
				this.explorerService.save(explorer);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("could not execute statement; SQL [n/a]; constraint [null]" + "; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement"))
					result = this.createEditModelAndView(explorer, "explorer.commit.error.duplicateProfile");
				else
					result = this.createEditModelAndView(explorer, "explorer.commit.error");
			}

		return result;
	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Explorer explorer) {

		ModelAndView result;
		result = this.createEditModelAndView(explorer, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Explorer explorer, final String message) {

		ModelAndView result;

		result = new ModelAndView("explorer/edit");
		result.addObject("explorer", explorer);
		result.addObject("message", message);
		result.addObject("RequestURI", "explorerProfile/edit.do");

		return result;

	}
}
