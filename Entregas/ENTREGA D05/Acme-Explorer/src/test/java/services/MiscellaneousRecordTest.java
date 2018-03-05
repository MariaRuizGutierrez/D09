
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Curricula;
import domain.MiscellaneousRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class MiscellaneousRecordTest extends AbstractTest {

	//Service under test-------------------------------

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;

	@Autowired
	private CurriculaService			curriculaService;


	//Test

	@Test
	public void testCreate() {
		MiscellaneousRecord miscellaneousRecord;
		miscellaneousRecord = this.miscellaneousRecordService.create();
		Assert.notNull(miscellaneousRecord);
	}

	@Test
	public void testFindAll() {
		Collection<MiscellaneousRecord> miscellaneousRecords;
		miscellaneousRecords = this.miscellaneousRecordService.findAll();
		Assert.notEmpty(miscellaneousRecords);
	}

	@Test
	public void testFindOne() {
		MiscellaneousRecord miscellaneousRecord;
		Collection<MiscellaneousRecord> miscellaneousRecords;
		int idMiscellaneousRecord;

		miscellaneousRecords = this.miscellaneousRecordService.findAll();
		idMiscellaneousRecord = miscellaneousRecords.iterator().next().getId();

		miscellaneousRecord = this.miscellaneousRecordService.findOne(idMiscellaneousRecord);
		Assert.notNull(miscellaneousRecord);

	}

	@Test
	public void testSave() {

		MiscellaneousRecord miscellaneousRecord;
		MiscellaneousRecord miscellaneousRecordSaved;

		miscellaneousRecord = this.miscellaneousRecordService.create();

		miscellaneousRecord.setTitle("title 1");
		miscellaneousRecord.setLink("https://link1.com");

		miscellaneousRecordSaved = this.miscellaneousRecordService.save(miscellaneousRecord);

		Assert.notNull(miscellaneousRecordSaved);

	}

	@Test
	public void testSaveAll() {
		MiscellaneousRecord miscellaneousRecord1;
		MiscellaneousRecord miscellaneousRecord2;
		Collection<MiscellaneousRecord> miscellaneousRecordsSaved;
		Collection<MiscellaneousRecord> miscellaneousRecords = new ArrayList<>();

		miscellaneousRecord1 = this.miscellaneousRecordService.create();

		miscellaneousRecord1.setTitle("title 1");
		miscellaneousRecord1.setLink("https://link1.com");

		miscellaneousRecord2 = this.miscellaneousRecordService.create();

		miscellaneousRecord2.setTitle("title 2");
		miscellaneousRecord2.setLink("https://link2.com");

		miscellaneousRecords.add(miscellaneousRecord1);
		miscellaneousRecords.add(miscellaneousRecord2);

		miscellaneousRecordsSaved = this.miscellaneousRecordService.saveAll(miscellaneousRecords);

		Assert.notNull(miscellaneousRecordsSaved);
		Assert.notEmpty(miscellaneousRecordsSaved);
	}

	@Test
	public void testDelete() {

		Curricula curricula;
		MiscellaneousRecord miscellaneousRecord;

		curricula = this.curriculaService.findOne(super.getEntityId("curricula1"));
		miscellaneousRecord = curricula.getMiscellaneousRecords().iterator().next();

		Assert.notNull(miscellaneousRecord);

		curricula.getMiscellaneousRecords().remove(miscellaneousRecord);

		this.miscellaneousRecordService.delete(miscellaneousRecord);

		Assert.isNull(this.miscellaneousRecordService.findOne(miscellaneousRecord.getId()));

	}
}
