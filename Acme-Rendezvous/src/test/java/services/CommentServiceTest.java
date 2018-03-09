
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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


	// Test CreateAndSave ----------------------------------------------------------------------------------

	@SuppressWarnings("unchecked")
	@Test
	public void driverCreateAndSave() {
		List<Comment> comments = new ArrayList<Comment>();
		final Object testingData[][] = {
			{
				//Se crea un Comment correctamente
				null, "text 1", "https://cloud.educaplay.com/recursos/110/3542066/imagen_1_1519849279.jpg", comments, null, "rendezvouse1", "user1", null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateAndSave((Date) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (List<Comment>) testingData[i][3], (Comment) testingData[i][4], super.getEntityId((String) testingData[i][5]),
				(String) testingData[i][6], (Class<?>) testingData[i][7]);
	}
	private void templateCreateAndSave(final Date writtenMoment, final String text, final String picture, final List<Comment> replys, final Comment commentTo, final int rendezvouseId, final String username, final Class<?> expected) {
		final Rendezvouse rendezvouseOfComment;
		Comment comment;
		Comment result;
		;
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			rendezvouseOfComment = this.rendezvouseService.findOne(rendezvouseId);
			comment = this.commentService.create();
			comment.setWrittenMoment(writtenMoment);
			comment.setText(text);
			comment.setPicture(picture);
			comment.setReplys(replys);
			comment.setCommentTo(commentTo);
			comment.setRendezvouse(rendezvouseOfComment);
			result = this.commentService.save(comment);
			this.unauthenticate();
			this.commentService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	//Other Methods additionals---------------------------------------------------------------------------------------

	private Collection<Comment> createAllCommentForTesting() {
		final Collection<Comment> result;

		final Comment commentOfThisUser;

		result = new ArrayList<Comment>();
		commentOfThisUser = this.commentService.findOne(super.getEntityId("comment1"));
		result.add(commentOfThisUser);
		return result;
	}
	//	// Test Edit ----------------------------------------------------------------------------------
	//
	//	@Test
	//	public void driverEdit() {
	//		final Object testingData[][] = {
	//			{
	//				//Se edita el announcement1 por el user que la ha creado
	//				"user1", "announcement1", null
	//			}, {
	//				//Se edita el announcement1 por el user que NO la ha creado (Hacking get)
	//				"user5", "announcement1", IllegalArgumentException.class
	//			}
	//		};
	//		for (int i = 0; i < testingData.length; i++)
	//			this.templateEdit((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	//	}
	//	private void templateEdit(final String username, final int announcementId, final Class<?> expected) {
	//		Announcement announcement;
	//		Class<?> caught;
	//
	//		caught = null;
	//		try {
	//			super.authenticate(username);
	//			announcement = this.announcementService.findOne(announcementId);
	//			announcement.setTitle("Editing test title");
	//			announcement = this.announcementService.save(announcement);
	//			this.unauthenticate();
	//			this.announcementService.flush();
	//		} catch (final Throwable oops) {
	//			caught = oops.getClass();
	//		}
	//
	//		this.checkExceptions(expected, caught);
	//
	//	}
	//
	//	// Test Delete ----------------------------------------------------------------------------------
	//
	//	@Test
	//	public void driverDelete() {
	//		final Object testingData[][] = {
	//			{
	//				//Se elimina el announcement1 por el user que la ha creado (ningun user puede eliminar un announcement)
	//				"user1", "announcement1", IllegalArgumentException.class
	//			}, {
	//				//Se elimina el announcement1 por el user que NO la ha creado(Hacking get) (ningun user puede eliminar un announcement)
	//				"user5", "announcement1", IllegalArgumentException.class
	//			}, {
	//				//Se elimina el announcement1 por un admin cualquiera (Cualquier admin puede eliminar cualquier announcement)
	//				"admin", "announcement1", null
	//			}
	//		};
	//		for (int i = 0; i < testingData.length; i++)
	//			this.templateDelete((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	//	}
	//	private void templateDelete(final String username, final int announcementId, final Class<?> expected) {
	//		Announcement announcement;
	//		Class<?> caught;
	//
	//		caught = null;
	//		try {
	//			super.authenticate(username);
	//			announcement = this.announcementService.findOne(announcementId);
	//			this.announcementService.delete(announcement);
	//			this.unauthenticate();
	//			this.announcementService.flush();
	//		} catch (final Throwable oops) {
	//			caught = oops.getClass();
	//		}
	//
	//		this.checkExceptions(expected, caught);
	//
	//	}

}
