
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "organisedMoment,deleted,draftMode")
})
public class Rendezvouse extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private String	name;
	private String	description;
	private Date	organisedMoment;
	private String	picture;
	private GPS		gps;
	private boolean	draftMode;
	private boolean	deleted;
	private boolean	forAdult;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public Date getOrganisedMoment() {
		return this.organisedMoment;
	}

	public void setOrganisedMoment(final Date organisedMoment) {
		this.organisedMoment = organisedMoment;
	}

	@URL
	public String getPicture() {
		return this.picture;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}

	@Valid
	public GPS getGps() {
		return this.gps;
	}

	public void setGps(final GPS gps) {
		this.gps = gps;
	}

	public boolean isDraftMode() {
		return this.draftMode;
	}

	public void setDraftMode(final boolean draftMode) {
		this.draftMode = draftMode;
	}

	public boolean isDeleted() {
		return this.deleted;
	}

	public void setDeleted(final boolean deleted) {
		this.deleted = deleted;
	}

	public boolean isForAdult() {
		return this.forAdult;
	}

	public void setForAdult(final boolean forAdult) {
		this.forAdult = forAdult;
	}


	// Relationships ----------------------------------------------------------
	private Collection<User>			assistants;
	private Collection<Rendezvouse>		similarRendezvouses;
	private Collection<Announcement>	announcements;
	private Collection<Service>			services;


	@Valid
	@ManyToMany
	public Collection<User> getAssistants() {
		return this.assistants;
	}

	public void setAssistants(final Collection<User> assistants) {
		this.assistants = assistants;
	}

	@Valid
	@ManyToMany
	public Collection<Rendezvouse> getSimilarRendezvouses() {
		return this.similarRendezvouses;
	}

	public void setSimilarRendezvouses(final Collection<Rendezvouse> similarRendezvouses) {
		this.similarRendezvouses = similarRendezvouses;
	}

	@Valid
	@OneToMany(mappedBy = "rendezvouse", cascade = CascadeType.REMOVE)
	public Collection<Announcement> getAnnouncements() {
		return this.announcements;
	}

	public void setAnnouncements(final Collection<Announcement> announcements) {
		this.announcements = announcements;
	}

	@ManyToMany
	@Valid
	public Collection<Service> getServices() {
		return this.services;
	}

	public void setServices(final Collection<Service> services) {
		this.services = services;
	}

}
