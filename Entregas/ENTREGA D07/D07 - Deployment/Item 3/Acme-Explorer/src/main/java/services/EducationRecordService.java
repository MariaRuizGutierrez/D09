
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EducationRecordRepository;
import domain.Curricula;
import domain.EducationRecord;
import domain.Ranger;

@Service
@Transactional
public class EducationRecordService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private EducationRecordRepository	educationRecordRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private RangerService				rangerService;

	@Autowired
	private CurriculaService			curriculaService;


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

	public EducationRecord findOne(final int educationRecordId) {
		Assert.isTrue(educationRecordId != 0);
		Assert.notNull(educationRecordId);

		EducationRecord educationRecord;

		educationRecord = this.educationRecordRepository.findOne(educationRecordId);

		return educationRecord;
	}

	public EducationRecord save(final EducationRecord educationRecord) {
		Assert.notNull(educationRecord);

		EducationRecord result;
		Ranger rangerPrincipal;
		final Curricula curriculaPrincipal;

		result = this.educationRecordRepository.save(educationRecord);
		rangerPrincipal = this.rangerService.findByPrincipal();
		curriculaPrincipal = this.curriculaService.findCurriculaFromRanger(rangerPrincipal.getId());
		if (!curriculaPrincipal.getEducationRecords().contains(result))
			curriculaPrincipal.getEducationRecords().add(result);

		Assert.notNull(result);

		return result;
	}

	public void delete(final EducationRecord educationRecord) {
		Assert.notNull(educationRecord);
		Assert.isTrue(educationRecord.getId() != 0);

		Ranger rangerPrincipal;
		final Curricula curriculaPrincipal;

		rangerPrincipal = this.rangerService.findByPrincipal();
		curriculaPrincipal = this.curriculaService.findCurriculaFromRanger(rangerPrincipal.getId());
		curriculaPrincipal.getEducationRecords().remove(educationRecord);
		this.educationRecordRepository.delete(educationRecord);

		Assert.isNull(this.educationRecordRepository.findOne(educationRecord.getId()));
	}

}
