/*
 * CurriculumCustomerController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.explorer;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ContactEmergencyService;
import services.ExplorerService;
import controllers.AbstractController;
import domain.ContactEmergency;
import domain.Explorer;

@Controller
@RequestMapping("/contactsEmergency/explorer")
public class ContactEmergencyExplorerController extends AbstractController {

	//service---------------------------------------------------------------------------
	@Autowired
	private ContactEmergencyService	contactEmergencyService;
	@Autowired
	private ExplorerService			explorerService;


	//constructor-------------------------------------------------------------------------
	public ContactEmergencyExplorerController() {
		super();
	}
	//Listing---------------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<ContactEmergency> contactsEmergency;
		contactsEmergency = this.explorerService.findByPrincipalContacts();

		result = new ModelAndView("contactsEmergency/list");
		result.addObject("contactsEmergency", contactsEmergency);

		return result;

	}
	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		ContactEmergency contactsEmergency;

		contactsEmergency = this.contactEmergencyService.create();
		result = this.createEditModelAndView(contactsEmergency);

		return result;
	}
	//Edition--------------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int contactEmergencyId) {
		ModelAndView result;
		ContactEmergency contactEmergency;
		Explorer explorer;

		explorer = this.explorerService.findByPrincipal();
		contactEmergency = this.contactEmergencyService.findOne(contactEmergencyId);
		Assert.isTrue(explorer.getContactsEmergency().contains(contactEmergency), "Cannot commit this operation, because it's illegal");
		Assert.notNull(contactEmergency);
		result = this.createEditModelAndView(contactEmergency);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ContactEmergency contactEmergency, final BindingResult bindingResult) {
		ModelAndView result;

		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(contactEmergency);
		else
			try {
				this.contactEmergencyService.save(contactEmergency);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("introduce at least one parameter, email or phone"))
					result = this.createEditModelAndView(contactEmergency, "contactEmergency.error.parameter");
				else
					result = this.createEditModelAndView(contactEmergency, "contactEmergency.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@ModelAttribute final ContactEmergency contactEmergency, final BindingResult bindingResult) {
		ModelAndView result;

		try {
			this.contactEmergencyService.delete(contactEmergency);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(contactEmergency, "contactEmergency.commit.error");
		}

		return result;
	}
	//ancially methods---------------------------------------------------------------------------

	protected ModelAndView createEditModelAndView(final ContactEmergency contactsEmergency) {
		Assert.notNull(contactsEmergency);
		ModelAndView result;
		result = this.createEditModelAndView(contactsEmergency, null);
		return result;

	}

	protected ModelAndView createEditModelAndView(final ContactEmergency contactsEmergency, final String message) {
		Assert.notNull(contactsEmergency);
		ModelAndView result;

		result = new ModelAndView("conctactsEmergency/edit");
		result.addObject("contactEmergency", contactsEmergency);
		result.addObject("message", message);
		return result;

	}
}
