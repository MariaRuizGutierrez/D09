
package controllers.ranger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculaService;
import services.ProfessionalRecordService;
import services.RangerService;
import controllers.AbstractController;
import domain.Curricula;
import domain.ProfessionalRecord;
import domain.Ranger;

@Controller
@RequestMapping("/professionalRecord/ranger")
public class ProfessionalRecordRangerController extends AbstractController {

	// Services---------------------------------------------------------

	@Autowired
	private ProfessionalRecordService	professionalRecordService;
	@Autowired
	private CurriculaService			curriculaService;
	@Autowired
	private RangerService				rangerService;


	//Constructor--------------------------------------------------------

	public ProfessionalRecordRangerController() {
		super();

	}

	//Creating----------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		ProfessionalRecord professionalRecord;

		professionalRecord = this.professionalRecordService.create();
		result = this.createEditModelAndView(professionalRecord);

		return result;

	}

	//Editing-----------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int professionalRecordId) {

		ModelAndView result;
		ProfessionalRecord professionalRecord;
		Curricula curriculaPrincipal;
		final Ranger rangerPrincipal;

		professionalRecord = this.professionalRecordService.findOne(professionalRecordId);
		Assert.notNull(professionalRecord);

		//Compruebo que la curricula a editar sea del Ranger autentificado
		curriculaPrincipal = this.curriculaService.CurriculaWithThisProfessionalRecord(professionalRecordId);
		rangerPrincipal = this.rangerService.findByPrincipal();
		Assert.isTrue(curriculaPrincipal.getRanger().equals(rangerPrincipal));

		result = this.createEditModelAndView(professionalRecord);

		return result;

	}

	//Saving-----------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ProfessionalRecord professionalRecord, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(professionalRecord);
		else
			try {
				this.professionalRecordService.save(professionalRecord);
				result = new ModelAndView("redirect:/curricula/ranger/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(professionalRecord, "professionalRecord.commit.error");
			}

		return result;

	}

	//Deleting-----------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final ProfessionalRecord professionalRecord, final BindingResult binding) {

		ModelAndView result;

		try {
			this.professionalRecordService.delete(professionalRecord);
			result = new ModelAndView("redirect:/curricula/ranger/display.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(professionalRecord, "professionalRecord.commit.error");
		}

		return result;
	}
	//auxiliary------------------

	protected ModelAndView createEditModelAndView(final ProfessionalRecord professionalRecord) {

		Assert.notNull(professionalRecord);
		ModelAndView result;
		result = this.createEditModelAndView(professionalRecord, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final ProfessionalRecord professionalRecord, final String messageCode) {
		assert professionalRecord != null;

		ModelAndView result;

		result = new ModelAndView("professionalRecord/edit");
		result.addObject("professionalRecord", professionalRecord);
		result.addObject("message", messageCode);
		result.addObject("RequestURI", "professionalRecord/ranger/edit.do");

		return result;

	}
}
