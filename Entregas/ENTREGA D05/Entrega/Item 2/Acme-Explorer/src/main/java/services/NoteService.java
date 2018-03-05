
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.NoteRepository;
import domain.Auditor;
import domain.Note;

@Service
@Transactional
public class NoteService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private NoteRepository	noteRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ManagerService	managerService;

	@Autowired
	private AuditorService	auditorService;


	// Constructors-------------------------------------------------------

	public NoteService() {
		super();
	}

	// Simple CRUD methods------------------------------------------------

	public Note create() {
		Note result;
		Auditor auditorPrincipal;
		Date createdMoment;

		result = new Note();
		auditorPrincipal = this.auditorService.findByPrincipal();
		Assert.notNull(auditorPrincipal);
		createdMoment = new Date();

		result.setCreatedMoment(createdMoment);
		result.setAuditor(auditorPrincipal);
		return result;
	}

	public Note findOne(final int noteId) {
		Assert.isTrue(noteId != 0);
		Note result;
		result = this.noteRepository.findOne(noteId);
		return result;
	}

	public Collection<Note> findAll() {
		Collection<Note> result;
		result = this.noteRepository.findAll();
		return result;
	}

	public Note save(Note note) {
		Assert.notNull(note);
		Note result;
		result = this.noteRepository.save(note);
		Date createdMoment;
		createdMoment = new Date(System.currentTimeMillis() - 1000);
		result.setCreatedMoment(createdMoment);
		Assert.notNull(result);
		return result;
	}

	// Other business methods------------------------------------------------------

	//Yo le paso una nota instrumentada y le hago un set reply y un setreplymoment
	// y ya se me modifica automáticamente y devuelvo la nota con los valores 
	//anteriores y los nuevos 
	public Note replyANote(Note note, String reply) {
		this.managerService.checkPrincipal();
		Date replyMoment;
		replyMoment = new Date(System.currentTimeMillis() - 1000);
		note.setReply(reply);
		note.setReplyMoment(replyMoment);
		Assert.notNull(note);
		return note;
	}
}
