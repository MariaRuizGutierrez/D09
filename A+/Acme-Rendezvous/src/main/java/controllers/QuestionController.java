
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.QuestionService;
import domain.Question;

@Controller
@RequestMapping("/question")
public class QuestionController extends AbstractController {

	// Services---------------------------------------------------------

	@Autowired
	private QuestionService	questionService;


	//Listing-----------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView question(@RequestParam int rendezvouseId) {

		ModelAndView result;
		Collection<Question> questions;

		questions = this.questionService.findAllQuestionsByRendezvous(rendezvouseId);

		result = new ModelAndView("Question/display");
		result.addObject("Question", questions);
		result.addObject("requestURI", "question/list.do");

		return result;
	}

}
