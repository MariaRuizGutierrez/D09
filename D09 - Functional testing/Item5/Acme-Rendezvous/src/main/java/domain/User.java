
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class User extends Actor {

	// Attributes -------------------------------------------------------------
	private Date	birthDate;


	@NotNull
	@Past
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(final Date birthDate) {
		this.birthDate = birthDate;
	}


	// Relationships ----------------------------------------------------------

	private Collection<Rendezvouse>	rendezvousesCreated;
	private Collection<Rendezvouse>	rendezvousesAssisted;


	@Valid
	@NotNull
	@OneToMany
	public Collection<Rendezvouse> getRendezvousesCreated() {
		return this.rendezvousesCreated;
	}

	public void setRendezvousesCreated(final Collection<Rendezvouse> rendezvousesCreated) {
		this.rendezvousesCreated = rendezvousesCreated;
	}

	@NotNull
	@Valid
	@ManyToMany(mappedBy = "assistants")
	public Collection<Rendezvouse> getRendezvousesAssisted() {
		return this.rendezvousesAssisted;
	}

	public void setRendezvousesAssisted(final Collection<Rendezvouse> rendezvousesAssisted) {
		this.rendezvousesAssisted = rendezvousesAssisted;
	}

}
