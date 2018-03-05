
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TagRepository;
import domain.Tag;
import domain.Value;

@Service
@Transactional
public class TagService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private TagRepository	tagRepository;


	// Supporting services ----------------------------------------------------

	// Constructors------------------------------------------------------------
	public TagService() {
		super();
	}

	// Simple CRUD methods-----------------------------------------------------
	public Tag create() {
		Tag result;
		Collection<Value> values;

		values = new ArrayList<Value>();
		result = new Tag();
		result.setValues(values);
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
		Assert.notNull(tag);
		Collection<Value> values;
		Collection<Tag> tags;

		values = new ArrayList<Value>(tag.getValues());
		tags = this.tagRepository.findAll();

		//NO PUEDO MODIFICAR UNA TAG QUE TENGA UN TRIP ASOCIADO
		if (tag.getId() != 0)
			Assert.isTrue(values.size() == 0);
		Tag result;
		for (Tag t : tags)
			Assert.isTrue(!t.getName().equals(tag.getName()), "This name already exists");
		result = this.tagRepository.save(tag);
		Assert.notNull(result);

		return result;
	}
	public void delete(final Tag tag) {

		this.tagRepository.delete(tag);
	}

	public Collection<Tag> findAllTagByTripId(int tripId) {
		Collection<Tag> result;
		result = new ArrayList<Tag>(this.tagRepository.findAllTagByTripId(tripId));
		return result;
	}

	public Tag findTagById(int tagId) {
		Tag result;
		result = this.tagRepository.findTagById(tagId);
		return result;
	}

}
