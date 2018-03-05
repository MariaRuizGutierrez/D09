
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.StageRepository;
import domain.Stage;
import domain.Trip;

@Service
@Transactional
public class StageService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private StageRepository				stageRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private TripService					tripService;

	@Autowired
	private ConfigurationSystemService	configurationSystemService;


	// Constructors------------------------------------------------------------
	public StageService() {
		super();
	}

	public Stage create() {
		Stage result;
		result = new Stage();
		return result;
	}

	public Collection<Stage> findAll() {
		Collection<Stage> result;
		Assert.notNull(this.stageRepository);
		result = this.stageRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Stage findOne(final int stageId) {
		Stage result;
		result = this.stageRepository.findOne(stageId);
		Assert.notNull(result);
		return result;
	}

	public Stage save(final Stage stage) {
		Assert.notNull(stage);
		Trip trip;
		Stage result;

		trip = stage.getTrip();
		Assert.isTrue(trip.getId() != 0);
		trip = this.tripService.findOne(trip.getId());

		result = this.stageRepository.save(stage);
		//Si se añade stage (objeto no instrumentado) en vez de result (objeto instrumentado) daria un error de transient
		//Se añade el result al Trip manualmente porque es unidireccional la relacion
		trip.getStages().add(result);

		return result;
	}

	public void delete(final Stage stage) {
		Assert.notNull(stage);
		Assert.isTrue(stage.getId() != 0);
		Assert.isTrue(this.stageRepository.exists(stage.getId()));
		Trip trip;

		//La quito de Trip porque es unidireccional y no se actualiza ambas partes al eliminar el stage de la BD
		trip = stage.getTrip();
		Assert.isTrue(trip.getStages().contains(stage));
		trip.getStages().remove(stage);

		this.stageRepository.delete(stage);
	}

	// Other method bussisnes ---------------------------------------------------------
	public Collection<Stage> stages() {
		Collection<Stage> stages;

		stages = this.stageRepository.stages();
		return stages;
	}

	public void setTotalPrice(Collection<Stage> stages) {
		Double VAT = this.configurationSystemService.getVat();
		for (Stage s : stages)
			s.setTotalPrice(s.getPrice() * (1 + VAT));
	}
}
