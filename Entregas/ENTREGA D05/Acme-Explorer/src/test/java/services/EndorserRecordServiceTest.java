
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
import domain.EndorserRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EndorserRecordServiceTest extends AbstractTest {

	//Service under test----------------------------------------------------------	

	@Autowired
	private EndorserRecordService	endorserRecordService;


	// Supporting services ----------------------------------------------------

	// Test ------------------------------------------------------------------

	@Test
	public void testCreate() {
		EndorserRecord endorserRecord = this.endorserRecordService.create();

		Assert.isTrue(endorserRecord != null);

	}

	@Test
	public void testSave() {
		EndorserRecord endorserRecord = this.endorserRecordService.create();

		endorserRecord.setFullName("Endorser Record 11");
		endorserRecord.setEmail("test@gmail.com");
		endorserRecord.setLinkedProfile("https://endorsserRecord11.com");
		endorserRecord.setPhone("111111111");

		EndorserRecord newEndorserRecord = this.endorserRecordService.save(endorserRecord);
		Assert.notNull(newEndorserRecord);

	}
	@Test
	public void testDelete() {
		EndorserRecord endorserRecord = this.endorserRecordService.create();

		endorserRecord.setFullName("Endorser Record 11");
		endorserRecord.setEmail("test@gmail.com");
		endorserRecord.setLinkedProfile("https://endorsserRecord11.com");
		endorserRecord.setPhone("111111111");

		EndorserRecord newEndorserRecord = this.endorserRecordService.save(endorserRecord);
		Assert.notNull(newEndorserRecord);

		this.endorserRecordService.delete(newEndorserRecord);
	}

	@Test
	public void testFindOne() {
		EndorserRecord endorserRecord;
		endorserRecord = this.endorserRecordService.findOne(super.getEntityId("endorserRecord1"));
		Assert.notNull(endorserRecord);
	}

	@Test
	public void testFindAll() {
		Collection<EndorserRecord> endorserRecords;
		endorserRecords = this.endorserRecordService.findAll();
		Assert.notNull(endorserRecords);
	}
	@Test
	public void testEdit() {
		EndorserRecord endorserRecord;
		EndorserRecord endorserRecordModified;
		endorserRecord = this.endorserRecordService.findOne(super.getEntityId("endorserRecord1"));
		endorserRecord.setFullName("modified");
		endorserRecordModified = this.endorserRecordService.findOne(super.getEntityId("endorserRecord1"));
		Assert.isTrue(endorserRecordModified.getFullName() == "modified");
	}
}
