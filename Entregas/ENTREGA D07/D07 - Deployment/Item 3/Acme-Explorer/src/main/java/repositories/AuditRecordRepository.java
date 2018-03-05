
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Attachment;
import domain.AuditRecord;
import domain.Trip;

@Repository
public interface AuditRecordRepository extends JpaRepository<AuditRecord, Integer> {

	@Query("select t from Trip t join t.auditRecords a where a.id=?1")
	Trip findTripsGivingAnAuditRecord(int auditRecodId);

	@Query("select a from AuditRecord a where a.auditor.id=?1")
	Collection<AuditRecord> findByAuditorId(int auditorId);

	@Query("select r.url from AuditRecord a join a.attachments r where a.id=?1")
	Collection<Attachment> urlOfAttachments(int auditRecordId);

	@Query("select t.auditRecords from Trip t where t.id=?1")
	Collection<AuditRecord> auditRecordsForTrip(int tripId);

	@Query("select t.url from AuditRecord a join a.attachments t where a.id=?1")
	Collection<Attachment> findAttachmentsByAuditRecord(int auditRecord);

}
