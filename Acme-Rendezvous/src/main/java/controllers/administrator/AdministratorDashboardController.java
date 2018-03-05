
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import controllers.AbstractController;
import domain.Rendezvouse;

@Controller
@RequestMapping(value = "/administrator")
public class AdministratorDashboardController extends AbstractController {

	//Services---------------------
	@Autowired
	private AdministratorService	administratorService;


	//Displaying----------------------

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView display() {

		ModelAndView result;
		result = new ModelAndView("administrator/dashboard");

		Double findAvgStddevOfTheNumOfRendezvouseCreatedPerUser[];
		Double findRatioUsersWithRendezvousesAndNotRendezvouses;
		Double findAvgStddevOfTheNumOfAssistansPerRendezvouse;
		Double findAvgStddevOfTheNumOfRendezvouseAssitedPerUser[];
		Collection<Rendezvouse> findTop10RendezvouseWithRSVPd;
		Double findAvgStddevOfTheNumOfAnnouncementsPerRendezvous[];
		Collection<Rendezvouse> findRendezvousesWithMore75PerCent;
		Collection<Rendezvouse> findRendezvousesWithAreLinked;
		Double findAvgStddevOfTheNumOfQuestionsPerRendezvous[];
		Double findAvgStddevOfTheNumOfAnswerToQuestionsPerRendezvous[];
		Double findAvgStddevOfTheNumOfRepliesPerComment[];

		findAvgStddevOfTheNumOfRendezvouseCreatedPerUser = this.administratorService.findAvgStddevOfTheNumOfRendezvouseCreatedPerUser();
		findRatioUsersWithRendezvousesAndNotRendezvouses = this.administratorService.findRatioUsersWithRendezvousesAndNotRendezvouses();
		findAvgStddevOfTheNumOfAssistansPerRendezvouse = this.administratorService.findAvgStddevOfTheNumOfAssistansPerRendezvouse();
		findAvgStddevOfTheNumOfRendezvouseAssitedPerUser = this.administratorService.findAvgStddevOfTheNumOfRendezvouseAssitedPerUser();
		findTop10RendezvouseWithRSVPd = this.administratorService.findTop10RendezvouseWithRSVPd();
		findAvgStddevOfTheNumOfAnnouncementsPerRendezvous = this.administratorService.findAvgStddevOfTheNumOfAnnouncementsPerRendezvous();
		findRendezvousesWithMore75PerCent = this.administratorService.findRendezvousesWithMore75PerCent();
		findRendezvousesWithAreLinked = this.administratorService.findRendezvousesWithAreLinked();
		findAvgStddevOfTheNumOfQuestionsPerRendezvous = this.administratorService.findAvgStddevOfTheNumOfQuestionsPerRendezvous();
		findAvgStddevOfTheNumOfAnswerToQuestionsPerRendezvous = this.administratorService.findAvgStddevOfTheNumOfAnswerToQuestionsPerRendezvous();
		findAvgStddevOfTheNumOfRepliesPerComment = this.administratorService.findAvgStddevOfTheNumOfRepliesPerComment();

		result.addObject("findAvgStddevOfTheNumOfRendezvouseCreatedPerUser", findAvgStddevOfTheNumOfRendezvouseCreatedPerUser);
		result.addObject("findRatioUsersWithRendezvousesAndNotRendezvouses", findRatioUsersWithRendezvousesAndNotRendezvouses);
		result.addObject("findAvgStddevOfTheNumOfAssistansPerRendezvouse", findAvgStddevOfTheNumOfAssistansPerRendezvouse);
		result.addObject("findAvgStddevOfTheNumOfRendezvouseAssitedPerUser", findAvgStddevOfTheNumOfRendezvouseAssitedPerUser);
		result.addObject("findTop10RendezvouseWithRSVPd", findTop10RendezvouseWithRSVPd);
		result.addObject("findAvgStddevOfTheNumOfAnnouncementsPerRendezvous", findAvgStddevOfTheNumOfAnnouncementsPerRendezvous);
		result.addObject("findRendezvousesWithMore75PerCent", findRendezvousesWithMore75PerCent);
		result.addObject("findRendezvousesWithAreLinked", findRendezvousesWithAreLinked);
		result.addObject("findAvgStddevOfTheNumOfQuestionsPerRendezvous", findAvgStddevOfTheNumOfQuestionsPerRendezvous);
		result.addObject("findAvgStddevOfTheNumOfAnswerToQuestionsPerRendezvous", findAvgStddevOfTheNumOfAnswerToQuestionsPerRendezvous);
		result.addObject("findAvgStddevOfTheNumOfRepliesPerComment", findAvgStddevOfTheNumOfRepliesPerComment);

		return result;

	}
}
