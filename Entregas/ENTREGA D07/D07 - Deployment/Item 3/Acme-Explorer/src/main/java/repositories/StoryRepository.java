
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Attachment;
import domain.Story;

@Repository
public interface StoryRepository extends JpaRepository<Story, Integer> {

	@Query("select a.url from Story s join s.attachments a where s.id=?1")
	Collection<Attachment> urlOfAttachments(int storyId);

	@Query("select c from Story c where c.explorer.id=?1")
	Collection<Story> storyOfExplore(int explorerId);

	@Query("select s from Story s where s.trip.id=?1")
	Collection<Story> findAllStoriesByTripId(int tripId);

}
