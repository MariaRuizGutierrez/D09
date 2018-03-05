
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Curricula;
import domain.EducationRecord;
import domain.EndorserRecord;
import domain.MiscellaneousRecord;
import domain.PersonalRecord;
import domain.ProfessionalRecord;
import domain.Ranger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CurriculaServiceTest extends AbstractTest {

	//Service under test----------------------------------------------------------	

	@Autowired
	private CurriculaService		curriculaService;

	// Supporting services ----------------------------------------------------

	@Autowired
	private PersonalRecordService	personalRecordService;

	@Autowired
	private RangerService			rangerService;


	// Test -----------------------------------------------------------------------------

	@Test
	public void testCreate() {
		this.authenticate("ranger1");
		Curricula curricula;
		curricula = this.curriculaService.create();
		Assert.notNull(curricula);
		this.authenticate(null);
	}
	@Test
	public void testSave() {
		this.authenticate("ranger5");
		Curricula curricula;
		curricula = this.curriculaService.create();

		PersonalRecord personalRecord = this.personalRecordService.create();
		personalRecord.setFullName("personalRecord 8");
		personalRecord.setEmail("dany@gmail.com");
		personalRecord.setLinkedProfile("https://www.example.com");
		personalRecord.setPhoto("https://www.example.com");
		personalRecord.setPhone("+34(578)1239");

		PersonalRecord newPersonalRecord = this.personalRecordService.save(personalRecord);
		curricula.setPersonalRecord(newPersonalRecord);

		curricula.setTicker("041117-FFFF");

		this.curriculaService.save(curricula);
		this.authenticate(null);

	}
	@Test
	public void testFindAll() {
		Collection<Curricula> curriculas;
		curriculas = this.curriculaService.findAll();
		Assert.notNull(curriculas);
	}

	@Test
	public void testFindOne() {
		Curricula curricula;

		curricula = this.curriculaService.findOne(super.getEntityId("curricula4"));
		Assert.notNull(curricula);
	}

	@Test
	public void testDelete() {
		Curricula curricula;
		curricula = this.curriculaService.findOne(super.getEntityId("curricula4"));

		this.curriculaService.delete(curricula);
	}
	@Test
	public void testUpdate() {
		this.authenticate("ranger1");
		Curricula curriculaModify;
		Curricula curriculaModificated;
		PersonalRecord personalRecordToSave;
		PersonalRecord personalRecord1;
		List<ProfessionalRecord> professionalRecords;
		List<EducationRecord> educationRecords;
		List<EndorserRecord> endorserRecords;
		List<MiscellaneousRecord> miscellaneousRecords;

		curriculaModificated = this.curriculaService.create();
		curriculaModify = this.curriculaService.create();
		personalRecord1 = this.personalRecordService.create();
		personalRecord1.setEmail("modify@hotmail.com");
		personalRecord1.setFullName("modify1");
		personalRecord1.setLinkedProfile("https://www.modify.com");
		personalRecord1.setPhone("+34(578)1294");
		personalRecord1.setPhoto("https://www.photo.com");
		personalRecordToSave = this.personalRecordService.save(personalRecord1);

		professionalRecords = new ArrayList<ProfessionalRecord>();
		educationRecords = new ArrayList<EducationRecord>();
		endorserRecords = new ArrayList<EndorserRecord>();
		miscellaneousRecords = new ArrayList<MiscellaneousRecord>();

		curriculaModify.setEducationRecords(educationRecords);
		curriculaModify.setEndorserRecords(endorserRecords);
		curriculaModify.setMiscellaneousRecords(miscellaneousRecords);
		curriculaModify.setId(super.getEntityId("curricula1"));
		curriculaModify.setPersonalRecord(personalRecordToSave);
		curriculaModify.setProfessionalRecords(professionalRecords);

		curriculaModificated = this.curriculaService.save(curriculaModify);
		Assert.notNull(curriculaModificated);
		this.authenticate(null);
	}

	@Test
	public void testFindCurriculaFromRanger() {
		Curricula curricula;
		Ranger ranger;
		ranger = this.rangerService.findOne(super.getEntityId("ranger2"));
		curricula = this.curriculaService.findCurriculaFromRanger(ranger.getId());
		Assert.notNull(curricula);
	}

	@Test
	public void testGeneratedTicker() {
		String ticker;
		ticker = this.curriculaService.generatedTicker();
		Assert.notNull(ticker);
	}

	@Test
	public void testCurriculaWithPersonalRecord() {
		Curricula curriculaWithPersonalRecord;
		PersonalRecord personalRecord;

		personalRecord = this.personalRecordService.findOne(super.getEntityId("personalRecord1"));
		curriculaWithPersonalRecord = this.curriculaService.CurriculaWithThisPersonalRecord(personalRecord.getId());
		Assert.notNull(curriculaWithPersonalRecord);
	}
}
