
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MiscellaneousRecordRepository;
import domain.Curricula;
import domain.MiscellaneousRecord;
import domain.Ranger;

@Service
@Transactional
public class MiscellaneousRecordService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private MiscellaneousRecordRepository	miscellaneousRecordRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private RangerService					rangerService;

	@Autowired
	private CurriculaService				curriculaService;


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

	public MiscellaneousRecord findOne(final int miscellaneousRecordId) {

		Assert.notNull(miscellaneousRecordId);
		Assert.isTrue(miscellaneousRecordId != 0);

		MiscellaneousRecord result;

		result = this.miscellaneousRecordRepository.findOne(miscellaneousRecordId);

		return result;
	}
	public MiscellaneousRecord save(final MiscellaneousRecord miscellaneousRecord) {

		Assert.notNull(miscellaneousRecord);

		MiscellaneousRecord result;
		Ranger rangerPrincipal;
		final Curricula curriculaPrincipal;

		result = this.miscellaneousRecordRepository.save(miscellaneousRecord);
		rangerPrincipal = this.rangerService.findByPrincipal();
		curriculaPrincipal = this.curriculaService.findCurriculaFromRanger(rangerPrincipal.getId());
		if (!curriculaPrincipal.getMiscellaneousRecords().contains(result))
			curriculaPrincipal.getMiscellaneousRecords().add(result);

		Assert.notNull(result);

		return result;
	}
	public void delete(final MiscellaneousRecord miscellaneousRecord) {

		Assert.notNull(miscellaneousRecord);
		Assert.isTrue(miscellaneousRecord.getId() != 0);

		Ranger rangerPrincipal;
		final Curricula curriculaPrincipal;

		rangerPrincipal = this.rangerService.findByPrincipal();
		curriculaPrincipal = this.curriculaService.findCurriculaFromRanger(rangerPrincipal.getId());
		curriculaPrincipal.getMiscellaneousRecords().remove(miscellaneousRecord);
		this.miscellaneousRecordRepository.delete(miscellaneousRecord);

		Assert.isNull(this.miscellaneousRecordRepository.findOne(miscellaneousRecord.getId()));

	}

	public Collection<MiscellaneousRecord> saveAll(final Collection<MiscellaneousRecord> miscellaneousRecords) {
		Assert.notNull(miscellaneousRecords);
		final List<MiscellaneousRecord> newMiscellaneousRecords = new ArrayList<MiscellaneousRecord>();
		newMiscellaneousRecords.addAll(this.miscellaneousRecordRepository.save(miscellaneousRecords));

		return newMiscellaneousRecords;
	}
}
