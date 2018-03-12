
package controllers.manager;

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

import services.CategoryService;
import services.ManagerService;
import services.ServiceOfferedService;
import controllers.AbstractController;
import domain.Category;
import domain.ServiceOffered;

@Controller
@RequestMapping("/serviceoffered/manager")
public class ServiceOfferedManagerController extends AbstractController {

	//service---------------------------------------------------------------------------

	@Autowired
	private ServiceOfferedService	serviceOfferedService;
	
	@Autowired
	private ManagerService			managerService;
	
	@Autowired
	private CategoryService			categoryService;


	//constructor-------------------------------------------------------------------------
	public ServiceOfferedManagerController() {
		super();
	}
	//Listing-----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<ServiceOffered> serviceoffered;
		serviceoffered = this.serviceOfferedService.AllServiceNotCancelled();
		result = new ModelAndView("serviceoffered/list");
		result.addObject("serviceoffered", serviceoffered);
		result.addObject("requestURI", "serviceoffered/manager/list.do");
		return result;

	}

	// Create -----------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		ServiceOffered serviceOffered;
		this.managerService.checkPrincipal();

		serviceOffered = this.serviceOfferedService.create();

		result = this.createEditModelAndView(serviceOffered);
		return result;
	}
	
	//Edition--------------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int serviceOfferedId) {
		ModelAndView result;
		ServiceOffered serviceOffered;

		serviceOffered = this.serviceOfferedService.findOne(serviceOfferedId);

		Assert.notNull(serviceOffered);
		result = this.createEditModelAndView(serviceOffered);
		return result;
	}
	
	
	// Save -----------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid ServiceOffered serviceOffered, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(serviceOffered);
		else
			try {
				this.serviceOfferedService.save(serviceOffered);
				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(serviceOffered, "serviceOffered.commit.error");
			}
		return result;
	}
	
	//Delete---------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@ModelAttribute final ServiceOffered serviceOffered, final BindingResult bindingResult) {
		ModelAndView result;

		try {
			this.serviceOfferedService.delete(serviceOffered);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(serviceOffered, "serviceOffered.commit.error");
		}

		return result;
	}
	
	//Auxiliary-----------------------

	protected ModelAndView createEditModelAndView(ServiceOffered serviceOffered) {

		Assert.notNull(serviceOffered);
		ModelAndView result;
		result = this.createEditModelAndView(serviceOffered, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(ServiceOffered serviceOffered, String messageCode) {
		assert serviceOffered != null;

		ModelAndView result;
		Collection<Category> categories;
			
		categories = this.categoryService.findAll();

		result = new ModelAndView("serviceOffered/edit");
		result.addObject("serviceOffered", serviceOffered);
		result.addObject("categories", categories);
		result.addObject("message", messageCode);

		return result;

	}
	

	
	
	//ancially methods---------------------------------------------------------------------------

	//	protected ModelAndView createEditModelAndView(final Rendezvouse rendezvouse) {
	//		Assert.notNull(rendezvouse);
	//		ModelAndView result;
	//		result = this.createEditModelAndView(rendezvouse, null);
	//		return result;
	//
	//	}
	//
	//	protected ModelAndView createEditModelAndView(final Rendezvouse rendezvouse, final String message) {
	//		Assert.notNull(rendezvouse);
	//		Collection<Rendezvouse> similarRendezvouses;
	//		ModelAndView result;
	//		result = new ModelAndView("rendezvous/edit");
	//		similarRendezvouses = this.rendezvouseService.findAllRendezvousesNotDeletedExceptRendezvousId(rendezvouse.getId());
	//
	//		result.addObject("rendezvouse", rendezvouse);
	//		result.addObject("similarRendezvouses", similarRendezvouses);
	//		result.addObject("message", message);
	//		return result;
	//
	//	}

}
