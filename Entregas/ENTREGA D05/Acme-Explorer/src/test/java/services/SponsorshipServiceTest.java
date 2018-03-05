
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
import domain.CreditCard;
import domain.Sponsor;
import domain.Sponsorship;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SponsorshipServiceTest extends AbstractTest {

	// Service under test ---------------------------------
	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private TripService			tripService;

	@Autowired
	private SponsorService		sponsorService;


	// Tests ----------------------------------------------

	@Test
	public void testCreate() {
		this.authenticate("sponsor2");

		Sponsorship res;
		res = this.sponsorshipService.create();
		Assert.notNull(res);

		super.unauthenticate();
	}

	@Test
	public void testFindAll() {
		Collection<Sponsorship> res;

		res = this.sponsorshipService.findAll();
		Assert.notEmpty(res);
	}

	@Test
	public void testSave() {
		this.authenticate("sponsor2");

		Sponsorship sponsorship;
		CreditCard creditcard;
		Sponsor sponsor;
		Trip trip1;

		sponsorship = this.sponsorshipService.create();
		creditcard = new CreditCard();
		trip1 = this.tripService.findOne(super.getEntityId("trip1"));

		creditcard.setBrandName("brandNametest");
		creditcard.setHolderName("holderNametest");
		creditcard.setNumber("4388576018410707");
		creditcard.setExpirationMonth("02");
		creditcard.setExpirationYear("19");
		creditcard.setCvv(655);

		sponsorship.setLink("http://www.link-banner.com");
		sponsorship.setBannerURL("http://www.banner.com");
		sponsorship.setCreditCard(creditcard);
		sponsorship.setTrip(trip1);

		sponsorship = this.sponsorshipService.save(sponsorship);

		sponsor = this.sponsorService.findByPrincipal();
		Assert.notNull(sponsor);
		//Assert.isTrue(sponsor.getSponsorships().contains(sponsorship));
		//Assert.isTrue(trip1.getSponsorships().contains(sponsorship));

		super.unauthenticate();
	}

	@Test
	public void testDelete() {
		Sponsorship sponsorship;
		sponsorship = this.sponsorshipService.findOne(super.getEntityId("sponsorship1"));
		this.sponsorshipService.delete(sponsorship);

	}
	@Test
	public void testFindOne() {
		Sponsorship sponsorship;
		sponsorship = this.sponsorshipService.findOne(super.getEntityId("sponsorship2"));
		Assert.notNull(sponsorship);
	}
}
