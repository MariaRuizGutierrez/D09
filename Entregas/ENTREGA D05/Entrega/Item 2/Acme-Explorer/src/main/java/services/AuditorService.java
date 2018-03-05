
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AuditorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.AuditRecord;
import domain.Auditor;
import domain.MessageFolder;
import domain.Note;
import domain.SocialIdentity;

@Service
@Transactional
public class AuditorService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private AuditorRepository		auditorRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private MessageFolderService	messageFolderService;


	// Constructors-------------------------------------------------------

	public AuditorService() {
		super();
	}

	// Simple CRUD methods------------------------------------------------

	public Auditor create() {

		Auditor result;
		UserAccount useraccount;
		Authority authority;
		Collection<SocialIdentity> socialIdentities;
		Collection<MessageFolder> messagesFolders;
		Collection<Note> notes;
		Collection<AuditRecord> auditrecords;

		result = new Auditor();
		useraccount = new UserAccount();
		authority = new Authority();
		messagesFolders = new ArrayList<MessageFolder>();
		socialIdentities = new ArrayList<SocialIdentity>();
		notes = new ArrayList<Note>();
		auditrecords = new ArrayList<AuditRecord>();

		messagesFolders = this.messageFolderService.createDefaultFolders();

		authority.setAuthority(Authority.AUDITOR);
		useraccount.addAuthority(authority);
		result.setUserAccount(useraccount);
		result.setMessagesFolders(messagesFolders);
		result.setSocialIdentities(socialIdentities);
		result.setNotes(notes);
		result.setAuditRecords(auditrecords);

		return result;
	}

	public Collection<Auditor> findAll() {
		Collection<Auditor> result;
		result = this.auditorRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Auditor findOne(int auditorId) {
		Assert.isTrue(auditorId != 0);
		Auditor result;
		result = this.auditorRepository.findOne(auditorId);
		return result;
	}

	public Auditor save(Auditor auditor) {
		Assert.notNull(auditor);
		Auditor result;
		result = this.auditorRepository.save(auditor);
		Assert.notNull(result);
		return result;
	}

	public void delete(Auditor auditor) {
		Assert.notNull(auditor);
		Assert.isTrue(auditor.getId() != 0);
		this.auditorRepository.delete(auditor);
	}

	// Other business methods------------------------------------------------------

	public Auditor findByPrincipal() {
		Auditor result;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		result = this.auditorRepository.findByUserAccountId(userAccount.getId());
		return result;
	}

	public void checkPrincipal() {

		UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		Collection<Authority> authorities = userAccount.getAuthorities();
		Assert.notNull(authorities);

		Authority auth = new Authority();
		auth.setAuthority("AUDITOR");

		Assert.isTrue(authorities.contains(auth));
	}
}
