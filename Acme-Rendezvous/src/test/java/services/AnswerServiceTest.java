
package services;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Answer;
import domain.Question;
import domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AnswerServiceTest extends AbstractTest {

	// Supporting services ----------------------------------------------------

	@Autowired
	QuestionService	questionService;

	@Autowired
	UserService		userService;

	@Autowired
	AnswerService	answerService;

	@PersistenceContext
	EntityManager	entityManager;


	// Test CreateAndSave ----------------------------------------------------------------------------------
	// Se comprueba si se crean y guardan correctamente las Answers
	@Test
	public void driverCreateAndSave() {
		final Object testingData[][] = {
			{
				//El user1 responde una pregunta para la rendezvous que asiste
				"user1", "reply test", "question1", null
			}, {
				//El user5 responde una pregunta para la rendezvous que no asiste
				"user5", "reply test", "question2", IllegalArgumentException.class
			}, {
				//El user1 responde una pregunta para la rendezvous que asiste
				"user1", "", "question1", javax.validation.ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreateAndSave((String) testingData[i][0], (String) testingData[i][1], super.getEntityId((String) testingData[i][2]), (Class<?>) testingData[i][3]);
	}

	private void templateCreateAndSave(final String username, final String reply, final int questionId, final Class<?> expected) {
		final Question questionToAnswer;
		Answer answer;
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			questionToAnswer = this.questionService.findOne(questionId);
			answer = this.answerService.create(questionToAnswer);
			answer.setReply(reply);
			answer = this.answerService.save(answer);
			this.answerService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();

		}

		this.checkExceptions(expected, caught);
		this.unauthenticate();

	}
	// Test Edit ----------------------------------------------------------------------------------

	@Test
	public void driverEdit() {
		final Object testingData[][] = {
			{
				//El user1 edita una respuesta que le pertenece
				"user1", "answer1", null
			}, {
				//El user1 edita una respuesta que no le pertenece
				"user5", "answer1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateEdit((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}
	private void templateEdit(final String username, final int answerId, final Class<?> expected) {
		Answer answer;
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			answer = this.answerService.findOne(answerId);
			answer.setReply("reply test");
			answer = this.answerService.save(answer);
			this.unauthenticate();
			this.answerService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	// Test Delete ----------------------------------------------------------------------------------
	// Se comprueba que se eliminen las answer
	@Test
	public void driverDelete() {
		final Object testingData[][] = {
			{
				//Se elimina el answer1 por el manager que la ha creado
				"manager1", "answer1", IllegalArgumentException.class
			}, {
				//Se elimina el answer2 por el admin (debe dejar borrarse porque al borrar una Rendezvous se llama a este metodo para borrar todas las Answers
				//de esta Rendezvous
				"admin", "answer2", null
			}, {
				//Se elimina el answer1 por el user que la ha creado
				"user1", "answer1", null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDelete((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}
	private void templateDelete(final String username, final int answerId, final Class<?> expected) {
		Answer answer;
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			answer = this.answerService.findOne(answerId);
			this.answerService.delete(answer);
			this.unauthenticate();
			this.answerService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	// Test findAllAnswerByQuestionId(questionId) ------------------------------------------------------
	// Caso de uso 20.1
	@Test
	public void driverlistFindAllAnswerByQuestionId() {
		final Object testingData[][] = {
			{
				// Se comprueba que el metodo devuelve la answer1 del user1 para la question1
				"user1", "answer1", "question1", null
			}, {
				// Se comprueba que el metodo no devuelve la answer4 del user4 para la question1 porque la answer4 es de la question4 
				"user4", "answer4", "question1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templatelistFindAllAnswerByQuestionId((super.getEntityId((String) testingData[i][0])), (super.getEntityId((String) testingData[i][1])), (super.getEntityId((String) testingData[i][2])), (Class<?>) testingData[i][3]);
	}
	private void templatelistFindAllAnswerByQuestionId(final int userId, final int answerId, final int questionId, final Class<?> expected) {
		Collection<Answer> answersForQuestionId;
		final User user;
		Answer answer;
		Class<?> caught;

		caught = null;
		try {

			user = this.userService.findOne(userId);
			answer = this.answerService.findOne(answerId);
			answersForQuestionId = this.answerService.findAllAnswerByQuestionId(questionId);

			Assert.isTrue(answersForQuestionId.contains(answer));
			Assert.isTrue(answer.getUser().equals(user));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}
}
