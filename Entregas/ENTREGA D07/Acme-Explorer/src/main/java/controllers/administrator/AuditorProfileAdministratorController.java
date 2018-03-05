
package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AuditorService;
import controllers.AbstractController;
import domain.Auditor;

@Controller
@RequestMapping("/auditorProfile/administrator")
public class AuditorProfileAdministratorController extends AbstractController {

	// Services---------------------------------------------------------

	@Autowired
	private AuditorService	auditorService;


	//Constructor--------------------------------------------------------

	public AuditorProfileAdministratorController() {
		super();
	}

	//Edition------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Auditor auditor;

		auditor = this.auditorService.create();
		Assert.notNull(auditor);
		result = this.createEditModelAndView(auditor);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Auditor auditor, final BindingResult bindingResult) {
		ModelAndView result;

		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(auditor);
		else
			try {
				this.auditorService.save(auditor);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("could not execute statement; SQL [n/a]; constraint [null]" + "; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement"))
					result = this.createEditModelAndView(auditor, "auditor.commit.error.duplicateProfile");
				else
					result = this.createEditModelAndView(auditor, "auditor.commit.error");
			}

		return result;
	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Auditor auditor) {

		ModelAndView result;
		result = this.createEditModelAndView(auditor, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Auditor auditor, final String message) {

		ModelAndView result;

		result = new ModelAndView("auditor/edit");
		result.addObject("auditor", auditor);
		result.addObject("message", message);
		result.addObject("RequestURI", "auditorProfile/administrator/edit.do");

		return result;

	}
}
