
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ContactEmergencyRepository;
import domain.ContactEmergency;
import domain.Explorer;

@Service
@Transactional
public class ContactEmergencyService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ContactEmergencyRepository	contactEmergencyRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ExplorerService				explorerService;


	// Constructors -----------------------------------------------------------

	public ContactEmergencyService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public ContactEmergency create() {
		ContactEmergency result;

		result = new ContactEmergency();

		return result;
	}

	public Collection<ContactEmergency> findAll() {
		Collection<ContactEmergency> result;

		Assert.notNull(this.contactEmergencyRepository);
		result = this.contactEmergencyRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public ContactEmergency findOne(final int contactEmergencyId) {
		ContactEmergency result;

		result = this.contactEmergencyRepository.findOne(contactEmergencyId);
		Assert.notNull(result);

		return result;
	}

	public ContactEmergency save(final ContactEmergency contactEmergency) {
		Assert.notNull(contactEmergency);

		ContactEmergency result;

		result = this.contactEmergencyRepository.save(contactEmergency);

		return result;
	}

	public void delete(final ContactEmergency contactEmergency) {
		final Collection<Explorer> explorersWithThisContact;
		Assert.notNull(contactEmergency);
		Assert.isTrue(contactEmergency.getId() != 0);
		Assert.isTrue(this.contactEmergencyRepository.exists(contactEmergency.getId()));
		//De esta manera falla:
		//Collection<ContactEmergency> contactsEmergency;
		//contactsEmergency = this.findContactsEmergencyByContactEmergencyId(contactEmergency.getId());
		//contactsEmergency.removeAll(contactsEmergency);
		explorersWithThisContact = this.explorerService.findExplorersByContactEmergencyId(contactEmergency.getId());
		for (final Explorer e : explorersWithThisContact)
			e.getContactsEmergency().remove(contactEmergency);
		this.contactEmergencyRepository.delete(contactEmergency);
	}
	// Other business methods -------------------------------------------------

}
