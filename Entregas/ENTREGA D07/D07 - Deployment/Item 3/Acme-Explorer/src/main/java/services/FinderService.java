
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import domain.Finder;
import domain.Trip;

@Service
@Transactional
public class FinderService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private FinderRepository			finderRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ConfigurationSystemService	configurationSystemService;
	@Autowired
	private TripService					tripService;


	// Constructors -----------------------------------------------------------

	public FinderService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Finder create() {
		Finder result;
		Collection<Trip> trips;

		result = new Finder();
		trips = new ArrayList<Trip>();

		trips = this.tripService.findAll();
		result.setStartCacheTime(new Date());

		result.setTrips(trips);

		return result;
	}
	public Collection<Finder> findAll() {
		Collection<Finder> result;

		Assert.notNull(this.finderRepository);
		result = this.finderRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Finder findOne(final int finderId) {
		Finder result;

		result = this.finderRepository.findOne(finderId);
		Assert.notNull(result);

		return result;
	}

	public Finder save(final Finder finder) {
		Finder newFinder;
		Assert.notNull(finder);

		finder.setStartCacheTime(new Date());
		newFinder = this.finderRepository.save(finder);
		Assert.notNull(newFinder);

		return newFinder;
	}

	public void delete(final Finder finder) {

		this.finderRepository.delete(finder);
	}

	// Other business methods -------------------------------------------------

	public Finder search(final Finder finder) {
		final Collection<Trip> trips;
		final Page<Trip> pages;
		int maxNumberFinder;

		maxNumberFinder = this.configurationSystemService.findOne().getMaxNumberFinder();
		final Pageable pageable = new PageRequest(0, maxNumberFinder);

		if (finder.getKeyWord() == null)
			finder.setKeyWord("");
		pages = this.finderRepository.findByKeyWord(finder.getKeyWord(), pageable);
		trips = new ArrayList<Trip>(pages.getContent());

		if (finder.getLowPrice() != null) {
			final Page<Trip> pageLowPrice;
			pageLowPrice = this.finderRepository.findByLowPrice(finder.getLowPrice(), pageable);
			final Collection<Trip> tripsLowPrice = pageLowPrice.getContent();
			trips.retainAll(tripsLowPrice);
		}

		if (finder.getHighPrice() != null) {
			Page<Trip> tripsHighPrice;
			tripsHighPrice = this.finderRepository.findByHighPrice(finder.getHighPrice(), pageable);
			trips.retainAll(tripsHighPrice.getContent());
		}

		if (finder.getInitialDate() != null) {
			Page<Trip> tripsInitialDate;
			tripsInitialDate = this.finderRepository.findByInitialDate(finder.getInitialDate(), pageable);
			trips.retainAll(tripsInitialDate.getContent());
		}

		if (finder.getFinalDate() != null) {
			Page<Trip> tripsFinalDate;
			tripsFinalDate = this.finderRepository.findByFinalDate(finder.getFinalDate(), pageable);
			trips.retainAll(tripsFinalDate.getContent());
		}

		finder.setTrips(trips);
		return finder;
	}
	public boolean checkEquals(final Finder finderCache, final Finder finder) {
		boolean result;

		result = false;
		if (this.compareKeyWord(finderCache.getKeyWord(), finder.getKeyWord()) && (this.compareLowPrice(finderCache.getLowPrice(), finder.getLowPrice())) && (this.compareHighPrice(finderCache.getHighPrice(), finder.getHighPrice()))
			&& (this.compareInitialDate(finderCache.getInitialDate(), finder.getInitialDate())) && this.compareFinalDate(finderCache.getFinalDate(), finder.getFinalDate()))
			result = true;

		return result;
	}

	private boolean compareFinalDate(final Date finalDate, final Date finalDate2) {
		boolean result;

		result = false;
		if (finalDate == null && finalDate2 == null)
			result = true;
		else if ((finalDate == null && finalDate2 != null) || (finalDate != null && finalDate2 == null))
			result = false;
		else if (finalDate.equals(finalDate2))
			result = true;
		return result;
	}

	private boolean compareInitialDate(final Date initialDate, final Date initialDate2) {
		boolean result;

		result = false;
		if (initialDate == null && initialDate2 == null)
			result = true;
		else if ((initialDate == null && initialDate2 != null) || (initialDate != null && initialDate2 == null))
			result = false;
		else if (initialDate.equals(initialDate2))
			result = true;
		return result;
	}

	private boolean compareHighPrice(final Double highPrice, final Double highPrice2) {
		boolean result;

		result = false;
		if (highPrice == null && highPrice2 == null)
			result = true;
		else if ((highPrice == null && highPrice2 != null) || (highPrice != null && highPrice2 == null))
			result = false;
		else if (highPrice.equals(highPrice2))
			result = true;
		return result;
	}

	private boolean compareLowPrice(final Double lowPrice, final Double lowPrice2) {
		boolean result;

		result = false;
		if (lowPrice == null && lowPrice2 == null)
			result = true;
		else if ((lowPrice == null && lowPrice2 != null) || (lowPrice != null && lowPrice2 == null))
			result = false;
		else if (lowPrice.equals(lowPrice2))
			result = true;
		return result;
	}

	private boolean compareKeyWord(final String keyWord, final String keyWord2) {
		boolean result;

		result = false;
		if (keyWord == null && keyWord2 == null)
			result = true;
		else if ((keyWord == null && keyWord2 != null) || (keyWord != null && keyWord2 == null))
			result = false;
		else if (keyWord.equals(keyWord2))
			result = true;
		return result;
	}
}
