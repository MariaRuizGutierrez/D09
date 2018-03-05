
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Tag;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class TagServiceTest extends AbstractTest {

	//Service under test----------------------------------------------------------	

	@Autowired
	private TagService	tagService;
	@Autowired
	private TripService	tripService;


	// Supporting services ----------------------------------------------------

	// Test --------------------------------------------------------------------

	@Test
	public void testCreate() {

		Tag tag;
		tag = this.tagService.create();
		Assert.notNull(tag);
	}

	@Test
	public void testSave() {
		final Tag tag = this.tagService.create();

		tag.setName("tag 11");

		final Tag newTag = this.tagService.save(tag);
		Assert.notNull(newTag);
	}

	@Test
	public void testDelete() {
		this.authenticate("admin");
		Tag tag;
		tag = this.tagService.findOne(super.getEntityId("tag2"));
		this.tagService.delete(tag);
		this.unauthenticate();
	}

	@Test
	public void testUpdate() {
		this.authenticate("admin");
		Tag tag;
		tag = this.tagService.create();
		tag.setId(7547);
		tag.setName("modificado1");
		this.tagService.save(tag);
		this.authenticate(null);
	}

	@Test
	public void testFindOne() {
		Tag tag;
		tag = this.tagService.findOne(super.getEntityId("tag1"));
		Assert.notNull(tag);
	}
	@Test
	public void testFindAll() {
		Collection<Tag> tags;
		tags = this.tagService.findAll();
		Assert.notNull(tags);
	}

	@Test
	public void testFindTagById() {
		Tag result;
		Tag tag;

		tag = this.tagService.findOne(super.getEntityId("tag1"));

		result = this.tagService.findTagById(tag.getId());
		Assert.notNull(result);
	}

	@Test
	public void findAllTagByTripId() {
		Collection<Tag> result;
		Trip trip;

		trip = this.tripService.findOne(super.getEntityId("trip1"));
		result = new ArrayList<Tag>(this.tagService.findAllTagByTripId(trip.getId()));

		Assert.notNull(result);
	}

}
