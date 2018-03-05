
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.ApplicationFor;

@Repository
public interface ApplicationForRepository extends JpaRepository<ApplicationFor, Integer> {

	@Query("select a from Manager m join m.applicationsFor a where m.id = ?1")
	Collection<ApplicationFor> findAllByManagerId(int managerId);

	@Query("select c from ApplicationFor c where c.explorer.id=?1")
	Collection<ApplicationFor> applicationsForOfActor(int explorerId);

	@Query("select a from ApplicationFor a where a.trip.id=?2 and a.explorer.id=?1")
	Collection<ApplicationFor> checkDuplicatedApply(int explorerId, int tripId);

}
