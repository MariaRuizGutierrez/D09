
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.UserService;
import domain.User;
import forms.UserForm;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

	// Services---------------------------------------------------------

	@Autowired
	private UserService	userService;


	//Constructor--------------------------------------------------------

	public UserController() {
		super();
	}

	//Listing-----------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<User> users;

		users = this.userService.findAll();

		result = new ModelAndView("user/list");
		result.addObject("users", users);
		result.addObject("requestURI", "user/list.do");

		return result;

	}

	//Displaying----------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int userId) {

		ModelAndView result;
		User user;

		user = this.userService.findOne(userId);

		result = new ModelAndView("user/display");
		result.addObject("user", user);
		result.addObject("requestURI", "user/display.do");

		return result;
	}

	//Create----------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createUser() {
		ModelAndView result;
		User user;

		user = this.userService.create();

		UserForm cf;
		cf = new UserForm(user);

		result = new ModelAndView("user/edit");
		result.addObject("userForm", cf);

		return result;
	}

	//Edition------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		User user;

		user = this.userService.findByPrincipal();
		UserForm userForm;
		userForm = new UserForm(user);
		result = new ModelAndView("user/edit");
		result.addObject("userForm", userForm);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCustomer(@ModelAttribute("userForm") UserForm userForm, final BindingResult binding) {
		ModelAndView result;

		userForm = this.userService.reconstruct(userForm, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(userForm);
		else
			try {
				if ((userForm.getUser().getId() == 0)) {
					Assert.isTrue(userForm.getUser().getUserAccount().getPassword().equals(userForm.getPasswordCheck()), "password does not match");
					Assert.isTrue(userForm.getConditions(), "the conditions must be accepted");
				}
				this.userService.save(userForm.getUser());
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("password does not match"))
					result = this.createEditModelAndView(userForm, "user.password.match");
				else if (oops.getMessage().equals("the conditions must be accepted"))
					result = this.createEditModelAndView(userForm, "actor.conditions.accept");
				else if (oops.getMessage().equals("could not execute statement; SQL [n/a]; constraint [null]" + "; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement"))
					result = this.createEditModelAndView(userForm, "user.commit.error.duplicateProfile");
				else
					result = this.createEditModelAndView(userForm, "user.commit.error");
			}

		return result;
	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final UserForm userForm) {

		ModelAndView result;
		result = this.createEditModelAndView(userForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final UserForm userForm, final String message) {

		ModelAndView result;

		result = new ModelAndView("user/edit");
		result.addObject("user", userForm);
		result.addObject("message", message);
		result.addObject("RequestURI", "user/edit.do");

		return result;

	}

}
