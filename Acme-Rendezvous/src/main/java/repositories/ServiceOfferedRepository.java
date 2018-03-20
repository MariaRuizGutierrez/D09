
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

	//Todos los servicios creados por un manager
	@Query("select s from Manager m join m.servicesOffered s where m.id=?1")
	Collection<ServiceOffered> findAllServicesByManager(int managerId);

	//Los servicios que pertenecen a la categoria pasada por parametro
	@Query("select s from ServiceOffered s where s.category.id=?1")
	Collection<ServiceOffered> ServiceByCategoryId(int categoryId);

	//Servicios disponibles dado una rendezvous en concreto
	@Query("select s from ServiceOffered s join s.rendezvouses r where r.id=?1 and s.cancelled = false")
	Collection<ServiceOffered> findAllServicesAvailableByRendezvous(int rendezvousId);

}
