
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorshipRepository;
import domain.Sponsor;
import domain.Sponsorship;
import domain.Trip;

@Service
@Transactional
public class SponsorshipService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private SponsorshipRepository	sponsorshipRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private SponsorService			sponsorService;

	@Autowired
	private ApplicationForService	applicationForService;


	// Constructors-------------------------------------------------------

	public SponsorshipService() {
		super();
	}

	// Simple CRUD methods------------------------------------------------

	public Sponsorship create(Trip trip) {
		Sponsorship result;
		Sponsor sponsorPrincipal;

		result = new Sponsorship();
		sponsorPrincipal = this.sponsorService.findByPrincipal();
		Assert.notNull(sponsorPrincipal);

		result.setSponsor(sponsorPrincipal);
		result.setTrip(trip);
		return result;
	}

	public Sponsorship findOne(final int sponsorshipId) {
		Assert.isTrue(sponsorshipId != 0);
		Sponsorship result;
		result = this.sponsorshipRepository.findOne(sponsorshipId);
		return result;
	}

	public Collection<Sponsorship> findAll() {
		Collection<Sponsorship> result;
		result = this.sponsorshipRepository.findAll();
		return result;
	}

	public Sponsorship save(Sponsorship sponsorship) {
		Assert.notNull(sponsorship);
		Sponsorship result;
		Assert.isTrue(this.applicationForService.checkCreditCard(sponsorship.getCreditCard()));
		result = this.sponsorshipRepository.save(sponsorship);
		Assert.notNull(result);
		return result;
	}

	public void delete(final Sponsorship sponsorship) {
		assert sponsorship != null;
		assert sponsorship.getId() != 0;
		Assert.isTrue(this.sponsorshipRepository.exists(sponsorship.getId()));
		this.sponsorshipRepository.delete(sponsorship);
	}

	//Other business methods------------------------------------------------

	public Collection<Sponsorship> findByPrincipal() {
		Collection<Sponsorship> result;
		Sponsor sponsor;

		sponsor = this.sponsorService.findByPrincipal();
		result = this.sponsorshipRepository.findBySponsorId(sponsor.getId());

		return result;
	}

	public Collection<Sponsorship> findAllSponsorshipByTripId(int tripId) {
		Collection<Sponsorship> result;
		result = new ArrayList<Sponsorship>(this.sponsorshipRepository.findAllSponsorshipByTripId(tripId));
		return result;
	}

	public Trip findByPrincipalSponsorhipTrip(Sponsorship sponsorship) {
		Trip result;

		result = this.sponsorshipRepository.findTripBySponsorship(sponsorship.getId());
		return result;
	}

	public String randomSponsorship(Trip trip) {
		List<Sponsorship> sponsorships;
		Integer size;
		String url;

		url = "nothing to show";

		sponsorships = this.sponsorshipRepository.findAllSponsorshipByTripId(trip.getId());
		size = sponsorships.size();

		if (size != 0) {
			int rand = (int) (Math.random() * size);
			url = sponsorships.get(rand).getBannerURL();
		}

		return url;
	}
}
