
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CommentRepository;
import domain.Comment;
import domain.User;

@Service
@Transactional
public class CommentService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private CommentRepository		commentRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private UserService				userService;

	@Autowired
	private AdministratorService	administratorService;

	//Importar la que pertenece a Spring
	@Autowired
	private Validator				validator;


	// Constructors------------------------------------------------------------
	public CommentService() {
		super();
	}

	// Simple CRUD methods-----------------------------------------------------

	public Comment create() {
		Comment result;
		Collection<Comment> replys;
		User user;
		Date moment;

		moment = new Date(System.currentTimeMillis() - 1000);
		result = new Comment();
		replys = new ArrayList<Comment>();
		user = this.userService.findByPrincipal();

		result.setReplys(replys);
		result.setUser(user);
		result.setWrittenMoment(moment);

		return result;

	}
	public Comment save(final Comment comment) {
		Date moment;
		User userConnected;

		moment = new Date();
		userConnected = this.userService.findByPrincipal();

		Assert.notNull(comment);
		Assert.isTrue(comment.getRendezvouse().getAssistants().contains(userConnected));

		Comment result;

		moment = new Date(System.currentTimeMillis() - 1000);

		this.userService.checkPrincipal();
		Assert.isTrue(comment.getUser().equals(userConnected));
		Assert.isTrue(comment.getId() == 0);

		comment.setWrittenMoment(moment);
		result = this.commentRepository.save(comment);

		return result;

	}

	public void delete(final Comment comment) {
		Assert.notNull(comment);
		Assert.isTrue(comment.getId() != 0);

		Assert.isTrue(this.commentRepository.findOne(comment.getId()) != null);

		this.administratorService.checkPrincipal();

		if (comment.getReplys().size() != 0)
			for (final Comment c : comment.getReplys())
				this.delete(c);

		this.commentRepository.delete(comment);

	}
	public Comment findOne(final int commentId) {
		Assert.isTrue(commentId != 0);
		Comment result;

		result = this.commentRepository.findOne(commentId);
		return result;
	}

	public Collection<Comment> findAll() {
		Collection<Comment> result;

		result = this.commentRepository.findAll();

		return result;
	}

	//Other methods bussisnes

	public Collection<Comment> commentsOfThisRendezvouse(final int rendezvouseId) {
		Collection<Comment> commentsOfThisRendezvouse;

		commentsOfThisRendezvouse = this.commentRepository.commentsOfThisRendezvouse(rendezvouseId);

		return commentsOfThisRendezvouse;
	}

	public Collection<Comment> findAllCommentsByRendezvousId(final int rendezvousId) {
		Collection<Comment> result;
		result = this.commentRepository.findAllCommentsByRendezvousId(rendezvousId);
		return result;

	}

	public Collection<Comment> commentsOfThisRendezvouseWithCommentNull(final int rendezvouseId) {
		Collection<Comment> commentsOfThisRendezvouse;

		commentsOfThisRendezvouse = this.commentRepository.commentsOfThisRendezvouseWithCommentNull(rendezvouseId);

		return commentsOfThisRendezvouse;
	}

	public Collection<Comment> commentTofindAllCommentsByRendezvousId(final int renzvousId) {
		Collection<Comment> result;
		result = this.commentRepository.commentTofindAllCommentsByRendezvousId(renzvousId);
		return result;
	}

	public Comment reconstruct(final Comment comment, final BindingResult binding) {
		Comment result;
		Comment commentBD;
		User userPrincipal;
		if (comment.getId() == 0) {
			Collection<Comment> replys;
			Date moment;

			result = comment;
			moment = new Date(System.currentTimeMillis() - 1000);
			replys = new ArrayList<Comment>();
			userPrincipal = this.userService.findByPrincipal();
			result.setUser(userPrincipal);
			result.setReplys(replys);
			result.setWrittenMoment(moment);
		} else {
			commentBD = this.commentRepository.findOne(comment.getId());
			comment.setId(commentBD.getId());
			comment.setVersion(commentBD.getVersion());
			comment.setUser(commentBD.getUser());
			comment.setReplys(commentBD.getReplys());
			comment.setWrittenMoment(commentBD.getWrittenMoment());
			result = comment;
		}
		this.validator.validate(result, binding);
		return result;
	}
}
