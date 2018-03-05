
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import controllers.AbstractController;
import domain.Trip;

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

		Double findAvgMinMaxStddevOfTheNumOfApplicationsPerTrip[];
		Double findAvgMinMaxStddevOfTheNumOfTripsPerManager[];
		Double findAvgMinMaxStddevOfThePriceOfTheTrips[];
		Double findAvgMinMaxStddevOfTheNumTripsPerRanger[];
		Double findRatOfApplicationsPending;
		Double findRatioOfApplicationsDue;
		Double findRatOfApplicationsAccepted;
		Double findRatOfApplicationsCancelled;
		Double findRatOfTheTripsCancelledvsTripsOrganised;
		Collection<Trip> findTrips10porcentMoreApplicationsThanAvg;
		Collection<String> titlesOfLegalText;
		Collection<Integer> findNumOfReferencesOfLegalText;
		Double findMinMaxAvgStddevOfTheNumOfNotesPerTrip[];
		Double findMinMaxAvgStddevOfTheNumOfAuditRecordsPerTrip[];
		Double findTheRatOfTripsWihoutAnAuditRecord;
		Double findTheRatOfRangersWhoHaveRegisteredCurricula;
		Double findTheRatOfRangersWhoseCurrIsEndorsed;
		Double findTheRatOFSuspiciousManagers;
		Double findTheRatOFSuspiciousRangers;

		findAvgMinMaxStddevOfTheNumOfApplicationsPerTrip = this.administratorService.findAvgMinMaxStddevOfTheNumOfApplicationsPerTrip();
		findAvgMinMaxStddevOfTheNumOfTripsPerManager = this.administratorService.findAvgMinMaxStddevOfTheNumOfTripsPerManager();
		findAvgMinMaxStddevOfThePriceOfTheTrips = this.administratorService.findAvgMinMaxStddevOfThePriceOfTheTrips();
		findAvgMinMaxStddevOfTheNumTripsPerRanger = this.administratorService.findAvgMinMaxStddevOfTheNumTripsPerRanger();
		findRatOfApplicationsPending = this.administratorService.findRatOfApplicationsPending();
		findRatioOfApplicationsDue = this.administratorService.findRatioOfApplicationsDue();
		findRatOfApplicationsAccepted = this.administratorService.findRatOfApplicationsAccepted();
		findRatOfApplicationsCancelled = this.administratorService.findRatOfApplicationsCancelled();
		findRatOfTheTripsCancelledvsTripsOrganised = this.administratorService.findRatOfTheTripsCancelledvsTripsOrganised();
		findTrips10porcentMoreApplicationsThanAvg = this.administratorService.findTrips10porcentMoreApplicationsThanAvg();
		titlesOfLegalText = this.administratorService.titlesOfLegalText();
		findNumOfReferencesOfLegalText = this.administratorService.findNumOfReferencesOfLegalText();
		findMinMaxAvgStddevOfTheNumOfNotesPerTrip = this.administratorService.findMinMaxAvgStddevOfTheNumOfNotesPerTrip();
		findMinMaxAvgStddevOfTheNumOfAuditRecordsPerTrip = this.administratorService.findMinMaxAvgStddevOfTheNumOfAuditRecordsPerTrip();
		findTheRatOfTripsWihoutAnAuditRecord = this.administratorService.findTheRatOfTripsWihoutAnAuditRecord();
		findTheRatOfRangersWhoHaveRegisteredCurricula = this.administratorService.findTheRatOfRangersWhoHaveRegisteredCurricula();
		findTheRatOfRangersWhoseCurrIsEndorsed = this.administratorService.findTheRatOfRangersWhoseCurrIsEndorsed();
		findTheRatOFSuspiciousManagers = this.administratorService.findTheRatOFSuspiciousManagers();
		findTheRatOFSuspiciousRangers = this.administratorService.findTheRatOFSuspiciousRangers();

		result.addObject("findAvgMinMaxStddevOfTheNumOfApplicationsPerTrip", findAvgMinMaxStddevOfTheNumOfApplicationsPerTrip);
		result.addObject("findAvgMinMaxStddevOfTheNumOfTripsPerManager", findAvgMinMaxStddevOfTheNumOfTripsPerManager);
		result.addObject("findAvgMinMaxStddevOfThePriceOfTheTrips", findAvgMinMaxStddevOfThePriceOfTheTrips);
		result.addObject("findAvgMinMaxStddevOfTheNumTripsPerRanger", findAvgMinMaxStddevOfTheNumTripsPerRanger);
		result.addObject("findRatOfApplicationsPending", findRatOfApplicationsPending);
		result.addObject("findRatioOfApplicationsDue", findRatioOfApplicationsDue);
		result.addObject("findRatOfApplicationsAccepted", findRatOfApplicationsAccepted);
		result.addObject("findRatOfApplicationsCancelled", findRatOfApplicationsCancelled);
		result.addObject("findRatOfTheTripsCancelledvsTripsOrganised", findRatOfTheTripsCancelledvsTripsOrganised);
		result.addObject("findTrips10porcentMoreApplicationsThanAvg", findTrips10porcentMoreApplicationsThanAvg);
		result.addObject("titlesOfLegalText", titlesOfLegalText);
		result.addObject("findNumOfReferencesOfLegalText", findNumOfReferencesOfLegalText);
		result.addObject("findMinMaxAvgStddevOfTheNumOfNotesPerTrip", findMinMaxAvgStddevOfTheNumOfNotesPerTrip);
		result.addObject("findMinMaxAvgStddevOfTheNumOfAuditRecordsPerTrip", findMinMaxAvgStddevOfTheNumOfAuditRecordsPerTrip);
		result.addObject("findTheRatOfTripsWihoutAnAuditRecord", findTheRatOfTripsWihoutAnAuditRecord);
		result.addObject("findTheRatOfRangersWhoHaveRegisteredCurricula", findTheRatOfRangersWhoHaveRegisteredCurricula);
		result.addObject("findTheRatOfRangersWhoseCurrIsEndorsed", findTheRatOfRangersWhoseCurrIsEndorsed);
		result.addObject("findTheRatOFSuspiciousManagers", findTheRatOFSuspiciousManagers);
		result.addObject("findTheRatOFSuspiciousRangers", findTheRatOFSuspiciousRangers);

		return result;

	}
}
