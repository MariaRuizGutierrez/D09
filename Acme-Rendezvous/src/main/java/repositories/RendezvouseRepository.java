
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Announcement;
import domain.Rendezvouse;
import domain.User;

@Repository
public interface RendezvouseRepository extends JpaRepository<Rendezvouse, Integer> {

	// devuelve las rendevouz creadas por un usuario y que no esten eliminadas
	@Query("select r from User u join u.rendezvousesCreated r where u.id=?1 and r.deleted=false")
	Collection<Rendezvouse> findRendezvousesCreatedByUser(int userId);
	// devuelve las rendevouz a las que asiste un usuario y que no esten eliminadas
	@Query("select r from User u join u.rendezvousesCreated r where u.id=?1 and r.deleted=false")
	Collection<Rendezvouse> findRendezvousesAssitedByUser(int userId);

	// devuelve las rendevouz a las que asiste un usuario sin comprobar que sea el principal y que no esten eliminadas
	@Query("select r from User u join u.rendezvousesCreated r where u.id=?1 and r.deleted=false")
	Collection<Rendezvouse> findRendezvousesAssitedByUser2(int userId);

	//Te devuelve todos los asistentes dada una cita concreta 
	@Query("select r.assistants from Rendezvouse r where r.id=?1")
	Collection<User> findAllAssistantsByRendezvous(int rendezvousId);

	//te devuelve las rendezvous similares que contienen una rendezvous
	@Query("select r from Rendezvouse r where ?1 member of r.similarRendezvouses")
	Collection<Rendezvouse> SimilarRendezvouseWhereIS(int rendezvousId);

	@Query("select r.announcements from Rendezvouse r where r.id=?1")
	Collection<Announcement> AnnoucemntofRendezvouse(int rendezvouse);

	// rendezvouses a las que puede dejar de asistir un usuario
	@Query("select r from Rendezvouse r where ?1 member of assistants and r.deleted=false and r.organisedMoment>CURRENT_TIMESTAMP and r.draftMode=false")
	Collection<Rendezvouse> CancelMyassistantToRendezvouse(int usuarioId);

	//rendezvouses a las que puede asistir un usuario
	@Query("select r from Rendezvouse r where ?1 not member of assistants and r.deleted=false and r.organisedMoment>CURRENT_TIMESTAMP and r.draftMode=false and r.forAdult=false")
	Collection<Rendezvouse> assistantToRendezvouse(int usuarioId);

	//Todas las rendezvous que estan borradas
	@Query("select a from User u join u.rendezvousesCreated a where a.deleted=true and u.id=?1")
	Collection<Rendezvouse> AllRendezvousesDeleted(int userId);

	//lista las rendezvous menos la pasada por parametro
	@Query("select r from Rendezvouse r where r.id!=?1")
	Collection<Rendezvouse> ListOFSimilarRendezvous(int rendezvousId);

	@Query("select r from Rendezvouse r where r.forAdult=false")
	Collection<Rendezvouse> findAllMinusAdult();

	//lista las rendezvous no canceladas menos la pasada por parametro
	@Query("select r from Rendezvouse r where r.id!=?1 and r.deleted=false")
	Collection<Rendezvouse> findAllRendezvousesNotDeletedExceptRendezvousId(int rendezvousId);

}
