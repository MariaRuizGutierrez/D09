
package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.TagService;
import controllers.AbstractController;
import domain.Tag;

@Controller
@RequestMapping("/tag/administrator/")
public class TagAdministratorController extends AbstractController {

	//Services--------------------------------------------

	@Autowired
	private TagService	tagService;

	public String		oldName;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<Tag> tags;

		tags = this.tagService.findAll();

		result = new ModelAndView("tag/list");
		result.addObject("tags", tags);
		result.addObject("requestURI", "tag/administrator/list.do");

		return result;

	}

	//Creating----------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Tag tag;

		tag = this.tagService.create();

		result = this.createEditModelAndView(tag);

		return result;

	}

	//Editing-----------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int tagId) {
		ModelAndView result;
		Tag tag;

		tag = this.tagService.findOne(tagId);
		Assert.notNull(tag);

		result = this.createEditModelAndView(tag);

		return result;

	}

	//Saving-----------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Tag tag, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(tag);
		else
			try {

				this.tagService.save(tag);
				result = new ModelAndView("redirect:list.do");

			} catch (Throwable oops) {
				if (oops.getMessage().equals("This name already exists"))
					result = this.createEditModelAndView(tag, "tag.duplicate");
				else
					result = this.createEditModelAndView(tag, "tag.commit.error");
			}
		return result;

	}

	//Deleting-----------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Tag tag, BindingResult binding) {

		ModelAndView result;

		try {
			this.tagService.delete(tag);
			result = new ModelAndView("redirect:list.do");
		} catch (Throwable oops) {
			result = this.createEditModelAndView(tag, "tag.commit.error");
		}

		return result;
	}

	//auxiliary------------------

	protected ModelAndView createEditModelAndView(Tag tag) {

		Assert.notNull(tag);
		ModelAndView result;
		result = this.createEditModelAndView(tag, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(Tag tag, String messageCode) {
		assert tag != null;

		ModelAndView result;

		result = new ModelAndView("tag/edit");
		result.addObject("tag", tag);
		result.addObject("message", messageCode);

		return result;

	}
}
