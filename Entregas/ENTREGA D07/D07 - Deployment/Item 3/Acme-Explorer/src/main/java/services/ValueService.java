
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ValueRepository;
import domain.Manager;
import domain.Tag;
import domain.Trip;
import domain.Value;

@Service
@Transactional
public class ValueService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ValueRepository	valueRepository;

	@Autowired
	private TagService		tagService;
	@Autowired
	private TripService		tripService;
	@Autowired
	private ManagerService	managerService;


	// Constructors------------------------------------------------------------
	public ValueService() {
		super();
	}

	// Simple CRUD methods-----------------------------------------------------

	public Value create() {
		Value result;
		result = new Value();

		return result;
	}

	public Value create(Integer tripId, Integer tagId) {
		Value result;
		Tag tag;
		Trip trip;

		tag = this.tagService.findTagById(tagId);
		trip = this.tripService.findOne(tripId);

		result = new Value();
		result.setTrip(trip);
		result.setTag(tag);

		return result;
	}
	public Value create(Integer value) {
		Value result;
		result = new Value();
		result.setValue(value);
		return result;
	}

	public Collection<Value> findAll() {
		Collection<Value> result;

		result = this.valueRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Value findOne(final int valueId) {
		Assert.isTrue(valueId != 0);

		Value result;
		result = this.valueRepository.findOne(valueId);
		Assert.notNull(result);

		return result;
	}

	public Value save1(final Value value, Trip trip, Tag tag) {
		Assert.notNull(value);
		Value result;
		Manager manager;
		Date date;

		date = new Date();
		manager = this.managerService.findByPrincipal();
		//SOLO PUEDE AÑADIR TAG A SUS TRIPS
		Assert.isTrue(manager.getTrips().contains(trip));
		//SOLO PUEDE AÑADIR TAG A LAS TRIPS QUE NO HAYAN SIDO PUBLICADAS
		Assert.isTrue(trip.getPublicationDate().after(date));
		result = this.valueRepository.save(value);
		result.setTag(tag);
		result.setTrip(trip);
		return result;
	}

	public Value save(final Value value) {
		Assert.notNull(value);
		Value result;
		result = this.valueRepository.save(value);
		return result;
	}

	public void delete(Value value) {

		Assert.notNull(value);
		Assert.isTrue(value.getId() != 0);

		this.valueRepository.delete(value);

	}

	public Collection<Value> defaultValues() {
		Collection<Value> values;
		values = new ArrayList<Value>();

		return values;
	}

}
