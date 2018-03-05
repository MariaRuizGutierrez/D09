
package controllers.auditor;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.NoteService;
import services.TripService;
import controllers.AbstractController;
import domain.Note;
import domain.Trip;

@Controller
@RequestMapping("/note/auditor")
public class NoteAuditorController extends AbstractController {

	// Services---------------------------------------------------------

	@Autowired
	private NoteService	noteService;

	@Autowired
	private TripService	tripService;


	//Constructor--------------------------------------------------------

	public NoteAuditorController() {
		super();
	}

	//Listing------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Note> notes;

		notes = this.noteService.findByPrincipal();

		result = new ModelAndView("note/list");
		result.addObject("requestURI", "note/auditor/list.do");
		result.addObject("notes", notes);
		return result;

	}

	//Creation-----------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int tripId) {
		ModelAndView result;
		Note note;
		Date date;
		Trip trip;

		trip = this.tripService.findOne(tripId);
		date = new Date();

		Assert.isTrue(trip.getFinishDate().after(date) && trip.isCancelled() == false);
		note = this.noteService.create();
		result = this.createEditModelAndView(note);
		result.addObject("requestURI", "note/auditor/addTrip.do?tripId=" + tripId);
		return result;

	}

	@RequestMapping(value = "/addTrip", method = RequestMethod.POST, params = "save")
	public ModelAndView addTrip(@Valid Note note, final BindingResult bindingResult, @RequestParam final int tripId) {
		ModelAndView result;
		Trip trip;

		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(note, "note.save.commit.error");
		else
			try {
				note = this.noteService.save(note);
				trip = this.tripService.findOne(tripId);
				trip.getNotes().add(note);
				this.tripService.saveA(trip);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(note, "note.commit.error");
			}
		return result;

	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Note note) {
		assert note != null;
		ModelAndView result;
		result = this.createEditModelAndView(note, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Note note, final String message) {

		assert note != null;
		ModelAndView result;

		result = new ModelAndView("note/edit");
		result.addObject("note", note);
		result.addObject("message", message);

		return result;

	}

	// Display ----------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int noteId) {
		ModelAndView result;
		Note note;

		note = this.noteService.findOne(noteId);

		result = new ModelAndView("note/display");
		result.addObject("note", note);

		return result;
	}

}
