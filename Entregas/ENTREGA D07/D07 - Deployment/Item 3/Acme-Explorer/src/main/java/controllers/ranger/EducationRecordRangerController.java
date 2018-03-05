
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
import services.EducationRecordService;
import services.RangerService;
import controllers.AbstractController;
import domain.Curricula;
import domain.EducationRecord;
import domain.Ranger;

@Controller
@RequestMapping("/educationRecord/ranger")
public class EducationRecordRangerController extends AbstractController {

	// Services---------------------------------------------------------

	@Autowired
	private EducationRecordService	educationRecordService;
	@Autowired
	private CurriculaService		curriculaService;
	@Autowired
	private RangerService			rangerService;


	//Constructor--------------------------------------------------------

	public EducationRecordRangerController() {
		super();

	}

	//Creating----------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		EducationRecord educationRecord;

		educationRecord = this.educationRecordService.create();
		result = this.createEditModelAndView(educationRecord);

		return result;

	}

	//Editing-----------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int educationRecordId) {

		ModelAndView result;
		EducationRecord educationRecord;
		Curricula curriculaPrincipal;
		final Ranger rangerPrincipal;

		educationRecord = this.educationRecordService.findOne(educationRecordId);
		Assert.notNull(educationRecord);

		//Compruebo que la curricula a editar sea del Ranger autentificado
		curriculaPrincipal = this.curriculaService.CurriculaWithThisEducationRecord(educationRecordId);
		rangerPrincipal = this.rangerService.findByPrincipal();
		Assert.isTrue(curriculaPrincipal.getRanger().equals(rangerPrincipal));

		result = this.createEditModelAndView(educationRecord);

		return result;

	}

	//Saving-----------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final EducationRecord educationRecord, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(educationRecord);
		else
			try {
				this.educationRecordService.save(educationRecord);
				result = new ModelAndView("redirect:/curricula/ranger/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(educationRecord, "educationRecord.commit.error");
			}

		return result;

	}

	//Deleting-----------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final EducationRecord educationRecord, final BindingResult binding) {

		ModelAndView result;

		try {
			this.educationRecordService.delete(educationRecord);
			result = new ModelAndView("redirect:/curricula/ranger/display.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(educationRecord, "educationRecord.commit.error");
		}

		return result;
	}
	//auxiliary------------------

	protected ModelAndView createEditModelAndView(final EducationRecord educationRecord) {

		Assert.notNull(educationRecord);
		ModelAndView result;
		result = this.createEditModelAndView(educationRecord, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final EducationRecord educationRecord, final String messageCode) {
		assert educationRecord != null;

		ModelAndView result;

		result = new ModelAndView("educationRecord/edit");
		result.addObject("educationRecord", educationRecord);
		result.addObject("message", messageCode);
		result.addObject("RequestURI", "educationRecord/ranger/edit.do");

		return result;

	}
}
