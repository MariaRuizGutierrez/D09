
package controllers.auditor;

import java.util.Collection;
import java.util.Date;

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

import services.AuditRecordService;
import services.AuditorService;
import services.TripService;
import controllers.AbstractController;
import domain.Attachment;
import domain.AuditRecord;
import domain.Auditor;
import domain.Trip;

@Controller
@RequestMapping("/auditRecord/auditor")
public class AuditRecordAuditorController extends AbstractController {

	// Services---------------------------------------------------------

	@Autowired
	private AuditRecordService	auditRecordService;

	@Autowired
	private TripService			tripService;

	@Autowired
	private AuditorService		auditorService;


	//Constructor--------------------------------------------------------

	public AuditRecordAuditorController() {
		super();
	}

	//Listing------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<AuditRecord> auditRecords;
		Auditor auditor;

		auditor = this.auditorService.findByPrincipal();
		auditRecords = this.auditRecordService.findByPrincipal();

		result = new ModelAndView("auditRecord/list");
		result.addObject("auditRecords", auditRecords);
		result.addObject("auditor", auditor);
		return result;

	}
	// Display ----------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int auditRecordId) {
		ModelAndView result;
		AuditRecord auditRecord = new AuditRecord();

		Collection<Attachment> attachs;

		auditRecord = this.auditRecordService.findOne(auditRecordId);

		attachs = this.auditRecordService.urlAttachments(auditRecord);

		result = new ModelAndView("auditRecord/display");
		result.addObject("auditRecord", auditRecord);
		result.addObject("attachments", attachs);

		return result;
	}
	//Creation-----------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int tripId) {
		ModelAndView result;
		AuditRecord auditRecord;
		Date date;
		Trip trip;

		trip = this.tripService.findOne(tripId);
		date = new Date();

		Assert.isTrue(trip.getFinishDate().after(date) && trip.isCancelled() == false);
		auditRecord = this.auditRecordService.create();
		result = this.createEditModelAndView(auditRecord);
		result.addObject("requestURI", "auditRecord/auditor/addTrip.do?tripId=" + tripId);
		return result;

	}

	@RequestMapping(value = "/addTrip", method = RequestMethod.POST, params = "save")
	public ModelAndView addTrip(@Valid AuditRecord auditRecord, final BindingResult bindingResult, @RequestParam final int tripId) {
		ModelAndView result;
		Trip trip;

		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(auditRecord, "auditRecordError.commit.error");
		else
			try {
				auditRecord = this.auditRecordService.save(auditRecord);
				trip = this.tripService.findOne(tripId);
				trip.getAuditRecords().add(auditRecord);
				this.tripService.saveA(trip);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(auditRecord, "auditRecord.commit.error");
			}
		return result;

	}

	//Edition------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int auditRecordId) {
		ModelAndView result;
		AuditRecord auditRecord;

		Auditor auditor;

		auditor = this.auditorService.findByPrincipal();
		auditRecord = this.auditRecordService.findOne(auditRecordId);
		Assert.isTrue(auditor.getAuditRecords().contains(auditRecord), "Cannot commit this operation, because it's illegal");
		Assert.notNull(auditRecord);
		Assert.isTrue(auditRecord.isDraftMode() == true, "Cannot commit this operation, because it's illegal");
		result = this.createEditModelAndView(auditRecord);
		result.addObject("requestURI", "auditRecord/auditor/edit.do");
		result.addObject("auditor", auditor);
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final AuditRecord auditRecord, final BindingResult bindingResult) {
		ModelAndView result;

		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(auditRecord);
		else
			try {
				this.auditRecordService.save(auditRecord);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(auditRecord, "auditRecord.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@ModelAttribute final AuditRecord auditRecord, final BindingResult bindingResult) {
		ModelAndView result;

		try {
			this.auditRecordService.delete(auditRecord);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(auditRecord, "auditRecord.commit.error");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final AuditRecord auditRecord) {
		assert auditRecord != null;
		ModelAndView result;
		result = this.createEditModelAndView(auditRecord, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final AuditRecord auditRecord, final String message) {

		assert auditRecord != null;
		ModelAndView result;

		result = new ModelAndView("auditRecord/edit");
		result.addObject("auditRecord", auditRecord);
		result.addObject("message", message);

		return result;

	}

}
