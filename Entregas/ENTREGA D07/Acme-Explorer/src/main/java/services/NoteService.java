
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.NoteRepository;
import domain.Auditor;
import domain.Manager;
import domain.Note;

@Service
@Transactional
public class NoteService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private NoteRepository				noteRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ManagerService				managerService;

	@Autowired
	private AuditorService				auditorService;

	@Autowired
	private ConfigurationSystemService	configurationSystemService;


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
		Date createdMoment;
		String reply;

		createdMoment = new Date(System.currentTimeMillis() - 1000);
		reply = ("");

		result = this.noteRepository.save(note);
		result.setCreatedMoment(createdMoment);
		result.setReply(reply);

		Assert.notNull(result);

		return result;
	}

	public Note saveReply(Note note) {
		Assert.notNull(note);
		Note result;

		Date replyMoment;
		String reply;
		int leng = note.getReply().trim().length();
		reply = note.getReply().trim();
		replyMoment = new Date(System.currentTimeMillis() - 1000);

		if (reply.equals("") && (leng == 0))
			Assert.isTrue(4 == 3, "reply cannot be blank");

		result = this.noteRepository.save(note);
		result.setReplyMoment(replyMoment);
		Assert.notNull(result);
		return result;
	}
	// Other business methods------------------------------------------------------

	//Yo le paso una nota instrumentada y le hago un set reply y un setreplymoment
	// y ya se me modifica automáticamente y devuelvo la nota con los valores 
	//anteriores y los nuevos 
	public Note replyANote(int noteId) {
		this.managerService.checkPrincipal();
		Note note;
		Date replyMoment;

		note = this.findOne(noteId);

		replyMoment = new Date(System.currentTimeMillis() - 1000);
		note.setReplyMoment(replyMoment);
		Assert.notNull(note);

		return note;
	}

	public Collection<Note> findByPrincipal() {
		Collection<Note> notes;
		Auditor auditor;

		auditor = this.auditorService.findByPrincipal();
		notes = this.noteRepository.findNotesByAuditor(auditor.getId());

		return notes;
	}

	public Collection<Note> findNotesByManager() {
		Collection<Note> notes;
		Manager manager;

		manager = this.managerService.findByPrincipal();
		notes = this.noteRepository.findNotesByManager(manager.getId());

		return notes;
	}

	public Boolean noteContainsSpam(Auditor auditor) {
		Boolean result;
		Collection<Note> notes;
		Collection<String> words;
		Collection<String> spamWords;

		notes = this.noteRepository.findNotesByAuditor(auditor.getId());
		result = false;
		spamWords = this.configurationSystemService.spamWord();
		words = new ArrayList<String>();

		for (Note note : notes)
			words.add(note.getBody().toLowerCase());

		for (String word : words)
			for (String spam : spamWords)
				if (word.indexOf(spam) > -1) {
					result = true;
					break;
				}
		return result;
	}
}
