
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
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
	private AuditorRepository			auditorRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private MessageFolderService		messageFolderService;

	@Autowired
	private ActorService				actorService;

	@Autowired
	private AuditRecordService			auditRecordService;

	@Autowired
	private NoteService					noteService;

	@Autowired
	private ConfigurationSystemService	configurationSystemService;


	// Constructors-------------------------------------------------------

	public AuditorService() {
		super();
	}

	// Simple CRUD methods------------------------------------------------

	public Auditor create() {

		Auditor result;
		UserAccount userAccount;
		Authority authority;
		Collection<SocialIdentity> socialIdentities;
		Collection<MessageFolder> messagesFolders;
		Collection<Note> notes;
		Collection<AuditRecord> auditrecords;

		result = new Auditor();
		userAccount = new UserAccount();
		authority = new Authority();
		messagesFolders = new ArrayList<MessageFolder>();
		socialIdentities = new ArrayList<SocialIdentity>();
		notes = new ArrayList<Note>();
		auditrecords = new ArrayList<AuditRecord>();

		messagesFolders = this.messageFolderService.createDefaultFoldersForRegister(result);

		authority.setAuthority(Authority.AUDITOR);
		userAccount.setActivated(true);
		userAccount.addAuthority(authority);
		result.setUserAccount(userAccount);
		result.setMessagesFolders(messagesFolders);
		result.setSocialIdentities(socialIdentities);
		result.setNotes(notes);
		result.setAuditRecords(auditrecords);
		result.setPhone(this.configurationSystemService.findOne().getDefaultPhone());
		result.setSuspicious(false);

		return result;
	}

	public Collection<Auditor> findAll() {
		Collection<Auditor> result;
		result = this.auditorRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Auditor findOne(final int auditorId) {
		Assert.isTrue(auditorId != 0);
		Auditor result;
		result = this.auditorRepository.findOne(auditorId);
		return result;
	}

	public Auditor save(final Auditor auditor) {
		Assert.notNull(auditor);
		final Auditor result;
		final Md5PasswordEncoder encoder;
		final String passwordHash;

		encoder = new Md5PasswordEncoder();
		passwordHash = encoder.encodePassword(auditor.getUserAccount().getPassword(), null);
		auditor.getUserAccount().setPassword(passwordHash);
		result = this.auditorRepository.save(auditor);

		Assert.notNull(result);

		return result;
	}

	public void delete(final Auditor auditor) {
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

		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		final Collection<Authority> authorities = userAccount.getAuthorities();
		Assert.notNull(authorities);

		final Authority auth = new Authority();
		auth.setAuthority("AUDITOR");

		Assert.isTrue(authorities.contains(auth));
	}

	public Boolean auditorIsSpam(final Auditor auditor) {
		Boolean result;
		result = false;

		result = this.actorService.actorIsSpam(auditor);
		if (result == true) {
			auditor.setSuspicious(result);
			return result;
		}
		result = this.auditRecordService.auditRecordContainsSpam(auditor);
		if (result == true) {
			auditor.setSuspicious(result);
			return result;
		}

		result = this.noteService.noteContainsSpam(auditor);
		if (result == true) {
			auditor.setSuspicious(result);
			return result;
		}

		return result;
	}
}
