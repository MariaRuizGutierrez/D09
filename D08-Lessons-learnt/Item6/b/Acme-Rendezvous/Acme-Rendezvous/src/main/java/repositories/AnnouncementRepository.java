
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Announcement;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {

	@Query("select a from Announcement a where a.rendezvouse.id=?1 order by madeMoment desc")
	Collection<Announcement> findAnnouncementByRendezvousId(int rendezvouseId);

	@Query("select r.announcements from Rendezvouse r join r.assistants a where a.id=?1 order by madeMoment desc")
	Collection<Announcement> findAnnouncementByUserIdForRendezvousesAssits(int userId);

	@Query("select b from User u join u.rendezvousesCreated a join a.announcements b where u.id=?1")
	Collection<Announcement> findAnnouncementByUserId(int userId);

}
