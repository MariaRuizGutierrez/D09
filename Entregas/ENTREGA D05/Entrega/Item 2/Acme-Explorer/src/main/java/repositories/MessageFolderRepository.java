
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.MessageFolder;

@Repository
public interface MessageFolderRepository extends JpaRepository<MessageFolder, Integer> {

	@Query("select m from Actor a join  a.messagesFolders m where a.id=$1")
	Collection<MessageFolder> ActorFolders(int id);

}
