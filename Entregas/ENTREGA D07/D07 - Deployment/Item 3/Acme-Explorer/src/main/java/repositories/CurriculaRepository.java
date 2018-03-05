
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Curricula;
import domain.Ranger;

@Repository
public interface CurriculaRepository extends JpaRepository<Curricula, Integer> {

	//Devuelve la curricula del ranger si es que tuviera
	@Query("select c from Curricula c where c.ranger.id=?1")
	Curricula findCurriculaFromRanger(int rangerId);

	//Devuelve el ranger de la curricula
	@Query("select c.ranger from Curricula c where c.id=?1")
	Ranger rangerWithThisCurricula(int curriculaID);

	//Devuelve la curricula de ese personalRecord
	@Query("select c from Curricula c where c.personalRecord.id=?1")
	Curricula CurriculaWithThisPersonalRecord(int personalRecordId);

	@Query("select c from Curricula c join c.miscellaneousRecords m where m.id=?1")
	Curricula CurriculaWithThisMiscellaneousRecord(int miscellaneousRecordId);

	@Query("select c from Curricula c join c.endorserRecords e where e.id=?1")
	Curricula CurriculaWithThisEndorserRecord(int endorserRecordId);

	@Query("select c from Curricula c join c.professionalRecords p where p.id=?1")
	Curricula CurriculaWithThisProfessionalRecord(int professionalRecordId);

	@Query("select c from Curricula c join c.educationRecords e where e.id=?1")
	Curricula CurriculaWithThisEducationRecord(int educationRecordId);
}
