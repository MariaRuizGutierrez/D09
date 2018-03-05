
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class LegalText extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String	title;
	private String	body;
	private int		lawsNumber;
	private Date	moment;
	private boolean	draftMode;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getBody() {
		return this.body;
	}

	public void setBody(final String body) {

		this.body = body;
	}

	public int getLawsNumber() {
		return this.lawsNumber;
	}

	public void setLawsNumber(final int lawsNumber) {

		this.lawsNumber = lawsNumber;
	}
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {

		this.moment = moment;
	}

	public boolean isDraftMode() {
		return this.draftMode;
	}

	public void setDraftMode(final boolean draftMode) {
		this.draftMode = draftMode;
	}


	// Relationships ----------------------------------------------------------
	private Collection<Trip>	trips;


	@Valid
	@NotEmpty
	@OneToMany
	public Collection<Trip> getTrips() {
		return this.trips;
	}

	public void setTrips(Collection<Trip> trips) {
		this.trips = trips;
	}
}
