
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
import domain.Question;
import domain.Rendezvouse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class QuestionServiceTest extends AbstractTest {

	// Supporting services ----------------------------------------------------
	@Autowired
	private QuestionService		questionService;

	@Autowired
	private RendezvouseService	rendezvouseService;


	// Requisito funcional: Crear y guardar una question.
	// Caso de uso 21.1: Manage the questions that are associated with a rendezvous that he or she's created previously.
	@Test
	public void driverCreateAndSaveQuestion() {

		final Object testingData[][] = {
			{
				//El usuario "user1" va a crear una pregunta para una cita que el ha creado.
				"question test", "user1", "rendezvouse1", null
			}, {

				//El usuario "user1" va a crear una pregunta para una cita que el no ha creado.
				"question test", "user1", "rendezvouse3", IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateAndSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	private void templateCreateAndSave(final String name, final String username, final String rendezvouse, final Class<?> expected) {
		Rendezvouse rendezvouseOfQuestion;
		Question question;
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			rendezvouseOfQuestion = this.rendezvouseService.findOne(this.getEntityId(rendezvouse));
			question = this.questionService.create();
			question.setRendezvouse(rendezvouseOfQuestion);
			question.setName(name);
			question = this.questionService.save(question);
			this.unauthenticate();
			this.questionService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	// Requisito funcional: Eliminar una pregunta.
	// Caso de uso 21.1: Manage the questions that are associated with a rendezvous that he or she's created previously.
	@Test
	public void driverDeleteQuestion() {

		final Object testingData[][] = {
			{
				//Esto da positivo porque esta restringido en el controlador y no puede hacerse en el servicio
				//El usuario "user1" va a intentar eliminar una pregunta que contiene respuestas.
				"question1", "user1", null
			}, {

				//El administrador "admin" va a intentar borrar una question que no existe
				"question2", "none", IllegalArgumentException.class
			}, {
				//El usuario "user2" va a eliminar la pregunta 2 que no tiene respuestas.
				"question3", "user2", null
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDelete((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	private void templateDelete(final String question, final String username, final Class<?> expected) {
		Question questionToDelete;
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			questionToDelete = this.questionService.findOne(this.getEntityId(question));
			this.questionService.delete(questionToDelete);
			this.unauthenticate();
			this.questionService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	// Requisito funcional: Editar una pregunta.
	// Caso de uso 21.1: Manage the questions that are associated with a rendezvous that he or she's created previously.
	@Test
	public void driverUpdateQuestion() {

		final Object testingData[][] = {
			{

				//El usuario "user1" va a intentar actualizar el nombre de la pregunta 1.
				"question1", "user1", "nombre modificado", IllegalArgumentException.class
			}, {
				//El usuario "user2" va a actualizar el nombre de la pregunta 2.
				"question2", "user2", "nombre modificado", null
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateUpdate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	private void templateUpdate(final String question, final String username, final String nameModificated, final Class<?> expected) {
		Question questionToUpdate;
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			questionToUpdate = this.questionService.findOne(this.getEntityId(question));
			questionToUpdate.setName(nameModificated);
			this.questionService.save(questionToUpdate);
			this.unauthenticate();
			this.questionService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	// Se comprueba el list de todas las question de la cita
	// Caso de uso 21.1: Manage the questions that are associated with a rendezvous that he or she's created previously.
	@Test
	public void driverQuestionNotDeleted() {
		final Object testingData[][] = {
			{
				// Se comprueba que la pregunta1 pertenece a la cita 1
				"question1", "rendezvouse1", null
			}, {
				// Se comprueba que la question1 no pertenece a la cita4
				"question1", "rendezvouse4", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateServicesAvailable(super.getEntityId((String) testingData[i][0]), super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}
	private void templateServicesAvailable(final int questionId, final int rendezvousId, final Class<?> expected) {
		final Collection<Question> questions;
		Question question;
		Class<?> caught;

		caught = null;
		try {
			question = this.questionService.findOne(questionId);
			questions = this.questionService.findAllQuestionsByRendezvous(rendezvousId);
			Assert.isTrue(questions.contains(question));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
