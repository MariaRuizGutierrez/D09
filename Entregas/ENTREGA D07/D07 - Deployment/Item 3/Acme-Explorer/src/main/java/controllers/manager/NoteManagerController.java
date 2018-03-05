
package controllers.manager;

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

import services.ManagerService;
import services.NoteService;
import services.TripService;
import controllers.AbstractController;
import domain.Manager;
import domain.Note;
import domain.Trip;

@Controller
@RequestMapping("/note/manager")
public class NoteManagerController extends AbstractController {

	//Services--------------------------------------------

	@Autowired
	private NoteService		noteService;

	@Autowired
	private ManagerService	managerService;

	@Autowired
	private TripService		tripService;


	//Listing----------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Note> notes;

		notes = this.noteService.findNotesByManager();

		result = new ModelAndView("note/list/manager");
		result.addObject("notes", notes);

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
		result.addObject("requestURI", "note/manager/display.do");

		return result;
	}
	//Editing---------

	@RequestMapping(value = "/reply", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int noteId) {

		ModelAndView result;
		Note note;
		Manager manager;
		Trip trip;

		note = this.noteService.findOne(noteId);
		trip = this.tripService.findTripsByNote(note);
		manager = this.managerService.findByPrincipal();
		Assert.notNull(note);
		Assert.isTrue(trip.getNotes().contains(note) && trip.getManager().equals(manager), "Cannot commit this operation, because it's illegal");
		result = this.createEditModelAndView(note);

		return result;
	}
	//Saving------------------

	@RequestMapping(value = "/reply", method = RequestMethod.POST, params = "save")
	public ModelAndView saveReply(@Valid Note note, BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(note);
		else
			try {
				this.noteService.saveReply(note);
				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(note, "note.commit.error");
			}
		return result;
	}
	//auxiliares--------

	protected ModelAndView createEditModelAndView(Note note) {

		Assert.notNull(note);
		ModelAndView result;
		result = this.createEditModelAndView(note, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(Note note, String messageCode) {
		assert note != null;

		ModelAndView result;

		result = new ModelAndView("note/reply");
		result.addObject("note", note);
		result.addObject("message", messageCode);

		return result;

	}

}
