
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorshipRepository;
import domain.Sponsor;
import domain.Sponsorship;

@Service
@Transactional
public class SponsorshipService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private SponsorshipRepository	sponsorshipRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private SponsorService			sponsorService;


	// Constructors-------------------------------------------------------

	public SponsorshipService() {
		super();
	}

	// Simple CRUD methods------------------------------------------------

	public Sponsorship create() {
		Sponsorship result;
		Sponsor sponsorPrincipal;

		result = new Sponsorship();
		sponsorPrincipal = this.sponsorService.findByPrincipal();
		Assert.notNull(sponsorPrincipal);

		result.setSponsor(sponsorPrincipal);
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

}
