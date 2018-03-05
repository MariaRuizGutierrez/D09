
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PersonalRecordRepository;
import domain.Curricula;
import domain.PersonalRecord;

@Service
@Transactional
public class PersonalRecordService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private PersonalRecordRepository	personalRecordRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private CurriculaService			curriculaService;


	// Constructors-------------------------------------------------------

	public PersonalRecordService() {
		super();
	}

	// Simple CRUD methods------------------------------------------------

	public PersonalRecord create() {
		PersonalRecord personalRecord;

		personalRecord = new PersonalRecord();

		return personalRecord;
	}

	public Collection<PersonalRecord> findAll() {
		Collection<PersonalRecord> personalRecords;

		personalRecords = this.personalRecordRepository.findAll();
		Assert.notNull(personalRecords);

		return personalRecords;
	}

	public PersonalRecord findOne(int personalRecordId) {
		Assert.isTrue(personalRecordId != 0);

		PersonalRecord personalRecord;

		personalRecord = this.personalRecordRepository.findOne(personalRecordId);

		return personalRecord;
	}

	public PersonalRecord save(PersonalRecord personalRecord) {
		Assert.notNull(personalRecord);

		PersonalRecord result;

		result = this.personalRecordRepository.saveAndFlush(personalRecord);
		Assert.notNull(result);

		return result;

	}

	public void delete(PersonalRecord personalRecord) {

		Assert.notNull(personalRecord);
		Assert.isTrue(personalRecord.getId() != 0);

		Curricula curriculaWithThisPersonalRecord;
		curriculaWithThisPersonalRecord = this.curriculaService.CurriculaWithThisPersonalRecord(personalRecord.getId());
		if (curriculaWithThisPersonalRecord != null && curriculaWithThisPersonalRecord.getId() != 0)
			this.curriculaService.delete(curriculaWithThisPersonalRecord);

		this.personalRecordRepository.delete(personalRecord);

	}
}
