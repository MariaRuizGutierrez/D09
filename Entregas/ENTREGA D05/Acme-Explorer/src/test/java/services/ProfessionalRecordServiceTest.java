
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
import domain.ProfessionalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ProfessionalRecordServiceTest extends AbstractTest {

	//Service under test----------------------------------------------------------	

	@Autowired
	private ProfessionalRecordService	professionalRecordService;


	// Supporting services ----------------------------------------------------

	// Test --------------------------------------------------------------------

	@Test
	public void testCreate() {

		ProfessionalRecord professionalRecord = this.professionalRecordService.create();
		Assert.isTrue(professionalRecord != null);
		Assert.notNull(professionalRecord.getComments());

	}

	@Test
	public void testSave() {
		ProfessionalRecord professionalRecord;
		professionalRecord = this.professionalRecordService.create();

		professionalRecord.setCompanyName("company Name 10");
		professionalRecord.setWorkPeriod("2014/2016");
		professionalRecord.setRole("Investigation 10");
		professionalRecord.setLink("https://www.test.com");

		ProfessionalRecord newProfessionalRecord = this.professionalRecordService.save(professionalRecord);
		Assert.notNull(newProfessionalRecord);
	}

	@Test
	public void testFindOne() {

		ProfessionalRecord professionalRecord = this.professionalRecordService.findOne(super.getEntityId("professionalRecord1"));
		Assert.notNull(professionalRecord);

	}

	@Test
	public void testDelete() {

		ProfessionalRecord professionalRecord;
		professionalRecord = this.professionalRecordService.create();

		professionalRecord.setCompanyName("company Name 10");
		professionalRecord.setWorkPeriod("2014/2016");
		professionalRecord.setRole("Investigation 10");
		professionalRecord.setLink("https://www.test.com");

		ProfessionalRecord newProfessionalRecord = this.professionalRecordService.save(professionalRecord);
		this.professionalRecordService.delete(newProfessionalRecord);
	}

	@Test
	public void testFindAll() {
		Collection<ProfessionalRecord> professionalRecords;
		professionalRecords = this.professionalRecordService.findAll();
		Assert.notNull(professionalRecords);
	}
	@Test
	public void testEdit() {
		ProfessionalRecord professionalRecord;
		ProfessionalRecord professionalRecordModified;
		professionalRecord = this.professionalRecordService.findOne(super.getEntityId("professionalRecord1"));
		professionalRecord.setCompanyName("modified");
		professionalRecordModified = this.professionalRecordService.findOne(super.getEntityId("professionalRecord1"));
		Assert.isTrue(professionalRecordModified.getCompanyName() == "modified");

	}
}
