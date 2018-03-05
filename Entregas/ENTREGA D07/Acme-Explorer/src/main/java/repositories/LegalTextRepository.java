
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.LegalText;

@Repository
public interface LegalTextRepository extends JpaRepository<LegalText, Integer> {

	@Query("select l from LegalText l join l.trips t where t.id=?1")
	Collection<LegalText> findAllLegalTextByTripId(int tripId);
}
