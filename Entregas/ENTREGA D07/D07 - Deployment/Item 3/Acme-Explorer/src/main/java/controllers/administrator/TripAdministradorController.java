
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.TripService;
import controllers.AbstractController;
import domain.Trip;

@Controller
@RequestMapping(value = "/trip/administrator")
public class TripAdministradorController extends AbstractController {

	@Autowired
	private TripService	tripService;


	//Listing------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int categoryId) {

		ModelAndView result;
		Collection<Trip> trips;
		double price;

		trips = this.tripService.findByCategory(categoryId);
		price = this.tripService.setPriceTrip(trips);

		result = new ModelAndView("trip/list");
		result.addObject("trips", trips);
		result.addObject("price", price);
		result.addObject("requestURISearch", "trip/search.do");
		result.addObject("requestURI", "trip/administrator/list.do");

		return result;

	}
}
