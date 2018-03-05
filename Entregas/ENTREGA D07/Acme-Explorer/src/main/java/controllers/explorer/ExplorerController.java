
package controllers.explorer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ExplorerService;
import controllers.AbstractController;
import domain.Explorer;

@Controller
@RequestMapping("/explorer")
public class ExplorerController extends AbstractController {

	// Services---------------------------------------------------------

	@Autowired
	private ExplorerService	explorerService;


	//Constructor--------------------------------------------------------

	public ExplorerController() {
		super();
	}
	//List---------------------------------------------------------------

	//Edition------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Explorer explorer;

		explorer = this.explorerService.findByPrincipal();
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
				result = this.createEditModelAndView(explorer, "sponsor.commit.error");
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Explorer sponsor) {

		ModelAndView result;
		result = this.createEditModelAndView(sponsor, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Explorer explorer, final String message) {

		ModelAndView result;

		result = new ModelAndView("explorer/edit");
		result.addObject("explorer", explorer);
		result.addObject("message", message);
		result.addObject("RequestURI", "explorer/edit.do");

		return result;

	}
}
