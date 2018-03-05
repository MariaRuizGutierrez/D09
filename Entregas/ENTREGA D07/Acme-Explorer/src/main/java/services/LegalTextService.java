
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.LegalTextRepository;
import domain.LegalText;
import domain.Trip;

@Service
@Transactional
public class LegalTextService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private LegalTextRepository		legalTextRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private AdministratorService	administratorService;


	// Constructors------------------------------------------------------------
	public LegalTextService() {
		super();
	}

	// Simple CRUD methods-----------------------------------------------------
	public LegalText create() {
		Assert.notNull(this.administratorService.findByPrincipal());

		LegalText result;
		Date moment;
		Collection<Trip> trips;

		result = new LegalText();
		moment = new Date(System.currentTimeMillis() - 1000);
		trips = new ArrayList<Trip>();

		result.setMoment(moment);
		result.setTrips(trips);

		return result;
	}
	public Collection<LegalText> findAll() {
		Collection<LegalText> result;

		result = this.legalTextRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public LegalText findOne(final int legalTextId) {
		Assert.notNull(legalTextId);

		LegalText result;
		result = this.legalTextRepository.findOne(legalTextId);

		return result;
	}

	public LegalText save(final LegalText legalText) {

		Assert.notNull(legalText);

		Assert.notNull(this.administratorService.findByPrincipal());

		if (legalText.isDraftMode() == true && !legalText.getTrips().isEmpty())
			Assert.isTrue(legalText.isDraftMode() == true && legalText.getTrips().isEmpty());

		LegalText result;

		result = this.legalTextRepository.save(legalText);

		return result;
	}
	public void delete(final LegalText legalText) {
		Assert.isTrue(legalText.getId() != 0);
		Assert.notNull(legalText);
		//Compruebo que no esté guardado en modo final y poder borrarlo
		Assert.isTrue(legalText.isDraftMode() == true);

		this.legalTextRepository.delete(legalText);
	}

	public Collection<LegalText> findAllLegalTextByTripId(int tripId) {
		Collection<LegalText> result;
		result = new ArrayList<LegalText>(this.legalTextRepository.findAllLegalTextByTripId(tripId));
		return result;
	}

	//Other services-------------------------------

	public LegalText findOneToEdit(final int idlegalText) {

		this.administratorService.checkPrincipal();

		LegalText legalText;

		legalText = this.findOne(idlegalText);
		Assert.isTrue(legalText.isDraftMode() == true);

		return legalText;

	}

	public LegalText addTrip(final LegalText legalText) {

		Assert.notNull(legalText);

		Assert.notNull(this.administratorService.findByPrincipal());

		Assert.isTrue(legalText.isDraftMode() == false);

		LegalText result;

		result = this.legalTextRepository.save(legalText);

		return result;
	}
}
