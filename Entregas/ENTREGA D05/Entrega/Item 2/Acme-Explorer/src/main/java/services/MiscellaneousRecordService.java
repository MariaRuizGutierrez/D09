
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MiscellaneousRecordRepository;
import domain.MiscellaneousRecord;

@Service
@Transactional
public class MiscellaneousRecordService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private MiscellaneousRecordRepository	miscellaneousRecordRepository;


	// Supporting services ----------------------------------------------------

	// Constructors-------------------------------------------------------

	public MiscellaneousRecordService() {
		super();
	}

	// Simple CRUD methods------------------------------------------------

	public MiscellaneousRecord create() {

		MiscellaneousRecord result;

		Collection<String> comments;

		comments = new ArrayList<>();

		result = new MiscellaneousRecord();

		result.setComments(comments);

		return result;
	}

	public Collection<MiscellaneousRecord> findAll() {

		Collection<MiscellaneousRecord> result;

		result = this.miscellaneousRecordRepository.findAll();

		Assert.notNull(result);

		return result;
	}

	public MiscellaneousRecord findOne(int miscellaneousRecordId) {

		Assert.notNull(miscellaneousRecordId);
		Assert.isTrue(miscellaneousRecordId != 0);

		MiscellaneousRecord result;

		result = this.miscellaneousRecordRepository.findOne(miscellaneousRecordId);

		return result;
	}
	public MiscellaneousRecord save(MiscellaneousRecord miscellaneousRecord) {

		Assert.notNull(miscellaneousRecord);

		MiscellaneousRecord result;

		result = this.miscellaneousRecordRepository.save(miscellaneousRecord);

		Assert.notNull(result);

		return result;
	}

	public void delete(MiscellaneousRecord miscellaneousRecord) {

		Assert.notNull(miscellaneousRecord);
		Assert.isTrue(miscellaneousRecord.getId() != 0);

		this.miscellaneousRecordRepository.delete(miscellaneousRecord);

		Assert.isNull(this.miscellaneousRecordRepository.findOne(miscellaneousRecord.getId()));

	}

	public Collection<MiscellaneousRecord> saveAll(Collection<MiscellaneousRecord> miscellaneousRecords) {
		Assert.notNull(miscellaneousRecords);
		List<MiscellaneousRecord> newMiscellaneousRecords = new ArrayList<MiscellaneousRecord>();
		newMiscellaneousRecords.addAll(this.miscellaneousRecordRepository.save(miscellaneousRecords));

		return newMiscellaneousRecords;
	}
}
