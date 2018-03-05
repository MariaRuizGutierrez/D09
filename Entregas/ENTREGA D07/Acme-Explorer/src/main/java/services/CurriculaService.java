
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CurriculaRepository;
import domain.Actor;
import domain.Curricula;
import domain.EducationRecord;
import domain.EndorserRecord;
import domain.MiscellaneousRecord;
import domain.ProfessionalRecord;
import domain.Ranger;

@Service
@Transactional
public class CurriculaService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private CurriculaRepository			curriculaRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private RangerService				rangerService;

	@Autowired
	private ConfigurationSystemService	configurationSystemService;


	// Constructors-------------------------------------------------------

	public CurriculaService() {
		super();
	}

	// Simple CRUD methods------------------------------------------------

	public Curricula create() {

		this.rangerService.checkPrincipal();

		Curricula curricula;
		List<ProfessionalRecord> professionalRecords;
		List<MiscellaneousRecord> miscellaneousRecords;
		List<EndorserRecord> endorserRecords;
		List<EducationRecord> educationRecords;
		Ranger ranger;

		ranger = this.rangerService.findByPrincipal();
		professionalRecords = new ArrayList<ProfessionalRecord>();
		miscellaneousRecords = new ArrayList<MiscellaneousRecord>();
		endorserRecords = new ArrayList<EndorserRecord>();
		educationRecords = new ArrayList<EducationRecord>();
		curricula = new Curricula();

		curricula.setRanger(ranger);
		curricula.setEducationRecords(educationRecords);
		curricula.setEndorserRecords(endorserRecords);
		curricula.setMiscellaneousRecords(miscellaneousRecords);
		curricula.setProfessionalRecords(professionalRecords);

		curricula.setTicker(this.generatedTicker());

		return curricula;

	}
	public Curricula save(final Curricula curricula) {

		this.rangerService.checkPrincipal();
		Assert.notNull(curricula);
		Assert.notNull(curricula.getPersonalRecord());

		Curricula newCurricula;
		Curricula curriculaBeforeSave;
		Ranger ranger;

		ranger = this.rangerService.findByPrincipal();
		newCurricula = new Curricula();

		newCurricula.setId(curricula.getId());
		newCurricula.setEducationRecords(curricula.getEducationRecords());
		newCurricula.setEndorserRecords(curricula.getEndorserRecords());
		newCurricula.setMiscellaneousRecords(curricula.getMiscellaneousRecords());
		newCurricula.setPersonalRecord(curricula.getPersonalRecord());
		newCurricula.setProfessionalRecords(curricula.getProfessionalRecords());

		Assert.isTrue(curricula.getRanger() == ranger);
		if (curricula.getId() != 0)
			newCurricula.setTicker(this.curriculaRepository.findOne(curricula.getId()).getTicker());
		if (curricula.getId() == 0)
			newCurricula.setTicker(this.generatedTicker());

		newCurricula.setRanger(ranger);

		curriculaBeforeSave = this.curriculaRepository.save(newCurricula);
		Assert.notNull(curriculaBeforeSave);

		return curriculaBeforeSave;
	}

	public void delete(final Curricula curricula) {

		Assert.notNull(curricula);
		Assert.notNull(this.curriculaRepository.findOne(curricula.getId()));

		this.curriculaRepository.delete(curricula);

	}

	public Collection<Curricula> findAll() {
		Collection<Curricula> curriculas;

		curriculas = this.curriculaRepository.findAll();
		Assert.notNull(curriculas);

		return curriculas;
	}

	public Curricula findOne(final int curriculaId) {
		Assert.notNull(curriculaId);
		Assert.isTrue(curriculaId != 0);

		Curricula curricula;

		curricula = this.curriculaRepository.findOne(curriculaId);

		return curricula;
	}

	// Other methods Bussiness --------------------------------------------------------

	public Curricula findCurriculaFromRanger(final int rangerId) {

		Curricula curricula;

		curricula = this.curriculaRepository.findCurriculaFromRanger(rangerId);

		return curricula;
	}

	public String generatedTicker() {

		Calendar calendar;

		calendar = Calendar.getInstance();
		String ticker;
		String dias;
		String mes;
		ticker = "";

		//ticker = String.valueOf(calendar.get(Calendar.YEAR)).substring(2) + String.valueOf(calendar.get(Calendar.MONTH) + 1);
		dias = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		mes = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		if (dias.length() <= 1)
			ticker = ticker + "0" + String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		else
			ticker = ticker + String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

		if (mes.length() <= 1)
			ticker = ticker + "0" + String.valueOf(calendar.get(Calendar.MONTH) + 1);
		else
			ticker = ticker + String.valueOf(calendar.get(Calendar.MONTH) + 1);
		ticker = ticker + String.valueOf(calendar.get(Calendar.YEAR)).substring(2);
		final char[] arr = new char[] {
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
		};
		String cadenaAleatoria = "";
		for (Integer i = 0; i <= 3; i++) {
			final char elegido = arr[(int) (Math.random() * 26)];
			cadenaAleatoria = cadenaAleatoria + elegido;

		}

		ticker = ticker + "-" + cadenaAleatoria;

		return ticker;
	}

	public Curricula CurriculaWithThisPersonalRecord(final int personalRecordId) {

		Assert.isTrue(personalRecordId != 0);
		return this.curriculaRepository.CurriculaWithThisPersonalRecord(personalRecordId);
	}

	public Boolean curriculaContainsSpam(final Actor actor) {
		Boolean result;
		Curricula curriculaFromActor;
		Collection<String> spamWords;
		Collection<String> wordsPublicated;

		curriculaFromActor = this.curriculaRepository.findCurriculaFromRanger(actor.getId());
		result = false;
		spamWords = this.configurationSystemService.spamWord();
		wordsPublicated = new ArrayList<String>();

		wordsPublicated.add(curriculaFromActor.getPersonalRecord().getEmail().toLowerCase());
		wordsPublicated.add(curriculaFromActor.getPersonalRecord().getFullName().toLowerCase());

		for (final EducationRecord edcuation : curriculaFromActor.getEducationRecords()) {
			wordsPublicated.add(edcuation.getDiplomaTitle().toLowerCase());
			wordsPublicated.add(edcuation.getInstitution().toLowerCase());
			wordsPublicated.add(edcuation.getLink().toLowerCase());
			for (final String c : edcuation.getComments())
				wordsPublicated.add(c.toLowerCase());
		}
		for (final EndorserRecord endorser : curriculaFromActor.getEndorserRecords()) {
			wordsPublicated.add(endorser.getEmail().toLowerCase());
			wordsPublicated.add(endorser.getFullName().toLowerCase());
			wordsPublicated.add(endorser.getLinkedProfile().toLowerCase());
			for (final String c : endorser.getComments())
				wordsPublicated.add(c.toLowerCase());
		}
		for (final MiscellaneousRecord miscellaneous : curriculaFromActor.getMiscellaneousRecords()) {
			wordsPublicated.add(miscellaneous.getTitle().toLowerCase());
			wordsPublicated.add(miscellaneous.getLink().toLowerCase());
			for (final String c : miscellaneous.getComments())
				wordsPublicated.add(c.toLowerCase());
		}
		for (final ProfessionalRecord professional : curriculaFromActor.getProfessionalRecords()) {
			wordsPublicated.add(professional.getCompanyName().toLowerCase());
			wordsPublicated.add(professional.getLink().toLowerCase());
			wordsPublicated.add(professional.getRole().toLowerCase());
			for (final String c : professional.getComments())
				wordsPublicated.add(c.toLowerCase());
		}
		for (final String word : wordsPublicated)
			for (final String spam : spamWords)
				if (word.indexOf(spam) > -1) {
					result = true;
					break;
				}

		return result;

	}

	public Curricula CurriculaWithThisMiscellaneousRecord(final int miscellaneousRecordId) {
		Assert.isTrue(miscellaneousRecordId != 0);
		return this.curriculaRepository.CurriculaWithThisMiscellaneousRecord(miscellaneousRecordId);
	}

	public Curricula CurriculaWithThisEndorserRecord(final int endorserRecordId) {
		Assert.isTrue(endorserRecordId != 0);
		return this.curriculaRepository.CurriculaWithThisEndorserRecord(endorserRecordId);
	}

	public Curricula CurriculaWithThisProfessionalRecord(final int professionalRecordId) {
		Assert.isTrue(professionalRecordId != 0);
		return this.curriculaRepository.CurriculaWithThisProfessionalRecord(professionalRecordId);
	}

	public Curricula CurriculaWithThisEducationRecord(final int educationRecordId) {
		Assert.isTrue(educationRecordId != 0);
		return this.curriculaRepository.CurriculaWithThisEducationRecord(educationRecordId);
	}
}
