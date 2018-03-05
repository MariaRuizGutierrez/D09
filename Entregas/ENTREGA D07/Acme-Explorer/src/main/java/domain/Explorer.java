
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Explorer extends Actor {

	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------
	private Collection<Story>				stories;
	private Collection<ApplicationFor>		applicationsFor;
	private Collection<ContactEmergency>	contactsEmergency;
	private Finder							finder;


	@NotNull
	@Valid
	@OneToMany(mappedBy = "explorer")
	public Collection<Story> getStories() {
		return this.stories;
	}

	public void setStories(final Collection<Story> stories) {
		this.stories = stories;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "explorer")
	public Collection<ApplicationFor> getApplicationsFor() {
		return this.applicationsFor;
	}

	public void setApplicationsFor(final Collection<ApplicationFor> applicationsFor) {
		this.applicationsFor = applicationsFor;
	}

	@NotNull
	@Valid
	@ManyToMany
	public Collection<ContactEmergency> getContactsEmergency() {
		return this.contactsEmergency;
	}

	public void setContactsEmergency(final Collection<ContactEmergency> contactsEmergency) {
		this.contactsEmergency = contactsEmergency;
	}

	@Valid
	@OneToOne(optional = true)
	public Finder getFinder() {
		return this.finder;
	}

	public void setFinder(final Finder finder) {
		this.finder = finder;
	}

}
