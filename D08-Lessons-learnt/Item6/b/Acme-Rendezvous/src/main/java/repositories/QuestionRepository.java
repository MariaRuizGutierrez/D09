
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

	@Query("select q from Question q where q.user.id=?1")
	Collection<Question> findAllQuestionsByUser(int userId);

	@Query("select q from Question q where q.rendezvouse.id=?1")
	Collection<Question> findAllQuestionsByRendezvous(int rendezvouseId);

}
