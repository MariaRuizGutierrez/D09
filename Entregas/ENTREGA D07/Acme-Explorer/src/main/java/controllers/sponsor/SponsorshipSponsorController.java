
package controllers.sponsor;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SponsorService;
import services.SponsorshipService;
import services.TripService;
import controllers.AbstractController;
import domain.Sponsor;
import domain.Sponsorship;
import domain.Trip;

@Controller
@RequestMapping("/sponsorship/sponsor")
public class SponsorshipSponsorController extends AbstractController {

	// Services---------------------------------------------------------

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private TripService			tripService;

	@Autowired
	private SponsorService		sponsorService;


	//Constructor--------------------------------------------------------

	public SponsorshipSponsorController() {
		super();
	}

	//Listing------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Sponsorship> sponsorships;

		sponsorships = this.sponsorshipService.findByPrincipal();

		result = new ModelAndView("sponsorship/list");
		result.addObject("sponsorships", sponsorships);

		return result;

	}

	//Creation-----------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int tripId) {
		ModelAndView result;
		Sponsorship sponsorship;
		Date date;
		Trip trip;

		trip = this.tripService.findOne(tripId);
		date = new Date();

		Assert.isTrue(trip.getFinishDate().after(date) && trip.isCancelled() == false);
		sponsorship = this.sponsorshipService.create(trip);

		result = this.createEditModelAndView(sponsorship);
		result.addObject("date", date);
		return result;

	}
	//Edition------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int sponsorshipId) {
		ModelAndView result;
		Sponsorship sponsorship;
		Sponsor sponsor;

		sponsor = this.sponsorService.findByPrincipal();
		sponsorship = this.sponsorshipService.findOne(sponsorshipId);
		Assert.notNull(sponsorship);
		Assert.isTrue(sponsor.getSponsorships().contains(sponsorship), "Cannot commit this operation, because it's illegal");
		result = this.createEditModelAndView(sponsorship);
		result.addObject("sponsor", sponsor);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Sponsorship sponsorship, final BindingResult bindingResult) {
		ModelAndView result;

		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(sponsorship, "sponsorship.save.commit.error");
		else
			try {
				this.sponsorshipService.save(sponsorship);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(sponsorship, "sponsorship.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@ModelAttribute final Sponsorship sponsorship, final BindingResult bindingResult) {
		ModelAndView result;

		try {
			this.sponsorshipService.delete(sponsorship);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(sponsorship, "sponsorship.commit.error");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Sponsorship sponsorship) {
		assert sponsorship != null;
		ModelAndView result;
		result = this.createEditModelAndView(sponsorship, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Sponsorship sponsorship, final String message) {

		assert sponsorship != null;
		ModelAndView result;
		Collection<Trip> trips;

		trips = this.tripService.findAll();

		result = new ModelAndView("sponsorship/edit");
		result.addObject("sponsorship", sponsorship);
		result.addObject("message", message);
		result.addObject("trips", trips);

		return result;

	}

	// Display ----------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int sponsorshipId) {
		ModelAndView result;
		Sponsorship sponsorship;

		sponsorship = this.sponsorshipService.findOne(sponsorshipId);
		result = new ModelAndView("sponsorship/display");
		result.addObject("sponsorship", sponsorship);

		return result;
	}

	// Display ----------------------------------------------------------------
	@RequestMapping(value = "/displayTrip", method = RequestMethod.GET)
	public ModelAndView displayTrip(@RequestParam int sponsorshipId) {
		ModelAndView result;
		Trip trip;
		Sponsorship sponsorship;
		sponsorship = this.sponsorshipService.findOne(sponsorshipId);
		trip = this.sponsorshipService.findByPrincipalSponsorhipTrip(sponsorship);
		result = new ModelAndView("sponsorship/displayTrip");
		result.addObject("trip", trip);
		result.addObject("sponsorship", sponsorship);

		return result;
	}

}
