
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
import services.PersonalRecordService;
import services.RangerService;
import controllers.AbstractController;
import domain.Curricula;
import domain.PersonalRecord;
import domain.Ranger;

@Controller
@RequestMapping("/personalRecord/ranger")
public class PersonalRecordRangerController extends AbstractController {

	// Services---------------------------------------------------------

	@Autowired
	private PersonalRecordService	personalRecordService;
	@Autowired
	private CurriculaService		curriculaService;
	@Autowired
	private RangerService			rangerService;


	//Constructor--------------------------------------------------------

	public PersonalRecordRangerController() {
		super();

	}

	//Creating----------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		PersonalRecord personalRecord;

		personalRecord = this.personalRecordService.create();
		result = this.createEditModelAndView(personalRecord);
		result.addObject("existCurricula", false);

		return result;

	}

	//Editing-----------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int personalRecordId) {

		ModelAndView result;
		PersonalRecord personalRecord;
		Ranger rangerPrincipal;
		Curricula curriculaPrincipal;

		personalRecord = this.personalRecordService.findOne(personalRecordId);
		Assert.notNull(personalRecord);

		//Compruebo que la curricula a editar sea del Ranger autentificado
		curriculaPrincipal = this.curriculaService.CurriculaWithThisPersonalRecord(personalRecordId);
		rangerPrincipal = this.rangerService.findByPrincipal();
		Assert.isTrue(curriculaPrincipal.getRanger().equals(rangerPrincipal));

		result = this.createEditModelAndView(personalRecord);
		result.addObject("existCurricula", true);

		return result;

	}

	//Saving-----------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid PersonalRecord personalRecord, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(personalRecord);
		else
			try {
				Curricula curricula;
				curricula = this.curriculaService.findCurriculaFromRanger(this.rangerService.findByPrincipal().getId());
				personalRecord = this.personalRecordService.save(personalRecord);
				if (curricula == null) {
					curricula = this.curriculaService.create();
					curricula.setRanger(this.rangerService.findByPrincipal());
					curricula.setPersonalRecord(personalRecord);
					this.curriculaService.save(curricula);
				}
				result = new ModelAndView("redirect:/curricula/ranger/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(personalRecord, "personalRecord.commit.error");
			}

		return result;

	}

	//Deleting-----------------

	//auxiliary------------------

	protected ModelAndView createEditModelAndView(final PersonalRecord personalRecord) {

		Assert.notNull(personalRecord);
		ModelAndView result;
		result = this.createEditModelAndView(personalRecord, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final PersonalRecord personalRecord, final String messageCode) {
		assert personalRecord != null;

		ModelAndView result;

		result = new ModelAndView("personalRecord/edit");
		result.addObject("personalRecord", personalRecord);
		result.addObject("message", messageCode);
		result.addObject("RequestURI", "personalRecord/ranger/edit.do");

		return result;

	}
}
