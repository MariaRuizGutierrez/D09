
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String	name;
	private String	concat;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}


	//-----------------------------Relationships-----------------------------

	private Collection<Category>	subCategories;
	private Category				fatherCategory;
	private Collection<Trip>		trips;


	@NotNull
	@Valid
	@OneToMany(mappedBy = "fatherCategory")
	public Collection<Category> getSubCategories() {
		return this.subCategories;
	}

	public void setSubCategories(final Collection<Category> subCategories) {
		this.subCategories = subCategories;
	}

	@Valid
	@ManyToOne(optional = true)
	public Category getFatherCategory() {
		return this.fatherCategory;
	}

	public void setFatherCategory(final Category fatherCategory) {
		this.fatherCategory = fatherCategory;
	}

	@ManyToMany(cascade = CascadeType.REMOVE)
	@Valid
	public Collection<Trip> getTrips() {
		return this.trips;
	}

	public void setTrips(Collection<Trip> trips) {
		this.trips = trips;
	}

	//Derivated

	@Transient
	public String getConcat() {
		if (this.getFatherCategory() != null)
			this.concat = this.getFatherCategory().getName() + "-" + this.getName();
		else
			this.concat = this.getName();
		return this.concat;
	}

	public void setConcat(String concat) {
		this.concat = concat;
	}

}
