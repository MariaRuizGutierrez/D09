
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

	@Query("select s from ServiceOffered s where s.category.name=?1")
	Collection<ServiceOffered> ServiceByCategoryName(int categoryId);

	//Servicios disponibles que no tenga una cita
	@Query("select s from ServiceOffered s where ?1 not member of s.rendezvouses and s.cancelled=false and s.category!=null")
	Collection<ServiceOffered> AllServiceNotCancelledAveibleForRendezvouse(int id);

}
