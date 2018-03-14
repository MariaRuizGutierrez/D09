
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AnswerRepository;
import domain.Answer;
import domain.Question;
import domain.User;

@Service
@Transactional
public class AnswerService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private AnswerRepository	answerRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private UserService			userService;

	//Importar la que pertenece a Spring
	@Autowired
	private Validator			validator;


	// Constructors -----------------------------------------------------------

	public AnswerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Answer create(final Question question) {
		Answer result;
		User user;

		user = this.userService.findByPrincipal();
		result = new Answer();

		result.setQuestion(question);
		result.setUser(user);
		return result;
	}

	public Collection<Answer> findAll() {
		Collection<Answer> result;
		result = this.answerRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Answer findOne(final int answerId) {
		Answer result;
		result = this.answerRepository.findOne(answerId);
		return result;
	}

	public Answer save(final Answer answer) {
		Assert.notNull(answer);
		Answer result;
		User user;

		user = this.userService.findByPrincipal();

		result = this.answerRepository.save(answer);
		answer.setUser(user);
		Assert.notNull(answer);
		return result;
	}

	public void delete(final Answer answer) {
		assert answer != null;
		assert answer.getId() != 0;
		Assert.isTrue(this.answerRepository.exists(answer.getId()));
		this.answerRepository.delete(answer);
	}

	// Other business methods -------------------------------------------------

	public Collection<Answer> findAllAnswerByQuestionId(final int questionId) {
		Collection<Answer> answers;

		answers = this.answerRepository.findAllAnswerByQuestionId(questionId);

		return answers;
	}

	public Collection<Answer> findAllAnswersByUserId() {
		Collection<Answer> answers;
		User user;

		user = this.userService.findByPrincipal();
		answers = this.answerRepository.findAllAnswersByUserId(user.getId());

		return answers;
	}

	public Answer reconstruct(final Answer answer, final BindingResult bindingResult) {
		Answer result;
		Answer answerBD;
		User userPrincipal;
		if (answer.getId() == 0) {
			result = answer;
			userPrincipal = this.userService.findByPrincipal();
			result.setUser(userPrincipal);
		} else {
			answerBD = this.answerRepository.findOne(answer.getId());
			answer.setId(answerBD.getId());
			answer.setVersion(answerBD.getVersion());
			answer.setUser(answerBD.getUser());
			result = answer;
		}
		this.validator.validate(result, bindingResult);
		return result;
	}

}
