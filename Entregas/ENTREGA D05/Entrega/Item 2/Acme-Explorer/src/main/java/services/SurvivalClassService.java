
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SurvivalClassRepository;
import domain.Administrator;
import domain.Explorer;
import domain.GPS;
import domain.Manager;
import domain.SurvivalClass;
import domain.Trip;

@Service
@Transactional
public class SurvivalClassService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private SurvivalClassRepository	survivalClassRecordRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ManagerService			managerService;


	// Constructors-------------------------------------------------------

	public SurvivalClassService() {

		super();
	}

	// Simple CRUD methods------------------------------------------------

	public SurvivalClass create(Administrator administrator) {

		this.managerService.checkPrincipal();

		Manager manager;
		Collection<Explorer> explorers;
		Date organisedMoment;
		Trip trip;
		GPS location;
		String title;
		String description;

		SurvivalClass result;

		manager = new Manager();
		explorers = new ArrayList<>();
		organisedMoment = new Date();
		trip = new Trip();
		location = new GPS();
		title = new String();
		description = new String();

		result = new SurvivalClass();
		result.setManager(manager);
		result.setExplorers(explorers);
		result.setOrganisedMoment(organisedMoment);
		result.setTrip(trip);
		result.setLocation(location);
		result.setTitle(title);
		result.setDescription(description);

		return result;
	}
	public Collection<SurvivalClass> findAll() {

		this.managerService.checkPrincipal();

		Collection<SurvivalClass> result;

		result = this.survivalClassRecordRepository.findAll();

		Assert.notNull(result);

		return result;
	}

	public SurvivalClass findOne(int survivalClassId) {

		SurvivalClass result;

		result = this.survivalClassRecordRepository.findOne(survivalClassId);

		return result;
	}

	public SurvivalClass save(SurvivalClass survivalClass) {

		this.managerService.checkPrincipal();

		Assert.notNull(survivalClass);

		SurvivalClass result;

		if (survivalClass.getId() == 0) {
			Date organisedMoment = new Date();
			survivalClass.setOrganisedMoment(organisedMoment);
		}

		result = this.survivalClassRecordRepository.save(survivalClass);

		Assert.notNull(result);

		return result;
	}
	public void delete(SurvivalClass survivalClass) {

		this.managerService.checkPrincipal();

		Assert.notNull(survivalClass);
		Assert.isTrue(survivalClass.getId() != 0);

		this.survivalClassRecordRepository.delete(survivalClass);

	}
}
