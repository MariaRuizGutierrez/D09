
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import controllers.AbstractController;
import domain.Administrator;
import forms.AdministratorForm;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	// Services---------------------------------------------------------

	@Autowired
	private AdministratorService	administratorService;


	//Constructor--------------------------------------------------------

	public AdministratorController() {
		super();
	}

	//Create------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Administrator administrator;
		AdministratorForm administratorForm;

		administrator = this.administratorService.create();
		Assert.notNull(administrator);
		administratorForm = new AdministratorForm(administrator);
		result = this.createEditModelAndView(administratorForm);
		return result;
	}

	//Edition------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		AdministratorForm administratorForm;
		Administrator administrator;

		administrator = this.administratorService.findByPrincipal();
		Assert.notNull(administrator);
		administratorForm = new AdministratorForm(administrator);
		result = this.createEditModelAndView(administratorForm);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(AdministratorForm administratorForm, final BindingResult bindingResult) {
		ModelAndView result;

		administratorForm = this.administratorService.reconstruct(administratorForm, bindingResult);
		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(administratorForm);
		else
			try {
				if ((administratorForm.getAdministrator().getId() == 0)) {
					Assert.isTrue(administratorForm.getAdministrator().getUserAccount().getPassword().equals(administratorForm.getPasswordCheck()), "password does not match");
					Assert.isTrue(administratorForm.getConditions(), "the conditions must be accepted");
				}
				this.administratorService.save(administratorForm.getAdministrator());
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("password does not match"))
					result = this.createEditModelAndView(administratorForm, "administrator.commit.error.passwordDoesNotMatch");
				else if (oops.getMessage().equals("the conditions must be accepted"))
					result = this.createEditModelAndView(administratorForm, "administrator.commit.error.conditions");
				else if (oops.getMessage().equals("could not execute statement; SQL [n/a]; constraint [null]" + "; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement"))
					result = this.createEditModelAndView(administratorForm, "administrator.commit.error.duplicateProfile");
				else
					result = this.createEditModelAndView(administratorForm, "administrator.commit.error");
			}

		return result;
	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final AdministratorForm administratorForm) {

		ModelAndView result;
		result = this.createEditModelAndView(administratorForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final AdministratorForm administratorForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("administrator/edit");
		result.addObject("administratorForm", administratorForm);
		result.addObject("message", message);

		return result;

	}

}
