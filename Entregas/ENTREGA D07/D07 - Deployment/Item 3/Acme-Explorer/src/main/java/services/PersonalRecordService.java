
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PersonalRecordRepository;
import domain.PersonalRecord;

@Service
@Transactional
public class PersonalRecordService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private PersonalRecordRepository	personalRecordRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ConfigurationSystemService	configurationSystemService;


	// Constructors-------------------------------------------------------

	public PersonalRecordService() {
		super();
	}

	// Simple CRUD methods------------------------------------------------

	public PersonalRecord create() {
		PersonalRecord personalRecord;

		personalRecord = new PersonalRecord();
		personalRecord.setPhone(this.configurationSystemService.findOne().getDefaultPhone());

		return personalRecord;
	}

	public Collection<PersonalRecord> findAll() {
		Collection<PersonalRecord> personalRecords;

		personalRecords = this.personalRecordRepository.findAll();
		Assert.notNull(personalRecords);

		return personalRecords;
	}

	public PersonalRecord findOne(final int personalRecordId) {
		Assert.isTrue(personalRecordId != 0);

		PersonalRecord personalRecord;

		personalRecord = this.personalRecordRepository.findOne(personalRecordId);

		return personalRecord;
	}

	public PersonalRecord save(final PersonalRecord personalRecord) {
		Assert.notNull(personalRecord);

		PersonalRecord result;

		result = this.personalRecordRepository.save(personalRecord);

		Assert.notNull(result);

		return result;
	}

}
