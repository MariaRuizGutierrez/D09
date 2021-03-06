
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RendezvouseRepository;
import domain.Announcement;
import domain.Comment;
import domain.Question;
import domain.Rendezvouse;
import domain.ServiceOffered;
import domain.User;

@Service
@Transactional
public class RendezvouseService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RendezvouseRepository	rendezvousRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private UserService				userService;

	@Autowired
	private AnnouncementService		announcementService;

	@Autowired
	private QuestionService			questionService;

	@Autowired
	private CommentService			commentService;

	//Importar la que pertenece a Spring
	@Autowired
	private Validator				validator;


	// Constructors -----------------------------------------------------------

	public RendezvouseService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Rendezvouse create() {
		Rendezvouse result;
		Collection<User> assistants;
		Collection<Rendezvouse> similarRendezvouses;
		Collection<Announcement> announcements;
		Collection<ServiceOffered> services;

		announcements = new ArrayList<Announcement>();
		similarRendezvouses = new ArrayList<Rendezvouse>();
		services = new ArrayList<ServiceOffered>();
		assistants = new ArrayList<User>();
		result = new Rendezvouse();

		result.setAssistants(assistants);
		result.setAnnouncements(announcements);
		result.setSimilarRendezvouses(similarRendezvouses);
		result.setDeleted(false);
		result.setServicesOffered(services);

		return result;
	}
	public Collection<Rendezvouse> findAll() {
		Collection<Rendezvouse> result;

		Assert.notNull(this.rendezvousRepository);
		result = this.rendezvousRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Rendezvouse findOne(final int rendezvouseId) {
		Rendezvouse result;

		result = this.rendezvousRepository.findOne(rendezvouseId);
		Assert.notNull(result);

		return result;
	}

	public Rendezvouse save(final Rendezvouse rendezvouse) {
		Assert.notNull(rendezvouse);
		Rendezvouse result;
		User user;
		Date now;

		now = new Date();
		user = this.userService.findByPrincipal();
		if (rendezvouse.getId() == 0)
			rendezvouse.getAssistants().add(user);

		Assert.isTrue(rendezvouse.getOrganisedMoment().after(now), "future");
		if (rendezvouse.isForAdult() == true)
			Assert.isTrue(this.calculateYearsOld(user.getBirthDate()) > 17, "user younger");

		result = this.rendezvousRepository.save(rendezvouse);
		if (rendezvouse.getId() == 0)
			user.getRendezvousesCreated().add(result);

		return result;
	}

	public void delete(final Rendezvouse rendezvouse) {
		User user;
		user = this.userService.findUserByRendezvousId(rendezvouse.getId());

		Collection<User> assistant;
		Collection<Question> questions;
		Collection<Comment> comments;
		Collection<Rendezvouse> similarrendezvous;
		Collection<Announcement> announcements;

		similarrendezvous = this.SimilarRendezvouseWhereIS(rendezvouse);
		announcements = this.AnnoucemntofRendezvouse(rendezvouse);
		questions = this.questionService.findAllQuestionsByRendezvous(rendezvouse.getId());
		comments = this.commentService.commentTofindAllCommentsByRendezvousId(rendezvouse.getId());

		assistant = this.rendezvousRepository.findAllAssistantsByRendezvous(rendezvouse.getId());
		Assert.isTrue(user.getRendezvousesCreated().contains(rendezvouse));

		user.getRendezvousesCreated().remove(rendezvouse);
		for (final Rendezvouse r : similarrendezvous)
			r.getSimilarRendezvouses().remove(rendezvouse);

		for (final Comment c : comments)
			this.commentService.delete(c);

		for (final Question q : questions)
			this.questionService.delete(q);

		for (final Announcement a : announcements)
			this.announcementService.delete(a);
		for (final User u : assistant)
			u.getRendezvousesAssisted().remove(rendezvouse);
		this.rendezvousRepository.delete(rendezvouse);

	}

	public Rendezvouse linkSimilar(final Rendezvouse rendezvouse) {
		Rendezvouse BD;
		Rendezvouse result;
		User user;

		user = this.userService.findByPrincipal();
		BD = this.rendezvousRepository.findOne(rendezvouse.getId());
		Assert.isTrue(rendezvouse.getDescription().equals(BD.getDescription()));
		Assert.isTrue(rendezvouse.getName().equals(BD.getName()));

		if (rendezvouse.getGps().getLongitude() != null)
			Assert.isTrue(rendezvouse.getGps().getLatitude().equals(BD.getGps().getLatitude()));

		if (rendezvouse.getGps().getLongitude() != null)
			Assert.isTrue(rendezvouse.getGps().getLongitude().equals(BD.getGps().getLongitude()));

		Assert.isTrue(rendezvouse.getPicture().equals(BD.getPicture()));
		Assert.isTrue(user.getRendezvousesCreated().contains(rendezvouse), "Cannot commit this operation, because it's illegal");

		BD.getSimilarRendezvouses().addAll(rendezvouse.getSimilarRendezvouses());
		result = this.rendezvousRepository.save(BD);

		return result;

	}

	public Rendezvouse unlinkSimilar(final Rendezvouse rendezvouse) {
		Rendezvouse BD;
		Rendezvouse result;
		User user;

		user = this.userService.findByPrincipal();
		BD = this.rendezvousRepository.findOne(rendezvouse.getId());
		Assert.isTrue(rendezvouse.getDescription().equals(BD.getDescription()));
		Assert.isTrue(rendezvouse.getName().equals(BD.getName()));

		if (rendezvouse.getGps().getLongitude() != null)
			Assert.isTrue(rendezvouse.getGps().getLatitude().equals(BD.getGps().getLatitude()));

		if (rendezvouse.getGps().getLongitude() != null)
			Assert.isTrue(rendezvouse.getGps().getLongitude().equals(BD.getGps().getLongitude()));

		Assert.isTrue(rendezvouse.getPicture().equals(BD.getPicture()));
		Assert.isTrue(user.getRendezvousesCreated().contains(rendezvouse), "Cannot commit this operation, because it's illegal");

		BD.getSimilarRendezvouses().removeAll(rendezvouse.getSimilarRendezvouses());
		result = this.rendezvousRepository.save(BD);

		return result;

	}

	public void flush() {
		this.rendezvousRepository.flush();

	}

	// Other business methods -------------------------------------------------

	public Collection<Rendezvouse> findRendezvousesCreatedByUser() {
		Collection<Rendezvouse> res;
		User user;
		user = this.userService.findByPrincipal();
		res = this.rendezvousRepository.findRendezvousesCreatedByUser(user.getId());

		return res;

	}

	public Collection<Rendezvouse> findRendezvousesAssitedByUser() {
		Collection<Rendezvouse> res;
		User user;
		user = this.userService.findByPrincipal();
		res = this.rendezvousRepository.findRendezvousesAssitedByUser(user.getId());
		return res;

	}

	public Collection<Rendezvouse> findRendezvousesAssitedByUser2(final int userId) {
		Collection<Rendezvouse> res;

		res = this.rendezvousRepository.findRendezvousesAssitedByUser2(userId);
		return res;

	}

	public Collection<User> findAllAssistantsByRendezvous(final int rendezvousId) {
		Collection<User> res;

		res = this.rendezvousRepository.findAllAssistantsByRendezvous(rendezvousId);
		return res;

	}

	public Rendezvouse deletevirtual(final Rendezvouse rendezvouse) {
		final User userPrincipal;
		Rendezvouse result;

		Assert.notNull(rendezvouse);
		//Solo se puede eliminar virtualmente las rendezvouses que el usuario logueado ha creado
		userPrincipal = this.userService.findByPrincipal();
		Assert.isTrue(userPrincipal.getRendezvousesCreated().contains(rendezvouse));
		final boolean aux = true;
		Assert.isTrue(rendezvouse.isDraftMode() == true);
		rendezvouse.setDeleted(aux);
		result = this.rendezvousRepository.save(rendezvouse);
		return result;
	}

	public Collection<Rendezvouse> SimilarRendezvouseWhereIS(final Rendezvouse rendezvouse) {
		Collection<Rendezvouse> result;
		result = this.rendezvousRepository.SimilarRendezvouseWhereIS(rendezvouse.getId());
		return result;
	}

	public Collection<Rendezvouse> findAllRendezvousesForQuestions() {
		Collection<Rendezvouse> result;
		User user;

		user = this.userService.findByPrincipal();
		result = this.rendezvousRepository.findAllRendezvousesForQuestions(user.getId());

		return result;
	}

	public Collection<Rendezvouse> findAllMinusAdult() {
		Collection<Rendezvouse> result;
		result = this.rendezvousRepository.findAllMinusAdult();
		return result;
	}

	public Collection<Rendezvouse> findAllMinusAdultAndFinalMode() {
		Collection<Rendezvouse> result;
		result = this.rendezvousRepository.findAllMinusAdultAndFinalMode();
		return result;
	}

	public Collection<Announcement> AnnoucemntofRendezvouse(final Rendezvouse rendezvouse) {
		Collection<Announcement> res;
		res = this.rendezvousRepository.AnnoucemntofRendezvouse(rendezvouse.getId());

		return res;

	}
	public Collection<Rendezvouse> CancelMyassistantToRendezvouse(final User user) {
		Collection<Rendezvouse> resul;
		resul = this.rendezvousRepository.CancelMyassistantToRendezvouse(user.getId());
		return resul;
	}

	public Collection<Rendezvouse> assistantToRendezvouse(final User user) {
		Collection<Rendezvouse> resul;
		resul = this.rendezvousRepository.assistantToRendezvouse(user.getId());
		return resul;
	}

	public Collection<Rendezvouse> assistantToRendezvouseNot18(final User user) {
		Collection<Rendezvouse> resul;
		resul = this.rendezvousRepository.assistantToRendezvouseNot18(user.getId());
		return resul;
	}

	public Collection<Rendezvouse> AllRendezvousesDeleted(final int userId) {
		Collection<Rendezvouse> res;
		User user;
		user = this.userService.findByPrincipal();
		res = this.rendezvousRepository.AllRendezvousesDeleted(user.getId());
		return res;

	}
	public void assist(final int rendezvousId) {
		User usuario;
		Rendezvouse rendezvous;
		rendezvous = this.rendezvousRepository.findOne(rendezvousId);
		usuario = this.userService.findByPrincipal();

		if (rendezvous.isForAdult() == true)
			Assert.isTrue(this.calculateYearsOld(usuario.getBirthDate()) > 17);

		if (!rendezvous.getAssistants().contains(usuario))
			rendezvous.getAssistants().add(usuario);
		this.rendezvousRepository.save(rendezvous);

	}

	public void unassist(final int rendezvousId) {
		User usuario;
		Rendezvouse rendezvous;
		rendezvous = this.rendezvousRepository.findOne(rendezvousId);
		usuario = this.userService.findByPrincipal();
		Assert.isTrue(rendezvous.getAssistants().contains(usuario));
		rendezvous.getAssistants().remove(usuario);
		this.rendezvousRepository.save(rendezvous);

	}

	public int calculateYearsOld(final Date birthDay) {
		Calendar today, fechan;
		today = Calendar.getInstance();
		fechan = Calendar.getInstance();
		fechan.setTime(birthDay);

		int diffYear = today.get(Calendar.YEAR) - fechan.get(Calendar.YEAR);
		final int diffMonth = today.get(Calendar.MONTH) - fechan.get(Calendar.MONTH);
		final int diffDay = today.get(Calendar.DAY_OF_MONTH) - fechan.get(Calendar.DAY_OF_MONTH);
		// Si est� en ese a�o pero todav�a no los ha cumplido
		if (diffMonth < 0 || (diffMonth == 0 && diffDay < 0))
			diffYear = diffYear - 1;
		return diffYear;

	}

	public Collection<Rendezvouse> ListOFSimilarRendezvous(final Rendezvouse rendezvous) {
		Collection<Rendezvouse> result;
		result = this.rendezvousRepository.ListOFSimilarRendezvous(rendezvous.getId());
		return result;

	}

	public Collection<ServiceOffered> findAllServicesByRendezvous(final int rendezvousId) {

		Collection<ServiceOffered> ren;

		ren = this.rendezvousRepository.findAllServicesByRendezvous(rendezvousId);

		return ren;
	}
	public Collection<Rendezvouse> findAllRendezvousesNotDeletedExceptRendezvousId(final int rendezvousId) {
		Collection<Rendezvouse> result;
		result = this.rendezvousRepository.findAllRendezvousesNotDeletedExceptRendezvousId(rendezvousId);
		return result;
	}

	public Collection<Rendezvouse> findAllRendezvousesNotDeletedForMinorExceptRendezvousId(final int rendezvousId) {
		Collection<Rendezvouse> result;

		result = this.rendezvousRepository.findAllRendezvousesNotDeletedForMinorExceptRendezvousId(rendezvousId);

		return result;
	}

	public Rendezvouse reconstruct(final Rendezvouse rendezvous, final BindingResult bindingResult) {
		Rendezvouse result;
		Rendezvouse rendezvousBD;
		if (rendezvous.getId() == 0) {
			Collection<User> assistants;
			Collection<Rendezvouse> similarRendezvouses;
			Collection<Announcement> announcements;
			Collection<ServiceOffered> serviceOffereds;

			announcements = new ArrayList<Announcement>();
			similarRendezvouses = new ArrayList<Rendezvouse>();
			assistants = new ArrayList<User>();
			serviceOffereds = new ArrayList<ServiceOffered>();

			rendezvous.setAssistants(assistants);
			rendezvous.setAnnouncements(announcements);
			rendezvous.setSimilarRendezvouses(similarRendezvouses);
			rendezvous.setServicesOffered(serviceOffereds);
			rendezvous.setDeleted(false);
			result = rendezvous;
		} else {
			rendezvousBD = this.rendezvousRepository.findOne(rendezvous.getId());
			rendezvous.setId(rendezvousBD.getId());
			rendezvous.setVersion(rendezvousBD.getVersion());
			rendezvous.setAssistants(rendezvousBD.getAssistants());
			rendezvous.setAnnouncements(rendezvousBD.getAnnouncements());
			rendezvous.setServicesOffered(rendezvousBD.getServicesOffered());
			if (rendezvous.getSimilarRendezvouses() == null)
				rendezvous.setSimilarRendezvouses(new ArrayList<Rendezvouse>());
			result = rendezvous;
		}
		this.validator.validate(result, bindingResult);
		return result;
	}

	public Rendezvouse reconstructLinkUnlinkSimilar(final Rendezvouse rendezvous, final BindingResult bindingResult) {
		Rendezvouse result;
		Rendezvouse rendezvousBD;

		rendezvousBD = this.rendezvousRepository.findOne(rendezvous.getId());
		rendezvous.setId(rendezvousBD.getId());
		rendezvous.setVersion(rendezvousBD.getVersion());
		rendezvous.setAssistants(rendezvousBD.getAssistants());
		rendezvous.setAnnouncements(rendezvousBD.getAnnouncements());
		rendezvous.setServicesOffered(rendezvousBD.getServicesOffered());
		if (rendezvous.getSimilarRendezvouses() == null)
			rendezvous.setSimilarRendezvouses(new ArrayList<Rendezvouse>());
		rendezvous.setAnnouncements(rendezvousBD.getAnnouncements());
		rendezvous.setName(rendezvousBD.getName());
		rendezvous.setDescription(rendezvousBD.getDescription());
		rendezvous.setPicture(rendezvousBD.getPicture());
		rendezvous.setOrganisedMoment(rendezvousBD.getOrganisedMoment());
		rendezvous.setDraftMode(rendezvousBD.isDraftMode());
		rendezvous.setForAdult(rendezvousBD.isForAdult());
		result = rendezvous;
		this.validator.validate(result, bindingResult);
		return result;
	}

	public Collection<Rendezvouse> findRendezvousByCategory(final int categoryId) {
		Collection<Rendezvouse> res;
		res = this.rendezvousRepository.findRendezvousByCategory(categoryId);
		return res;
	}

	public Collection<Rendezvouse> ListOfRendezvousAssistantUserId(final int userId) {

		Collection<Rendezvouse> result;

		result = this.rendezvousRepository.ListOfRendezvousAssistantUserId(userId);

		return result;

	}

	public Collection<Rendezvouse> findAllSimilarForNoAuthenticathed(final int rendezvousId) {
		Collection<Rendezvouse> similars;

		similars = this.rendezvousRepository.findAllSimilarForNoAuthenticathed(rendezvousId);

		return similars;
	}

	public Collection<Rendezvouse> FindRendezvousThatUserAssist(User user) {

		Collection<Rendezvouse> result;

		result = this.rendezvousRepository.FindRendezvousThatUserAssist(user.getId());

		return result;
	}

	public Collection<Rendezvouse> FindRendezvousThatUserAssistMinor(User user) {
		Collection<Rendezvouse> result;

		result = this.rendezvousRepository.FindRendezvousThatUserAssistMinor(user.getId());

		return result;
	}

}
