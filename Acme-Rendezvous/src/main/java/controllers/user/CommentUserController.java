
package controllers.user;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import services.RendezvouseService;
import services.UserService;
import controllers.AbstractController;
import domain.Comment;
import domain.Rendezvouse;
import domain.User;

@Controller
@RequestMapping("/comment/user")
public class CommentUserController extends AbstractController {

	// Services---------------------------------------------------------

	@Autowired
	private CommentService		commentService;

	@Autowired
	private UserService			userService;

	@Autowired
	private RendezvouseService	rendezvouseService;


	//constructor-------------------------------------------------------------------------
	public CommentUserController() {
		super();
	}

	//---------------------LISTING--------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int rendezvouseId) {
		final ModelAndView result;
		Collection<Comment> comments;

		comments = this.commentService.commentsOfThisRendezvouseWithCommentNull(rendezvouseId);
		result = new ModelAndView("comment/list");

		result.addObject("comments", comments);
		result.addObject("requestURI", "/comment/user/list.do");

		return result;

	}

	@RequestMapping(value = "/listReplys", method = RequestMethod.GET)
	public ModelAndView listReplys(@RequestParam final int commentId) {
		final ModelAndView result;
		Collection<Comment> comments;
		Comment comment;

		comment = this.commentService.findOne(commentId);

		comments = comment.getReplys();
		result = new ModelAndView("comment/list");

		result.addObject("comments", comments);
		result.addObject("requestURI", "/comment/user/list.do");

		return result;

	}

	// Creation ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int rendezvouseId) {
		ModelAndView result;
		Comment comment;
		Rendezvouse rendezvouse;

		rendezvouse = this.rendezvouseService.findOne(rendezvouseId);
		comment = this.commentService.create();
		comment.setRendezvouse(rendezvouse);

		result = this.createEditModelAndView(comment);

		return result;
	}

	@RequestMapping(value = "/createReply", method = RequestMethod.GET)
	public ModelAndView createReply(@RequestParam final int commentId) {
		ModelAndView result;
		Comment comment;
		Comment resultComment;

		comment = this.commentService.findOne(commentId);
		resultComment = this.commentService.create();
		resultComment.setRendezvouse(comment.getRendezvouse());
		resultComment.setCommentTo(comment);

		result = this.createEditModelAndView(resultComment);

		return result;
	}
	// Edition ----------------------------------------------------------------

	//Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Comment comment, final BindingResult binding) {
		ModelAndView result;

		comment = this.commentService.reconstruct(comment, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(comment);
		else
			try {
				this.commentService.save(comment);
				result = new ModelAndView("redirect:list.do?rendezvouseId=" + comment.getRendezvouse().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(comment, "comment.commit.error");
			}
		return result;
	}

	// Display ----------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int commentId) {
		ModelAndView result;
		Comment comment = new Comment();

		comment = this.commentService.findOne(commentId);

		result = new ModelAndView("comment/display");
		result.addObject("comment", comment);
		result.addObject("requestURI", "comment/user/display.do");

		return result;
	}

	// Ancillary methods ------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Comment comment) {
		ModelAndView result;

		result = this.createEditModelAndView(comment, null);
		return result;
	}

	private ModelAndView createEditModelAndView(final Comment comment, final String message) {
		ModelAndView result;

		User user;
		Rendezvouse rendezvouse;

		rendezvouse = comment.getRendezvouse();
		user = this.userService.findByPrincipal();

		result = new ModelAndView("comment/edit");
		result.addObject("comment", comment);
		result.addObject("user", user);
		result.addObject("rendezvouse", rendezvouse);
		result.addObject("message", message);

		return result;
	}

}
