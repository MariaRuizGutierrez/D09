
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Manager extends Actor {

	// Attributes -------------------------------------------------------------
	private String	vat;


	@NotBlank
	public String getVat() {
		return this.vat;
	}

	public void setVat(final String vat) {
		this.vat = vat;
	}


	// Relationships ----------------------------------------------------------
	private Collection<Service>	services;


	@Valid
	@NotNull
	@OneToMany
	public Collection<Service> getServices() {
		return this.services;
	}

	public void setServices(final Collection<Service> services) {
		this.services = services;
	}

}
