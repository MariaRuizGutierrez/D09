
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.QuestionRepository;
import domain.Answer;
import domain.Question;
import domain.User;

@Service
@Transactional
public class QuestionService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private QuestionRepository	questionRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private UserService			userService;

	@Autowired
	private AnswerService		answerService;

	//Importar la que pertenece a Spring
	@Autowired
	private Validator			validator;


	// Constructors -----------------------------------------------------------

	public QuestionService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Question create() {
		Question result;
		User user;

		user = this.userService.findByPrincipal();
		result = new Question();

		result.setUser(user);
		return result;
	}

	public Collection<Question> findAll() {
		Collection<Question> result;
		result = this.questionRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Question findOne(final int questionId) {
		Question result;
		result = this.questionRepository.findOne(questionId);
		return result;
	}

	public Question save(final Question question) {
		Assert.notNull(question);
		Question result;
		result = this.questionRepository.save(question);
		return result;
	}

	public void delete(final Question question) {
		assert question != null;
		assert question.getId() != 0;
		Assert.isTrue(this.questionRepository.exists(question.getId()));
		Collection<Answer> answers;
		int questionId;

		questionId = question.getId();
		answers = this.answerService.findAllAnswerByQuestionId(questionId);

		for (final Answer s : answers)
			this.answerService.delete(s);

		this.questionRepository.delete(question);
	}

	// Other business methods -------------------------------------------------

	public Collection<Question> findAllQuestionsByUser() {
		User user;
		Collection<Question> questions;

		user = this.userService.findByPrincipal();
		questions = this.questionRepository.findAllQuestionsByUser(user.getId());

		return questions;
	}

	public Collection<Question> findAllQuestionsByRendezvous(final int rendezvouseId) {
		Collection<Question> questions;

		questions = this.questionRepository.findAllQuestionsByRendezvous(rendezvouseId);
		return questions;
	}

	public Question reconstruct(final Question question, final BindingResult bindingResult) {
		Question result;
		Question questionBD;
		User userPrincipal;
		if (question.getId() == 0) {
			result = question;
			userPrincipal = this.userService.findByPrincipal();
			result.setUser(userPrincipal);
		} else {
			questionBD = this.questionRepository.findOne(question.getId());
			question.setId(questionBD.getId());
			question.setVersion(questionBD.getVersion());
			question.setUser(questionBD.getUser());
			result = question;
		}
		this.validator.validate(result, bindingResult);
		return result;
	}

}
