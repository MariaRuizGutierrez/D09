
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TagRepository;
import domain.Tag;
import domain.Trip;

@Service
@Transactional
public class TagService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private TagRepository			tagRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private TripService				tripService;


	// Constructors------------------------------------------------------------
	public TagService() {
		super();
	}

	// Simple CRUD methods-----------------------------------------------------
	public Tag create() {
		Tag result;
		result = new Tag();

		return result;
	}

	public Collection<Tag> findAll() {
		Collection<Tag> result;

		result = this.tagRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Tag findOne(final int tagId) {
		Assert.isTrue(tagId != 0);

		Tag result;
		result = this.tagRepository.findOne(tagId);
		Assert.notNull(result);

		return result;
	}

	public Tag save(final Tag tag) {
		Collection<Tag> tagsWithTrip;
		tagsWithTrip = this.tagRepository.findTagWithTrip();

		if (tag.getId() != 0) {
			Assert.isTrue(!tagsWithTrip.contains(tag));
			this.administratorService.checkPrincipal();
		}
		Assert.notNull(tag);

		Tag result;
		result = this.tagRepository.save(tag);
		Assert.notNull(result);

		return result;
	}
	public void delete(final Tag tag) {
		this.administratorService.checkPrincipal();
		Assert.notNull(tag);
		Assert.notNull(this.tagRepository.findOne(tag.getId()));
		final Collection<Trip> trips;

		trips = this.tripService.findAllTripsByTagId(tag.getId());
		for (final Trip t : trips)
			t.getTags().remove(tag);

		this.tagRepository.delete(tag);
	}

}
