
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Tag;
import domain.Trip;
import domain.Value;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ValueServiceTest extends AbstractTest {

	// Service under test ---------------------------------
	@Autowired
	private ValueService	valueService;

	@Autowired
	private TripService		tripService;
	@Autowired
	private TagService		tagService;


	// Tests ----------------------------------------------
	@Test
	public void testCreate() {
		Value value;
		value = this.valueService.create();
		Assert.notNull(value);
	}

	@Test
	public void testCreateTripTag() {
		Value value;
		Trip trip;
		Tag tag;

		trip = this.tripService.findOne(super.getEntityId("trip1"));
		tag = this.tagService.findOne(super.getEntityId("tag1"));

		value = this.valueService.create(trip.getId(), tag.getId());
		Assert.notNull(value);
	}

	@Test
	public void testCreateValue() {
		Value value;
		Integer number;

		number = 7;
		value = this.valueService.create(number);
		Assert.notNull(value);
	}

	@Test
	public void testFindOne() {
		Value value;

		value = this.valueService.findOne(super.getEntityId("value1"));
		Assert.notNull(value);
	}

	@Test
	public void testDelete() {
		Value value;
		value = this.valueService.findOne(super.getEntityId("value1"));
		this.valueService.delete(value);
	}

	@Test
	public void testSave() {
		Value value;
		value = this.valueService.create();
		Assert.notNull(value);
	}

	@Test
	public void testSave1() {
		this.authenticate("manager5");
		Trip trip5;
		Tag tag;
		Value value;
		Value value1;

		value = new Value();
		value.setValue(7);
		tag = this.tagService.findOne(this.getEntityId("tag1"));
		trip5 = this.tripService.findOne(this.getEntityId("trip5"));

		value1 = this.valueService.save1(value, trip5, tag);
		Assert.notNull(value1);
	}

	@Test
	public void testDefaultValues() {
		Collection<Value> values;
		values = new ArrayList<Value>();
		Assert.notNull(values);
	}

}
