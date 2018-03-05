
package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationSystemService;
import controllers.AbstractController;
import domain.ConfigurationSystem;

@Controller
@RequestMapping(value = "/configurationSystem/administrator")
public class ConfigurationSystemAdministratorController extends AbstractController {

	//Services--------------------------------------------

	@Autowired
	private ConfigurationSystemService	configurationSystemService;


	//Editing-----------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {

		ModelAndView result;
		ConfigurationSystem configurationSystem;

		configurationSystem = this.configurationSystemService.findOne();
		Assert.notNull(configurationSystem);

		result = this.createEditModelAndView(configurationSystem);

		return result;

	}

	//Saving-----------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ConfigurationSystem configurationSystem, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(configurationSystem);
		else
			try {
				this.configurationSystemService.save(configurationSystem);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(configurationSystem, "configurationSystem.commit.error");
			}

		return result;

	}

	//auxiliary------------------

	protected ModelAndView createEditModelAndView(final ConfigurationSystem configurationSystem) {

		Assert.notNull(configurationSystem);
		ModelAndView result;
		result = this.createEditModelAndView(configurationSystem, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final ConfigurationSystem configurationSystem, final String messageCode) {
		Assert.notNull(configurationSystem);

		ModelAndView result;

		result = new ModelAndView("configurationSystem/edit");
		result.addObject("configurationSystem", configurationSystem);
		result.addObject("message", messageCode);
		result.addObject("RequestURI", "configurationSystem/administrator/edit.do");

		return result;

	}
}
