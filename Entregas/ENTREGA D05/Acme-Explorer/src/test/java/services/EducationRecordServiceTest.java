
package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.EducationRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EducationRecordServiceTest extends AbstractTest {

	//Service under test----------------------------------------------------------	

	@Autowired
	private EducationRecordService	educationRecordService;


	// Supporting services ----------------------------------------------------

	// Test ---------------------------------------------------------------------

	@Test
	public void testCreate() {
		EducationRecord educationRecord;
		educationRecord = this.educationRecordService.create();

		Assert.notNull(educationRecord);
	}

	@Test
	public void testSave() {

		EducationRecord educationRecord;
		educationRecord = this.educationRecordService.create();

		educationRecord.setDiplomaTitle("diplomaTitle 11");
		educationRecord.setInstitution("institution 11");
		educationRecord.setLink("https://www.testEducationRecord.com");
		educationRecord.setStudyingPeriod("2012/2016");

		EducationRecord newEducationRecord = this.educationRecordService.save(educationRecord);
		Assert.notNull(newEducationRecord);

	}

	@Test
	public void testDelete() {

		EducationRecord educationRecord;
		educationRecord = this.educationRecordService.create();

		educationRecord.setDiplomaTitle("diplomaTitle 11");
		educationRecord.setInstitution("institution 11");
		educationRecord.setLink("https://www.testEducationRecord.com");
		educationRecord.setStudyingPeriod("2012/2016");

		EducationRecord newEducationRecord = this.educationRecordService.save(educationRecord);
		Assert.notNull(newEducationRecord);

		this.educationRecordService.delete(newEducationRecord);

	}

	@Test
	public void testFindOne() {
		EducationRecord educationRecord;
		educationRecord = this.educationRecordService.findOne(super.getEntityId("educationRecord1"));
		Assert.notNull(educationRecord);
	}

	@Test
	public void testFindAll() {
		Collection<EducationRecord> educationRecords;
		educationRecords = this.educationRecordService.findAll();
		Assert.notNull(educationRecords);
	}
}
