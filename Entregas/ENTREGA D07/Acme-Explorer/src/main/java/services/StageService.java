
package services;

import java.text.DecimalFormat;
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
		Collection<Stage> allStagesForTrip;
		Trip trip;
		Stage result;

		//Cuando vaya a salvar la stage tengo que comprobar que no exista ese numero en la bd
		trip = stage.getTrip();
		allStagesForTrip = stage.getTrip().getStages();
		for (Stage s : allStagesForTrip)
			Assert.isTrue(stage.getNumber() != s.getNumber(), "The number of this stage must be different");

		Assert.isTrue(trip.getId() != 0);
		trip = this.tripService.findOne(trip.getId());

		result = this.stageRepository.save(stage);
		if (!trip.getStages().contains(result))
			trip.getStages().add(result);

		//Si se añade stage (objeto no instrumentado) en vez de result (objeto instrumentado) daria un error de transient
		//Se añade el result al Trip manualmente porque es unidireccional la relacion
		//

		return result;
	}

	public void delete(final Stage stage) {
		Assert.notNull(stage);
		Assert.isTrue(stage.getId() != 0);
		Assert.isTrue(this.stageRepository.exists(stage.getId()));
		Trip trip;

		//La quito de Trip porque es unidireccional y no se actualiza ambas partes al eliminar el stage de la BD
		trip = stage.getTrip();
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

	public void setTotalPriceStage(Stage stage) {
		Double VAT = this.configurationSystemService.getVat();
		Double price = stage.getPrice() * (1 + VAT);
		DecimalFormat df = new DecimalFormat("0.00");
		price = new Double(df.format(price));

		stage.setTotalPrice(price);
	}

	public Collection<Stage> stagesOfTrip(int idTrip) {

		Collection<Stage> stages;

		stages = this.stageRepository.stagesOfTrip(idTrip);

		return stages;

	}
}
