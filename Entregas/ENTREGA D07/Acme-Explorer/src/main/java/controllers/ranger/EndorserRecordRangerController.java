
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
import services.EndorserRecordService;
import services.RangerService;
import controllers.AbstractController;
import domain.Curricula;
import domain.EndorserRecord;
import domain.Ranger;

@Controller
@RequestMapping("/endorserRecord/ranger")
public class EndorserRecordRangerController extends AbstractController {

	// Services---------------------------------------------------------

	@Autowired
	private EndorserRecordService	endorserRecordService;
	@Autowired
	private CurriculaService		curriculaService;
	@Autowired
	private RangerService			rangerService;


	//Constructor--------------------------------------------------------

	public EndorserRecordRangerController() {
		super();

	}

	//Creating----------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		EndorserRecord endorserRecord;

		endorserRecord = this.endorserRecordService.create();
		result = this.createEditModelAndView(endorserRecord);

		return result;

	}

	//Editing-----------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int endorserRecordId) {

		ModelAndView result;
		EndorserRecord endorserRecord;
		Curricula curriculaPrincipal;
		final Ranger rangerPrincipal;

		endorserRecord = this.endorserRecordService.findOne(endorserRecordId);
		Assert.notNull(endorserRecord);

		//Compruebo que la curricula a editar sea del Ranger autentificado
		curriculaPrincipal = this.curriculaService.CurriculaWithThisEndorserRecord(endorserRecordId);
		rangerPrincipal = this.rangerService.findByPrincipal();
		Assert.isTrue(curriculaPrincipal.getRanger().equals(rangerPrincipal));

		result = this.createEditModelAndView(endorserRecord);

		return result;

	}

	//Saving-----------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final EndorserRecord endorserRecord, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(endorserRecord);
		else
			try {
				this.endorserRecordService.save(endorserRecord);
				result = new ModelAndView("redirect:/curricula/ranger/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(endorserRecord, "endorserRecord.commit.error");
			}

		return result;

	}

	//Deleting-----------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final EndorserRecord endorserRecord, final BindingResult binding) {

		ModelAndView result;

		try {
			this.endorserRecordService.delete(endorserRecord);
			result = new ModelAndView("redirect:/curricula/ranger/display.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(endorserRecord, "endorserRecord.commit.error");
		}

		return result;
	}
	//auxiliary------------------

	protected ModelAndView createEditModelAndView(final EndorserRecord endorserRecord) {

		Assert.notNull(endorserRecord);
		ModelAndView result;
		result = this.createEditModelAndView(endorserRecord, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final EndorserRecord endorserRecord, final String messageCode) {
		assert endorserRecord != null;

		ModelAndView result;

		result = new ModelAndView("endorserRecord/edit");
		result.addObject("endorserRecord", endorserRecord);
		result.addObject("message", messageCode);
		result.addObject("RequestURI", "endorserRecord/ranger/edit.do");

		return result;

	}
}
