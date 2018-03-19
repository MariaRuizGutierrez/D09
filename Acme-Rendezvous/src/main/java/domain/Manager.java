
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Access(AccessType.PROPERTY)
public class Manager extends Actor {

	// Attributes -------------------------------------------------------------
	private String	vat;


	@Pattern(regexp = "^[\\w-]+$")
	public String getVat() {
		return this.vat;
	}

	public void setVat(final String vat) {
		this.vat = vat;
	}


	// Relationships ----------------------------------------------------------
	private Collection<ServiceOffered>	servicesOffered;


	@Valid
	@NotNull
	@OneToMany
	public Collection<ServiceOffered> getServicesOffered() {
		return this.servicesOffered;
	}

	public void setServicesOffered(final Collection<ServiceOffered> servicesOffered) {
		this.servicesOffered = servicesOffered;
	}

}
