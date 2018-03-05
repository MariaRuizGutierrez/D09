
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Auditor;
import domain.Note;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class NoteServiceTest extends AbstractTest {

	// Service under test ---------------------------------
	@Autowired
	private NoteService		noteService;

	@Autowired
	private AuditorService	auditorService;


	// Tests ----------------------------------------------

	@Test
	public void testCreate() {
		this.authenticate("auditor4");

		Note result;
		result = this.noteService.create();
		Assert.notNull(result);

		super.unauthenticate();
	}

	@Test
	public void testFindAll() {
		final Collection<Note> result = this.noteService.findAll();
		Assert.notEmpty(result);
	}

	@Test
	public void testSave() {
		this.authenticate("auditor4");
		Note note;
		Auditor auditor;
		String body;

		note = this.noteService.create();

		body = "note's test body";

		note.setRemark("remark");
		note.setBody(body);

		auditor = this.auditorService.findByPrincipal();
		Assert.notNull(auditor);
		note = this.noteService.save(note);

		super.unauthenticate();
	}

	@Test
	public void testfindOne() {
		Note note;
		note = this.noteService.findOne(super.getEntityId("note1"));
		Assert.notNull(note);
	}

	@Test
	public void testReplyNote() {

		this.authenticate("manager1");
		Note note;

		note = this.noteService.replyANote(super.getEntityId("note1"));

		note.setReply("hola");

	}

}
