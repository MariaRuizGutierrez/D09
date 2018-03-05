
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EducationRecordRepository;
import domain.EducationRecord;

@Service
@Transactional
public class EducationRecordService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private EducationRecordRepository	educationRecordRepository;


	// Supporting services ----------------------------------------------------

	// Constructors-------------------------------------------------------

	public EducationRecordService() {
		super();
	}

	// Simple CRUD methods------------------------------------------------

	public EducationRecord create() {
		EducationRecord educationRecord;
		List<String> comments;

		educationRecord = new EducationRecord();
		comments = new ArrayList<String>();

		educationRecord.setComments(comments);

		return educationRecord;

	}
	public Collection<EducationRecord> findAll() {
		Collection<EducationRecord> educationRecords;

		educationRecords = this.educationRecordRepository.findAll();
		Assert.notNull(educationRecords);

		return educationRecords;
	}

	public EducationRecord findOne(int educationRecordId) {
		Assert.isTrue(educationRecordId != 0);
		Assert.notNull(educationRecordId);

		EducationRecord educationRecord;

		educationRecord = this.educationRecordRepository.findOne(educationRecordId);

		return educationRecord;
	}

	public EducationRecord save(EducationRecord educationRecord) {
		Assert.notNull(educationRecord);

		EducationRecord newEducationRecord;

		newEducationRecord = this.educationRecordRepository.save(educationRecord);

		Assert.isTrue(newEducationRecord.getId() != 0);

		return newEducationRecord;

	}

	public void delete(EducationRecord educationRecord) {
		Assert.notNull(educationRecord);
		Assert.notNull(this.educationRecordRepository.findOne(educationRecord.getId()));

		this.educationRecordRepository.delete(educationRecord);

		Assert.isNull(this.educationRecordRepository.findOne(educationRecord.getId()));
	}

}
