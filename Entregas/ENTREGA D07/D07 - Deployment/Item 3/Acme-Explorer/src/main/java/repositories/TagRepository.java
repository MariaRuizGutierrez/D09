
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

	@Query("select t from Tag t where t.id=?1")
	Tag findTagById(int tagId);

	@Query("select t from Tag t join t.values v where v.trip.id=?1")
	Collection<Tag> findAllTagByTripId(int tripId);
}
