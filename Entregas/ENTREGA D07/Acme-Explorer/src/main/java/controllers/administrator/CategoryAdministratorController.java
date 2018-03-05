
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

import services.CategoryService;
import services.ConfigurationSystemService;
import controllers.AbstractController;
import domain.Category;

@Controller
@RequestMapping(value = "/category/administrator")
public class CategoryAdministratorController extends AbstractController {

	//Services--------------------------------------------

	@Autowired
	private CategoryService				categoryService;

	@Autowired
	private ConfigurationSystemService	configurationSystemService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<Category> categories;

		categories = this.categoryService.findAll();

		result = new ModelAndView("category/list");
		result.addObject("categories", categories);
		result.addObject("requestURI", "category/administrator/list.do");

		return result;

	}

	//Creating----------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		Category category;

		category = this.categoryService.create();
		result = this.createEditModelAndView(category);

		return result;

	}

	//Editing-----------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int categoryId) {

		ModelAndView result;
		Category category;
		Collection<Category> defaultCategories;

		defaultCategories = this.configurationSystemService.defaultCategories();
		category = this.categoryService.findOne(categoryId);
		Assert.isTrue(!defaultCategories.contains(category));
		Assert.notNull(category);

		result = this.createEditModelAndView(category);

		return result;

	}

	//Saving-----------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Category category, BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(category);
		else
			try {
				this.categoryService.save(category);
				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(category, "category.commit.error");
			}

		return result;

	}

	//Deleting-----------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Category category, BindingResult binding) {

		ModelAndView result;

		try {
			this.categoryService.delete(category);
			result = new ModelAndView("redirect:list.do");
		} catch (Throwable oops) {
			result = this.createEditModelAndView(category, "category.commit.error");
		}

		return result;
	}
	//auxiliary------------------

	protected ModelAndView createEditModelAndView(Category category) {

		Assert.notNull(category);
		ModelAndView result;
		result = this.createEditModelAndView(category, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(Category category, String messageCode) {
		assert category != null;

		ModelAndView result;
		Collection<Category> categories;

		categories = this.categoryService.findAll();

		result = new ModelAndView("category/edit");
		result.addObject("category", category);
		result.addObject("fatherCategories", categories);
		result.addObject("message", messageCode);

		return result;

	}

}
