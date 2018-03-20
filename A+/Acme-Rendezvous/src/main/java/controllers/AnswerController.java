
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AnswerService;
import domain.Answer;

@Controller
@RequestMapping("/answer")
public class AnswerController extends AbstractController {

	// Services---------------------------------------------------------

	@Autowired
	private AnswerService	answerService;


	//Constructor--------------------------------------------------------

	public AnswerController() {
		super();
	}

	// Display ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int questionId) {
		ModelAndView result;
		Collection<Answer> answers;

		answers = this.answerService.findAllAnswerByQuestionId(questionId);

		result = new ModelAndView("Answer/display");
		result.addObject("requestURI", "answer/list.do");
		result.addObject("Answer", answers);

		return result;
	}

}
