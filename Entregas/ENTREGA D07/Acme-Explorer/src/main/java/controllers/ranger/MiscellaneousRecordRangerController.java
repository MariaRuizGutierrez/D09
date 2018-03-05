
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
import services.MiscellaneousRecordService;
import services.RangerService;
import controllers.AbstractController;
import domain.Curricula;
import domain.MiscellaneousRecord;
import domain.Ranger;

@Controller
@RequestMapping("/miscellaneousRecord/ranger")
public class MiscellaneousRecordRangerController extends AbstractController {

	// Services---------------------------------------------------------

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;
	@Autowired
	private CurriculaService			curriculaService;
	@Autowired
	private RangerService				rangerService;


	//Constructor--------------------------------------------------------

	public MiscellaneousRecordRangerController() {
		super();

	}

	//Creating----------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		MiscellaneousRecord miscellaneousRecord;

		miscellaneousRecord = this.miscellaneousRecordService.create();
		result = this.createEditModelAndView(miscellaneousRecord);

		return result;

	}

	//Editing-----------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int miscellaneousRecordId) {

		ModelAndView result;
		MiscellaneousRecord miscellaneousRecord;
		Curricula curriculaPrincipal;
		Ranger rangerPrincipal;

		miscellaneousRecord = this.miscellaneousRecordService.findOne(miscellaneousRecordId);
		Assert.notNull(miscellaneousRecord);

		//Compruebo que la curricula a editar sea del Ranger autentificado
		curriculaPrincipal = this.curriculaService.CurriculaWithThisMiscellaneousRecord(miscellaneousRecordId);
		rangerPrincipal = this.rangerService.findByPrincipal();
		Assert.isTrue(curriculaPrincipal.getRanger().equals(rangerPrincipal));

		result = this.createEditModelAndView(miscellaneousRecord);

		return result;

	}

	//Saving-----------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final MiscellaneousRecord miscellaneousRecord, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(miscellaneousRecord);
		else
			try {
				this.miscellaneousRecordService.save(miscellaneousRecord);
				result = new ModelAndView("redirect:/curricula/ranger/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(miscellaneousRecord, "miscellaneousRecord.commit.error");
			}

		return result;

	}

	//Deleting-----------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final MiscellaneousRecord miscellaneousRecord, final BindingResult binding) {

		ModelAndView result;

		try {
			this.miscellaneousRecordService.delete(miscellaneousRecord);
			result = new ModelAndView("redirect:/curricula/ranger/display.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(miscellaneousRecord, "miscellaneousRecord.commit.error");
		}

		return result;
	}
	//auxiliary------------------

	protected ModelAndView createEditModelAndView(final MiscellaneousRecord miscellaneousRecord) {

		Assert.notNull(miscellaneousRecord);
		ModelAndView result;
		result = this.createEditModelAndView(miscellaneousRecord, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final MiscellaneousRecord miscellaneousRecord, final String messageCode) {
		assert miscellaneousRecord != null;

		ModelAndView result;

		result = new ModelAndView("miscellaneousRecord/edit");
		result.addObject("miscellaneousRecord", miscellaneousRecord);
		result.addObject("message", messageCode);
		result.addObject("RequestURI", "miscellaneousRecord/ranger/edit.do");

		return result;

	}
}
