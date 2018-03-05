
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculaService;
import services.RangerService;
import domain.Curricula;
import domain.Ranger;

@Controller
@RequestMapping("/curricula")
public class CurriculaController extends AbstractController {

	// Services---------------------------------------------------------

	@Autowired
	private CurriculaService	curriculaService;
	@Autowired
	private RangerService		rangerService;


	//Constructor--------------------------------------------------------

	public CurriculaController() {
		super();
	}

	// Display ----------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int rangerId) {
		Assert.notNull(rangerId);
		ModelAndView result;
		Curricula curricula;
		Ranger ranger;

		ranger = this.rangerService.findOne(rangerId);
		Assert.notNull(ranger);
		curricula = this.curriculaService.findCurriculaFromRanger(ranger.getId());
		result = new ModelAndView("curricula/display");
		if (curricula == null)
			result.addObject("existCurricula", false);
		else {
			result.addObject("existCurricula", true);
			result.addObject("curricula", curricula);
			result.addObject("miscellaneousRecord", curricula.getMiscellaneousRecords());
			result.addObject("endorserRecord", curricula.getEndorserRecords());
			result.addObject("professionalRecord", curricula.getProfessionalRecords());
			result.addObject("educationRecord", curricula.getEducationRecords());
			result.addObject("personalRecord", curricula.getPersonalRecord());
			result.addObject("requestURI", "curricula/display.do");
		}

		return result;
	}
	//Creation-----------------------------------------------------------

	//Edition------------------------------------------------------------

	// Ancillary methods ------------------------------------------------------

}
