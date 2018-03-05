
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
import domain.PersonalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class PersonalRecordServiceTest extends AbstractTest {

	//Service under test----------------------------------------------------------	

	@Autowired
	private PersonalRecordService	personalRecordService;


	// Supporting services ----------------------------------------------------

	@Test
	public void testCreate() {
		PersonalRecord personalRecord = this.personalRecordService.create();
		Assert.isTrue(personalRecord != null);

	}

	@Test
	public void testSave() {
		PersonalRecord personalRecord;
		personalRecord = this.personalRecordService.create();

		personalRecord.setFullName("personalRecord 8");
		personalRecord.setEmail("dany@gmail.com");
		personalRecord.setLinkedProfile("https://www.example.com");
		personalRecord.setPhoto("https://www.example.com");
		personalRecord.setPhone("+34(578)1239");

		PersonalRecord newPersonalRecord = this.personalRecordService.save(personalRecord);
		Assert.notNull(newPersonalRecord);
	}

	@Test
	public void testDelete() {

		PersonalRecord personalRecord;
		personalRecord = this.personalRecordService.create();

		personalRecord.setFullName("personalRecord 8");
		personalRecord.setEmail("dany@gmail.com");
		personalRecord.setLinkedProfile("https://www.example.com");
		personalRecord.setPhoto("https://www.example.com");
		personalRecord.setPhone("+34(578)1239");

		PersonalRecord newPersonalRecord = this.personalRecordService.save(personalRecord);

		this.personalRecordService.delete(newPersonalRecord);

	}
	@Test
	public void testDeletePersonalRecordWithCurricula() {
		PersonalRecord personalRecordToDelete;

		personalRecordToDelete = this.personalRecordService.findOne(super.getEntityId("personalRecord1"));
		this.personalRecordService.delete(personalRecordToDelete);
	}
	@Test
	public void testFindOne() {
		PersonalRecord personalRecord;
		personalRecord = this.personalRecordService.findOne(super.getEntityId("personalRecord1"));
		Assert.notNull(personalRecord);
	}

	@Test
	public void testFindAll() {
		Collection<PersonalRecord> personalRecords;
		personalRecords = this.personalRecordService.findAll();
		Assert.notNull(personalRecords);
	}
	@Test
	public void testEdit() {
		PersonalRecord personalRecord;
		PersonalRecord personalModified;
		personalRecord = this.personalRecordService.findOne(super.getEntityId("personalRecord1"));
		personalRecord.setFullName("modified");
		personalModified = this.personalRecordService.findOne(super.getEntityId("personalRecord1"));
		Assert.isTrue(personalModified.getFullName() == "modified");
	}

}
