
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EndorserRecordRepository;
import domain.Curricula;
import domain.EndorserRecord;
import domain.Ranger;

@Service
@Transactional
public class EndorserRecordService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private EndorserRecordRepository	endorserRecordRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private RangerService				rangerService;

	@Autowired
	private CurriculaService			curriculaService;

	@Autowired
	private ConfigurationSystemService	configurationSystemService;


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
		endorserRecord.setPhone(this.configurationSystemService.findOne().getDefaultPhone());

		return endorserRecord;
	}
	public Collection<EndorserRecord> findAll() {
		Collection<EndorserRecord> endorserRecords;

		endorserRecords = this.endorserRecordRepository.findAll();
		Assert.notNull(endorserRecords);

		return endorserRecords;
	}

	public EndorserRecord findOne(final int endorserRecordId) {
		Assert.isTrue(endorserRecordId != 0);
		Assert.notNull(endorserRecordId);

		EndorserRecord endorserRecord;

		endorserRecord = this.endorserRecordRepository.findOne(endorserRecordId);

		return endorserRecord;
	}

	public EndorserRecord save(final EndorserRecord endorserRecord) {
		Assert.notNull(endorserRecord);

		EndorserRecord result;
		Ranger rangerPrincipal;
		final Curricula curriculaPrincipal;

		result = this.endorserRecordRepository.save(endorserRecord);
		rangerPrincipal = this.rangerService.findByPrincipal();
		curriculaPrincipal = this.curriculaService.findCurriculaFromRanger(rangerPrincipal.getId());
		if (!curriculaPrincipal.getEndorserRecords().contains(result))
			curriculaPrincipal.getEndorserRecords().add(result);

		Assert.notNull(result);

		return result;
	}

	public void delete(final EndorserRecord endorserRecord) {
		Assert.notNull(endorserRecord);
		Assert.isTrue(endorserRecord.getId() != 0);

		Ranger rangerPrincipal;
		final Curricula curriculaPrincipal;

		rangerPrincipal = this.rangerService.findByPrincipal();
		curriculaPrincipal = this.curriculaService.findCurriculaFromRanger(rangerPrincipal.getId());
		curriculaPrincipal.getEndorserRecords().remove(endorserRecord);
		this.endorserRecordRepository.delete(endorserRecord);

		Assert.isNull(this.endorserRecordRepository.findOne(endorserRecord.getId()));
	}

}
