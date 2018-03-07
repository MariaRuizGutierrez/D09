
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.ServiceOffered;

@Repository
public interface ServiceOfferedRepository extends JpaRepository<ServiceOffered, Integer> {

	//Servicios disponibles
	@Query("select s from ServiceOffered s where s.cancelled=false")
	Collection<ServiceOffered> AllServiceNotCancelled();

}
