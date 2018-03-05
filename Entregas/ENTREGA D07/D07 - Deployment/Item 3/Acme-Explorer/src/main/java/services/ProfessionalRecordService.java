
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProfessionalRecordRepository;
import domain.Curricula;
import domain.ProfessionalRecord;
import domain.Ranger;

@Service
@Transactional
public class ProfessionalRecordService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ProfessionalRecordRepository	professionalRecordRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private RangerService					rangerService;

	@Autowired
	private CurriculaService				curriculaService;


	// Constructors-------------------------------------------------------

	public ProfessionalRecordService() {
		super();
	}

	// Simple CRUD methods------------------------------------------------

	public ProfessionalRecord create() {
		ProfessionalRecord professionalRecord;
		List<String> comments;

		professionalRecord = new ProfessionalRecord();
		comments = new ArrayList<String>();

		professionalRecord.setComments(comments);

		return professionalRecord;
	}
	public Collection<ProfessionalRecord> findAll() {
		Collection<ProfessionalRecord> professionalRecords;

		professionalRecords = this.professionalRecordRepository.findAll();
		Assert.notNull(professionalRecords);

		return professionalRecords;
	}

	public ProfessionalRecord findOne(final int professionalRecordId) {
		Assert.isTrue(professionalRecordId != 0);

		ProfessionalRecord professionalRecord;
		professionalRecord = this.professionalRecordRepository.findOne(professionalRecordId);

		return professionalRecord;
	}

	public ProfessionalRecord save(final ProfessionalRecord professionalRecord) {
		Assert.notNull(professionalRecord);

		ProfessionalRecord result;
		Ranger rangerPrincipal;
		final Curricula curriculaPrincipal;

		result = this.professionalRecordRepository.save(professionalRecord);
		rangerPrincipal = this.rangerService.findByPrincipal();
		curriculaPrincipal = this.curriculaService.findCurriculaFromRanger(rangerPrincipal.getId());
		if (!curriculaPrincipal.getProfessionalRecords().contains(result))
			curriculaPrincipal.getProfessionalRecords().add(result);

		Assert.notNull(result);

		return result;
	}

	public void delete(final ProfessionalRecord professionalRecord) {

		Assert.notNull(professionalRecord);
		Assert.isTrue(professionalRecord.getId() != 0);

		Ranger rangerPrincipal;
		final Curricula curriculaPrincipal;

		rangerPrincipal = this.rangerService.findByPrincipal();
		curriculaPrincipal = this.curriculaService.findCurriculaFromRanger(rangerPrincipal.getId());
		curriculaPrincipal.getProfessionalRecords().remove(professionalRecord);
		this.professionalRecordRepository.delete(professionalRecord);

	}

}
