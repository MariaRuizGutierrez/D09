
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.SurvivalClass;
import domain.Trip;

@Repository
public interface SurvivalClassRepository extends JpaRepository<SurvivalClass, Integer> {

	@Query("select s from SurvivalClass s where s.trip.manager.id=s.manager.id and manager.id =?1")
	Collection<SurvivalClass> findSurvivalClassByManager(int managerId);

	@Query("select s.trip from SurvivalClass s where s.manager.id=s.trip.manager.id and s.manager.id=?1")
	Collection<Trip> findTripsByManager(int managerId);

	@Query("select s.trip from SurvivalClass s where s.id=?1 and s.trip.manager=s.manager")
	Trip findTripBySurvivalClass(int survivalClassId);

	@Query("select s from SurvivalClass s where (select e from Explorer e where e.id=?2) member of s.explorers and s.trip.id=?1 group by s")
	Collection<SurvivalClass> findAllByTripIdEnrol(int tripId, int explorerId);

	@Query("select s from SurvivalClass s where (select e from Explorer e where e.id=?2)not member of s.explorers and s.trip.id=?1 group by s")
	Collection<SurvivalClass> findAllByTripIdNotEnrol(int tripId, int explorerId);

	@Query("select s from SurvivalClass s where s.trip.id=?1")
	Collection<SurvivalClass> findAllSurvivalClassByTripId(int tripId);
}
