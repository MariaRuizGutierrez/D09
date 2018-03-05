
package controllers.explorer;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationSystemService;
import services.ExplorerService;
import services.FinderService;
import services.TripService;
import controllers.AbstractController;
import domain.Explorer;
import domain.Finder;
import domain.Trip;

@Controller
@RequestMapping("/finder/explorer")
public class FinderExplorerController extends AbstractController {

	// Services---------------------------------------------------------

	@Autowired
	private ExplorerService				explorerService;
	@Autowired
	private FinderService				finderService;
	@Autowired
	private TripService					tripService;
	@Autowired
	private ConfigurationSystemService	configurationSystemService;


	//Constructor--------------------------------------------------------

	public FinderExplorerController() {
		super();
	}
	// Search -----------------------------------------------------------------

	@RequestMapping(value = "/search", method = RequestMethod.POST, params = "search")
	public ModelAndView listByKeywordPriceDate(@Valid final Finder finder, final BindingResult bindingResult) {
		ModelAndView result;
		final Finder finderCache;
		Finder finderResult;
		Explorer explorerPrincipal;

		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(finder);
		else
			try {
				result = new ModelAndView("finder/list");
				explorerPrincipal = this.explorerService.findByPrincipal();
				finderCache = explorerPrincipal.getFinder();
				final Date date = new Date();

				//Compruebo si el finder a modificar pertenece al explorer
				Assert.isTrue(finderCache.getId() == finder.getId());

				//Obtengo los milisegundos que el admin permite que se guarde en cache y compruebo si ha vencido el tiempo
				int hoursCacheMaxTime;
				final int milisecondsCacheMaxTime;
				hoursCacheMaxTime = this.configurationSystemService.findOne().getCacheMaxTime();
				milisecondsCacheMaxTime = hoursCacheMaxTime * 3600000;

				if (!(this.finderService.checkEquals(finderCache, finder) && (date.getTime() - finderCache.getStartCacheTime().getTime()) <= milisecondsCacheMaxTime)) {
					finderResult = this.finderService.search(finder);
					finderResult = this.finderService.save(finderResult);
					explorerPrincipal.setFinder(finderResult);
					this.explorerService.save(explorerPrincipal);
				}

				result = new ModelAndView("redirect:/finder/explorer/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(finder, "finder.commit.error");
			}

		return result;

	}
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listTrip() {
		ModelAndView result;
		Collection<Trip> trips;
		Explorer explorer;
		double price;
		Finder finder;

		explorer = this.explorerService.findByPrincipal();
		finder = explorer.getFinder();
		trips = finder.getTrips();
		price = this.tripService.setPriceTrip(trips);

		result = new ModelAndView("finder/list");
		result.addObject("trips", trips);
		result.addObject("finder", finder);
		result.addObject("price", price);
		result.addObject("requestURI", "finder/explorer/list.do");
		result.addObject("requestURISearch", "finder/explorer/search.do");

		return result;
	}

	protected ModelAndView createEditModelAndView(final Finder finder) {
		assert finder != null;
		ModelAndView result;
		result = this.createEditModelAndView(finder, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Finder finder, final String message) {

		assert finder != null;
		ModelAndView result;

		result = new ModelAndView("finder/list");
		result.addObject("finder", finder);
		result.addObject("message", message);

		return result;

	}
}
