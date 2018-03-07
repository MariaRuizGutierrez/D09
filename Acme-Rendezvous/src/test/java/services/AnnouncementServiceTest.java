
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Announcement;
import domain.Rendezvouse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AnnouncementServiceTest extends AbstractTest {

	@Autowired
	AnnouncementService	announcementService;

	@Autowired
	UserService			userService;

	@Autowired
	RendezvouseService	rendezvouseService;


	@Test
	public void testCreateAndSave() {
		final Rendezvouse rendezvouseForAnnouncement;
		Announcement announcement;

		super.authenticate("user1");
		rendezvouseForAnnouncement = this.rendezvouseService.findOne(super.getEntityId("rendezvouse1"));
		announcement = this.announcementService.create(rendezvouseForAnnouncement);
		announcement.setTitle("title test");
		announcement.setDescription("description test");
		announcement = this.announcementService.save(announcement);
		this.announcementService.flush();

	}
}
