
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ActorServiceTest extends AbstractTest {

	// Service under test ---------------------------------
	@Autowired
	private ActorService	actorService;


	@Test
	public void TestFindAll() {
		Collection<Actor> actors;
		actors = this.actorService.findAll();
		Assert.notEmpty(actors);
		Assert.notNull(actors);
	}

	@Test
	public void testSaveActor() {
		List<Actor> actors;
		actors = new ArrayList<>(this.actorService.findAll());
		Actor actor;
		actor = actors.get(0);
		actor.setEmail("juju@gmail.com");
		final Actor actorSave = this.actorService.save(actor);
		Assert.isTrue(actor.getEmail().equals(actorSave.getEmail()));
	}

	@Test
	public void testFindByPrincipal() {
		Actor actor;
		this.authenticate("administrator1");
		actor = this.actorService.findPrincipal();
		Assert.notNull(actor);
		this.authenticate(null);
	}

}
