
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Comment;
import domain.Rendezvouse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CommentServiceTest extends AbstractTest {

	// Supporting services ----------------------------------------------------

	@Autowired
	CommentService		commentService;

	@Autowired
	RendezvouseService	rendezvouseService;


	// Requisito funcional: User comment on the rendezvouses that he or she has RSVPd.
	@SuppressWarnings("unchecked")
	@Test
	public void driverCreateAndSaveComment() {
		final Collection<Comment> listComment = this.createAllCommentForTesting();
		final Iterator<Comment> iterator = listComment.iterator();

		final Object testingData[][] = {
			{
				//El usuario "user1" va a crear un comment para una rendezvous a la que va a asistir
				null, "text test", "https://test.com", null, null, "rendezvouse1", "user1", null
			}, {
				//El usuario "user1" va a crear un comment para una rendezvos que no va a asistir
				null, "text test", "https://test.com", null, null, "rendezvouse2", "user1", IllegalArgumentException.class
			}, {
				//El usuario "user1" va a responder a un comentario escrito en una cita que va a asistir.
				null, "text test", "https://test.com", null, iterator.next(), "rendezvouse1", "user1", null
			}, {
				//El usuario "user3" va a responder a un comentario escrito en una cita que no va a asistir.
				null, "text test", "https://test.com", null, iterator.next(), "rendezvouse2", "user1", IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateAndSave((Date) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (List<Comment>) testingData[i][3], (Comment) testingData[i][4], super.getEntityId((String) testingData[i][5]),
				(String) testingData[i][6], (Class<?>) testingData[i][7]);
	}
	private void templateCreateAndSave(final Date writtenMoment, final String text, final String picture, final List<Comment> replys, final Comment commentTo, final int rendezvouseId, final String username, final Class<?> expected) {
		final Rendezvouse rendezvouseOfComment;
		Comment comment;
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			rendezvouseOfComment = this.rendezvouseService.findOne(rendezvouseId);
			comment = this.commentService.create();
			comment.setWrittenMoment(writtenMoment);
			comment.setText(text);
			comment.setPicture(picture);
			comment.setCommentTo(commentTo);
			comment.setRendezvouse(rendezvouseOfComment);
			comment = this.commentService.save(comment);
			this.unauthenticate();
			this.commentService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	private Collection<Comment> createAllCommentForTesting() {
		final Collection<Comment> result;
		final Comment commentOk;
		final Comment commentOtherRendezvous;

		result = new ArrayList<Comment>();
		commentOk = this.commentService.findOne(this.getEntityId("comment1"));
		commentOtherRendezvous = this.commentService.findOne(this.getEntityId("comment2"));

		result.add(commentOk);
		result.add(commentOtherRendezvous);

		return result;
	}

	// Requisito funcional: Admin Remove a comment that he or she thinks is inappropriate.
	@Test
	public void driverAdminDeleteComment() {

		final Object testingData[][] = {
			{
				//El admin va a borrar un comentario que ya existe.
				"admin", "comment1", null
			}, {
				//El usuario "user1" va a intentar borrar un comentario existente en el sistema
				"user1", "comment1", IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDelete((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	private void templateDelete(String actor, String comment, final Class<?> expected) {
		Comment comment1;

		Class<?> caught;

		caught = null;
		try {
			this.authenticate(actor);
			comment1 = this.commentService.findOne(this.getEntityId(comment));
			this.commentService.delete(comment1);
			this.unauthenticate();
			this.commentService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}
}
