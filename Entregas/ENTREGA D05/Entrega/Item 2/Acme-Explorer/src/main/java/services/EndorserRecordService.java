
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EndorserRecordRepository;
import domain.EndorserRecord;

@Service
@Transactional
public class EndorserRecordService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private EndorserRecordRepository	endorserRecordRepository;


	// Supporting services ----------------------------------------------------

	// Constructors-------------------------------------------------------

	public EndorserRecordService() {
		super();
	}

	// Simple CRUD methods------------------------------------------------

	public EndorserRecord create() {
		EndorserRecord endorserRecord;
		List<String> comments;

		endorserRecord = new EndorserRecord();
		comments = new ArrayList<String>();

		endorserRecord.setComments(comments);

		return endorserRecord;
	}
	public Collection<EndorserRecord> findAll() {
		Collection<EndorserRecord> endorserRecords;

		endorserRecords = this.endorserRecordRepository.findAll();
		Assert.notNull(endorserRecords);

		return endorserRecords;
	}

	public EndorserRecord findOne(int endorserRecordId) {
		Assert.isTrue(endorserRecordId != 0);
		Assert.notNull(endorserRecordId);

		EndorserRecord endorserRecord;

		endorserRecord = this.endorserRecordRepository.findOne(endorserRecordId);

		return endorserRecord;
	}

	public EndorserRecord save(EndorserRecord endorserRecord) {
		Assert.notNull(endorserRecord);

		EndorserRecord newResult;

		newResult = this.endorserRecordRepository.save(endorserRecord);

		return newResult;

	}

	public void delete(EndorserRecord endorserRecord) {
		Assert.notNull(endorserRecord);
		Assert.notNull(this.endorserRecordRepository.findOne(endorserRecord.getId()));

		this.endorserRecordRepository.delete(endorserRecord);

		Assert.isNull(this.endorserRecordRepository.findOne(endorserRecord.getId()));
	}

}
