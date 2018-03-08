
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.CreditCard;
import domain.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

	@Query("select r.creditCard from Request r where r.user.id=?1 order by r.requestMoment DESC")
	Collection<CreditCard> findAllCreditCardsInDescendOrderByUser(int userId);

}
