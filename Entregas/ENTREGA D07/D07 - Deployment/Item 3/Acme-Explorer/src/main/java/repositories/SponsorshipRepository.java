
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sponsorship;
import domain.Trip;

@Repository
public interface SponsorshipRepository extends JpaRepository<Sponsorship, Integer> {

	@Query("select s from Sponsorship s where s.sponsor.id=?1")
	Collection<Sponsorship> findBySponsorId(int sponsorId);

	@Query("select s.trip from Sponsorship s where s.id=?1")
	Trip findTripBySponsorship(int sponsorshipId);

	@Query("select s from Sponsorship s where s.trip.id=?1")
	List<Sponsorship> findAllSponsorshipByTripId(int tripId);

}
