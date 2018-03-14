
package repositories;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;
import domain.Rendezvouse;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	@Query("select a from Administrator a where a.userAccount.id = ?1")
	Administrator findByUserAccountId(int id);

	//C/1 The average and the standard deviation of rendezvouses created per user.
	@Query("select avg(u.rendezvousesCreated.size), stddev(u.rendezvousesCreated.size) from User u")
	Double[] findAvgStddevOfTheNumOfRendezvouseCreatedPerUser();

	//C/2 The ratio of users who have ever created a rendezvous versus the users who have never created any rendezvouses.
	@Query("select (select count(u) from User u where u.rendezvousesCreated.size!=0) *1.0/count(u) from User u where u.rendezvousesCreated.size=0)")
	Double findRatioUsersWithRendezvousesAndNotRendezvouses();

	//C/3 The average and the standard deviation of users per rendezvous.
	@Query("select avg(r.assistants.size), stddev(r.assistants.size) from Rendezvouse r")
	Double findAvgStddevOfTheNumOfAssistansPerRendezvouse();

	//C/4 The average and the standard deviation of rendezvouses that are RSVPd per user.
	@Query("select avg(u.rendezvousesAssisted.size), stddev(u.rendezvousesAssisted.size) from User u")
	Double[] findAvgStddevOfTheNumOfRendezvouseAssitedPerUser();

	//C/5 The top-10 rendezvouses in terms of users who have RSVPd them.
	@Query("select r from Rendezvouse r where r.assistants.size!=0 order by r.assistants.size desc")
	Page<Rendezvouse> findTop10RendezvouseWithRSVPd(Pageable pageable);

	//B/1 The average and the standard deviation of announcements per rendezvous. 
	@Query("select avg(r.announcements.size), stddev(r.announcements.size) from Rendezvouse r")
	Double[] findAvgStddevOfTheNumOfAnnouncementsPerRendezvous();

	//B/2 The rendezvouses that whose number of announcements is above 75% the average number of announcements per rendezvous.
	@Query("select r from Rendezvouse r where r.announcements.size>(select 0.75 * avg(r.announcements.size) from Rendezvouse r)")
	Collection<Rendezvouse> findRendezvousesWithMore75PerCent();

	//B/3 The rendezvouses that are linked to a number of rendezvouses that is great-er than the average plus 10%.
	@Query("select r from Rendezvouse r where r.similarRendezvouses.size > (select avg(r.similarRendezvouses.size)*1.1 from Rendezvouse r)")
	Collection<Rendezvouse> findRendezvousesWithAreLinked();

	//A/1 The average and the standard deviation of the number of questions per ren-dezvous.
	@Query("select (select count(q) from Question q where q.rendezvouse.id=r.id) from Rendezvouse r")
	Collection<Long> findAvgStddevOfTheNumOfQuestionsPerRendezvous();

	//A/2 The average and the standard deviation of the number of answers to the questions per rendezvous.
	@Query("select (select count (a) from Answer a where a.question.rendezvouse.id=r.id) from Rendezvouse r")
	Collection<Long> findAvgStddevOfTheNumOfAnswerToQuestionsPerRendezvous();

	//A/3 The average and the standard deviation of replies per comment.
	@Query("select avg(c.replys.size), stddev(c.replys.size) from Comment c")
	Double[] findAvgStddevOfTheNumOfRepliesPerComment();

}
