
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Attachment;
import domain.AuditRecord;
import domain.Auditor;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class AuditRecordServiceTest extends AbstractTest {

	// Service under test ---------------------------------
	@Autowired
	private AuditRecordService	auditRecordService;

	@Autowired
	private AuditorService		auditorService;


	// Tests ----------------------------------------------

	@Test
	public void testCreate() {
		this.authenticate("auditor4");

		AuditRecord result;
		result = this.auditRecordService.create();
		Assert.notNull(result);

		super.unauthenticate();
	}

	@Test
	public void testFindAll() {
		Collection<AuditRecord> result;

		result = this.auditRecordService.findAll();
		Assert.notEmpty(result);
	}

	@Test
	public void testSave() {
		this.authenticate("auditor4");

		AuditRecord auditRecord;
		Auditor auditor;

		Collection<Attachment> attachments;

		auditRecord = this.auditRecordService.create();

		attachments = new ArrayList<Attachment>();

		auditRecord.setTitle("titletest");
		auditRecord.setDescription("descriptiontest");
		auditRecord.setDraftMode(true);
		auditRecord.setAttachments(attachments);

		auditRecord = this.auditRecordService.save(auditRecord);
		auditor = this.auditorService.findByPrincipal();
		Assert.notNull(auditor);
		//Assert.isTrue(trip1.getAuditRecords().contains(auditRecord));
		//Assert.isTrue(auditor.getAuditRecords().contains(auditRecord));

		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDeleleNegative() {
		Auditor auditor;
		AuditRecord auditRecord;

		auditRecord = this.auditRecordService.create();
		auditor = this.auditorService.findAll().iterator().next();

		auditRecord.setAuditor(auditor);
		auditRecord.setTitle("title2");
		auditRecord.setDescription("description2");
		auditRecord.setDraftMode(false);

		auditRecord = this.auditRecordService.save(auditRecord);
		this.auditRecordService.delete(auditRecord);

	}

	@Test
	public void testDelelePositive() {
		// Porque sé que la auditrecord que le estoy pasando tiene el modo borrador a true
		AuditRecord auditRecord;
		Trip trip;
		auditRecord = this.auditRecordService.findOne(super.getEntityId("auditrecord3"));
		//TENGO QUE BORRAR EL AUDITRECORD 3 DE LA TRIP SUYA
		trip = this.auditRecordService.findTripsGivingAnAuditRecord(super.getEntityId("auditrecord3"));
		trip.getAuditRecords().remove(auditRecord);
		this.auditRecordService.delete(auditRecord);

	}

	@Test
	public void testfindOne() {
		AuditRecord auditRecord;
		auditRecord = this.auditRecordService.findOne(super.getEntityId("auditrecord2"));
		Assert.notNull(auditRecord);
	}
	@Test
	public void testcheckToModified() {
		this.authenticate("auditor4");

		AuditRecord auditRecord;
		auditRecord = this.auditRecordService.findOne(super.getEntityId("auditrecord1"));
		this.auditRecordService.checkToModified(auditRecord);
		auditRecord.setTitle("gola");

	}

}
