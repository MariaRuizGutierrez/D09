
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
//@Table(uniqueConstraints = {
//	@UniqueConstraint(columnNames = "name"), @UniqueConstraint(columnNames = "fatherCategory_id")
//})
public class Category extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String	name;


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

	@ManyToMany
	@Valid
	public Collection<Trip> getTrips() {
		return this.trips;
	}

	public void setTrips(Collection<Trip> trips) {
		this.trips = trips;
	}

}
