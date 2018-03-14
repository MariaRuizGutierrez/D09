
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AnnouncementRepository;
import domain.Announcement;
import domain.Rendezvouse;
import domain.User;

@Service
@Transactional
public class AnnouncementService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private AnnouncementRepository	announcementRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private UserService				userService;


	// Constructors------------------------------------------------------------
	public AnnouncementService() {
		super();
	}

	// Simple CRUD methods-----------------------------------------------------
	public Announcement create(final Rendezvouse rendezvouse) {
		Date madeMoment;
		Announcement announcement;

		madeMoment = new Date(System.currentTimeMillis() - 1000);
		announcement = new Announcement();
		announcement.setMadeMoment(madeMoment);
		announcement.setRendezvouse(rendezvouse);

		return announcement;
	}

	public Announcement save(final Announcement announcement) {
		Assert.notNull(announcement);
		Announcement result;
		User user;
		Collection<Rendezvouse> rendezvousesOfUserConnected;

		user = this.userService.findByPrincipal();

		//Un usuario solo podrá editar sus anuncios.
		if (announcement.getId() != 0) {
			Collection<Announcement> announcementsOfUser;
			announcementsOfUser = this.announcementRepository.findAnnouncementByUserId(user.getId());
			Assert.isTrue(announcementsOfUser.contains(announcement));
		}

		//Un usuario solo podrá crear anuncios para sus rendezvouses
		rendezvousesOfUserConnected = new ArrayList<>(user.getRendezvousesCreated());
		Assert.isTrue(rendezvousesOfUserConnected.contains(announcement.getRendezvouse()));

		result = this.announcementRepository.save(announcement);
		return result;
	}

	public void delete(final Announcement announcement) {
		Assert.notNull(announcement);
		Assert.isTrue(announcement.getId() != 0);
		Rendezvouse rendezvouse;
		rendezvouse = announcement.getRendezvouse();
		rendezvouse.getAnnouncements().remove(announcement);
		//Sólo un admin podrá borrar un announcement
		this.administratorService.checkPrincipal();
		this.announcementRepository.delete(announcement);
	}

	public Collection<Announcement> findAll() {
		Collection<Announcement> result;
		result = new ArrayList<Announcement>(this.announcementRepository.findAll());
		Assert.notNull(result);
		return result;
	}

	public Announcement findOne(final int announcementId) {
		Announcement result;
		Assert.isTrue(announcementId != 0);
		result = this.announcementRepository.findOne(announcementId);
		return result;
	}

	//Other services-----------------------------------------------------------
	public Collection<Announcement> findAnnouncementByRendezvousId(int rendezvouseId) {
		Collection<Announcement> result;
		result = new ArrayList<>(this.announcementRepository.findAnnouncementByRendezvousId(rendezvouseId));
		Assert.notNull(result);
		return result;
	}

	public Collection<Announcement> findAnnouncementByUserIdForRendezvousesAssits() {
		Collection<Announcement> result;
		User user;
		user = this.userService.findByPrincipal();
		result = new ArrayList<>(this.announcementRepository.findAnnouncementByUserIdForRendezvousesAssits(user.getId()));
		Assert.notNull(result);
		return result;
	}

	public Collection<Announcement> findAnnouncementByUserId() {
		Collection<Announcement> result;
		User user;
		user = this.userService.findByPrincipal();
		result = new ArrayList<>(this.announcementRepository.findAnnouncementByUserId(user.getId()));
		Assert.notNull(result);
		return result;
	}

}
