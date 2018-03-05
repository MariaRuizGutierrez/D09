
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AuditRecordRepository;
import domain.Attachment;
import domain.AuditRecord;
import domain.Auditor;
import domain.Trip;

@Service
@Transactional
public class AuditRecordService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private AuditRecordRepository	auditRecordRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private AuditorService			auditorService;


	// Constructors-------------------------------------------------------

	public AuditRecordService() {
		super();
	}

	// Simple CRUD methods------------------------------------------------

	public AuditRecord create() {
		Auditor auditorPrincipal;
		AuditRecord result;
		Collection<Attachment> attachments;

		attachments = new ArrayList<>();
		result = new AuditRecord();
		auditorPrincipal = this.auditorService.findByPrincipal();

		result.setDraftMode(true); // Una vez que se crea está en modo borrador
		Date realisedMoment;
		realisedMoment = new Date();
		result.setRealisedMoment(realisedMoment);
		result.setAttachments(attachments);
		result.setAuditor(auditorPrincipal);
		return result;
	}

	public Collection<AuditRecord> findAll() {
		Collection<AuditRecord> result;
		result = this.auditRecordRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public AuditRecord findOne(final int auditrecordId) {
		AuditRecord result;
		result = this.auditRecordRepository.findOne(auditrecordId);
		return result;
	}

	public AuditRecord save(AuditRecord auditrecord) {
		Assert.notNull(auditrecord);
		AuditRecord result;

		Date realisedMoment;
		realisedMoment = new Date(System.currentTimeMillis() - 1000);
		result = new AuditRecord();
		result.setRealisedMoment(realisedMoment);
		Assert.notNull(result);
		result = this.auditRecordRepository.save(auditrecord);
		Assert.notNull(result);
		return result;
	}
	public void delete(final AuditRecord auditrecord) {
		//se puede borrar o modificar si está en modo borrador
		Assert.notNull(auditrecord);
		Assert.isTrue(auditrecord.isDraftMode() == true);
		this.auditRecordRepository.delete(auditrecord);
	}

	// Other business methods------------------------------------------------------

	public void checkToModified(AuditRecord auditRecord) {
		this.auditorService.checkPrincipal();
		Assert.notNull(auditRecord);
		Assert.isTrue(auditRecord.isDraftMode() == true);
	}

	public Trip findTripsGivingAnAuditRecord(int auditRecodId) {
		Trip trip;
		trip = this.auditRecordRepository.findTripsGivingAnAuditRecord(auditRecodId);
		return trip;
	}

}
