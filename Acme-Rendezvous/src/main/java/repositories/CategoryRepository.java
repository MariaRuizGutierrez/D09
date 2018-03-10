
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	//categorias de los servicios que tiene una rendezvous en concreto.
	@Query("select s.category from Rendezvouse r join r.servicesOffered s where r.id=?1")
	Collection<Category> findAllCategoiesByRendezvous(int rendezvousid);

}
