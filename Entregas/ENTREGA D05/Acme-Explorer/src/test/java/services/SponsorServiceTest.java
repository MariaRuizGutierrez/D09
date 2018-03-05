
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Sponsor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SponsorServiceTest extends AbstractTest {

	// Service under test ---------------------------------
	@Autowired
	private SponsorService	sponsorService;


	// Tests ----------------------------------------------

	@Test
	public void testCreate() {

		Sponsor sponsor;
		sponsor = this.sponsorService.create();
		Assert.notNull(sponsor);
	}

	@Test
	public void testFindAll() {

		Collection<Sponsor> sponsors;

		sponsors = this.sponsorService.findAll();

		Assert.notNull(sponsors);
		Assert.notEmpty(sponsors);
	}

	@Test
	public void testFindOne() {

		Collection<Sponsor> sponsors;
		int idSponsor;
		Sponsor sponsor;

		sponsors = this.sponsorService.findAll();
		idSponsor = sponsors.iterator().next().getId();
		sponsor = this.sponsorService.findOne(idSponsor);

		Assert.notNull(sponsor);

	}

	@Test
	public void testSave() {

		Sponsor sponsor;
		Sponsor sponsoSaved;

		sponsor = this.sponsorService.create();

		sponsor.setName("name 1");
		sponsor.setSurname("surname 1");
		sponsor.setEmail("sponsor1@gmail.com");

		sponsoSaved = this.sponsorService.save(sponsor);

		Assert.notNull(sponsoSaved);
	}

	@Test
	public void testDelete() {

		Collection<Sponsor> sponsors;
		int idSponsor;
		Sponsor sponsor;

		sponsors = this.sponsorService.findAll();
		idSponsor = sponsors.iterator().next().getId();
		sponsor = this.sponsorService.findOne(idSponsor);

		this.sponsorService.delete(sponsor);

		Assert.isNull(this.sponsorService.findOne(idSponsor));
	}
}
