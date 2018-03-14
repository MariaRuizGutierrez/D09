
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	@Query("select c from Comment c join c.rendezvouse r where r.id=?1")
	Collection<Comment> findAllCommentsByRendezvousId(int rendezvousId);

	@Query("select c from Comment c where c.rendezvouse.id=?1")
	Collection<Comment> commentsOfThisRendezvouse(int rendezvouseId);

	@Query("select c from Comment c where c.rendezvouse.id=?1 and c.commentTo=null")
	Collection<Comment> commentsOfThisRendezvouseWithCommentNull(int rendezvouseId);
	// solo te devuelve los commentarios padres de una rendezvous
	@Query("select c from Comment c join c.rendezvouse r where r.id=?1 and c.commentTo=null")
	Collection<Comment> commentTofindAllCommentsByRendezvousId(int rendezvous);

}
