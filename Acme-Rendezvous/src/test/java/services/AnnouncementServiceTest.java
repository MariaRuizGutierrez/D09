
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
	public void driver() {
		final Object testingData[][] = {
			{
				"user1", "rendezvouse1", null
			}, {
				"user5", "rendezvouse1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}
	private void template(final String username, final int announcementId, final Class<?> expected) {
		final Rendezvouse rendezvouseForAnnouncement;
		Announcement announcement;
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			rendezvouseForAnnouncement = this.rendezvouseService.findOne(announcementId);
			announcement = this.announcementService.create(rendezvouseForAnnouncement);
			announcement.setTitle("title test");
			announcement.setDescription("description test");
			announcement = this.announcementService.save(announcement);
			this.unauthenticate();
			this.announcementService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}
}
