
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.ContactEmergency;

public interface ContactEmergencyRepository extends JpaRepository<ContactEmergency, Integer> {

}
