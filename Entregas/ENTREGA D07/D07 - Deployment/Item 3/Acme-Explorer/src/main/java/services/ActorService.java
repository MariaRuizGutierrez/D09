
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import domain.Auditor;
import domain.Explorer;
import domain.Manager;
import domain.Ranger;
import domain.SocialIdentity;
import domain.Sponsor;

@Service
@Transactional
public class ActorService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ActorRepository				actorRepository;

	@Autowired
	private AdministratorService		administratorService;

	@Autowired
	private AuditorService				auditorService;

	@Autowired
	private ExplorerService				explorerService;

	@Autowired
	private ManagerService				managerService;

	@Autowired
	private RangerService				rangerService;

	@Autowired
	private SponsorService				sponsorService;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ConfigurationSystemService	configurationSystemService;


	// Constructors -----------------------------------------------------------
	public ActorService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------
	public Collection<Actor> findAll() {
		Collection<Actor> result;

		result = this.actorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Actor findOne(final int actorId) {
		Assert.isTrue(actorId != 0);

		Actor result;

		result = this.actorRepository.findOne(actorId);
		Assert.notNull(result);

		return result;
	}

	public Actor save(final Actor actor) {
		Assert.notNull(actor);

		Actor result;

		result = this.actorRepository.save(actor);

		return result;
	}

	public void delete(final Actor actor) {
		Assert.notNull(actor);
		Assert.isTrue(actor.getId() != 0);
		Assert.isTrue(this.actorRepository.exists(actor.getId()));
		this.actorRepository.delete(actor);
	}
	// Other business methods -------------------------------------------------

	public Actor findPrincipal() {
		Actor result;
		int userAccountId;
		userAccountId = LoginService.getPrincipal().getId();
		result = this.actorRepository.findActorByUseraccount(userAccountId);
		Assert.notNull(result);

		return result;
	}
	public boolean isAuthenticated() {
		boolean result = false;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		if (userAccount != null)
			result = true;
		return result;
	}
	public Actor findActorByUseraccount(int id) {
		Actor res;
		res = this.findActorByUseraccount(id);
		return res;

	}

	public Boolean isSpam(Collection<String> text) {

		Boolean res = false;
		Collection<String> spamWords;
		spamWords = this.configurationSystemService.spamWord();

		for (String spam : spamWords)
			if (text.contains(spam)) {
				res = true;
				break;
			}

		return res;
	}

	public Boolean actorIsSpam(Actor actor) {
		Boolean result;
		Collection<String> words;
		Collection<String> spamWords;

		spamWords = this.configurationSystemService.spamWord();
		words = new ArrayList<String>();
		result = false;

		words.add(actor.getAddress().toLowerCase());
		words.add(actor.getEmail().toLowerCase());
		words.add(actor.getName().toLowerCase());
		words.add(actor.getSurname().toLowerCase());

		for (SocialIdentity social : actor.getSocialIdentities()) {
			words.add(social.getLink().toLowerCase());
			words.add(social.getName().toLowerCase());
			words.add(social.getNick().toLowerCase());
		}

		for (String word : words)
			for (String spam : spamWords)
				if (word.indexOf(spam) > -1) {
					result = true;
					break;
				}
		return result;
	}

	public Collection<Actor> suspicious() {

		Collection<Explorer> explorers;
		Collection<Auditor> auditors;
		Collection<Manager> managers;
		Collection<Sponsor> sponsors;
		Collection<Administrator> administrators;
		Collection<Ranger> rangers;
		boolean result;
		Collection<Actor> actors;

		actors = new ArrayList<Actor>();
		administrators = this.administratorService.findAll();
		auditors = this.auditorService.findAll();
		explorers = this.explorerService.findAll();
		managers = this.managerService.findAll();
		rangers = this.rangerService.findAll();
		sponsors = this.sponsorService.findAll();

		for (Administrator a : administrators) {
			result = this.administratorService.administratorIsSpam(a);
			if (result == true)
				actors.add(a);
		}

		for (Auditor au : auditors) {
			result = this.auditorService.auditorIsSpam(au);
			if (result == true)
				actors.add(au);
		}

		for (Explorer e : explorers) {
			result = this.explorerService.explorerIsSpam(e);
			if (result == true)
				actors.add(e);
		}

		for (Manager m : managers) {
			result = this.managerService.managerIsSpam(m);
			if (result == true)
				actors.add(m);
		}

		for (Ranger r : rangers) {
			result = this.rangerService.rangerIsSpam(r);
			if (result == true)
				actors.add(r);
		}

		for (Sponsor s : sponsors) {
			result = this.sponsorService.sponsorIsSpam(s);
			if (result == true)
				actors.add(s);
		}

		return actors;

	}
	public boolean ban(UserAccount userAcount) {

		boolean result;

		userAcount.setActivated(false);

		result = userAcount.isActivated();

		return result;

	}

	public boolean unban(UserAccount userAccount) {

		boolean result;

		userAccount.setActivated(true);

		result = userAccount.isActivated();

		return result;

	}
}
