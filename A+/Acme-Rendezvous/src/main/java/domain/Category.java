
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
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
	private String	description;
	private String	concat;


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


	// Relationships ----------------------------------------------------------
	private Category					fatherCategory;
	private Collection<Category>		subCategories;
	private Collection<ServiceOffered>	servicesOffered;


	@Valid
	@ManyToOne(optional = true)
	public Category getFatherCategory() {
		return this.fatherCategory;
	}

	public void setFatherCategory(final Category fatherCategory) {
		this.fatherCategory = fatherCategory;
	}

	@Valid
	@OneToMany(mappedBy = "fatherCategory")
	public Collection<Category> getSubCategories() {
		return this.subCategories;
	}

	public void setSubCategories(final Collection<Category> subCategories) {
		this.subCategories = subCategories;
	}

	@Valid
	@OneToMany(mappedBy = "category")
	@NotNull
	public Collection<ServiceOffered> getServicesOffered() {
		return this.servicesOffered;
	}

	public void setServicesOffered(final Collection<ServiceOffered> servicesOffered) {
		this.servicesOffered = servicesOffered;
	}

	//Derivated

	@Transient
	public String getConcat() {
		Category cat;
		Category cat2;
		Category cat3;
		Category cat4;
		if (this.getFatherCategory() != null) {
			cat = this.getFatherCategory();
			this.concat = this.getFatherCategory().getName() + " || " + this.getName();
			if (cat.getFatherCategory() != null) {
				cat2 = cat.getFatherCategory();
				this.concat = cat.getFatherCategory().getName() + " || " + this.getFatherCategory().getName() + " || " + this.getName();
				if (cat2.getFatherCategory() != null) {
					cat3 = cat2.getFatherCategory();
					this.concat = cat2.getFatherCategory().getName() + " || " + cat.getFatherCategory().getName() + " || " + this.getFatherCategory().getName() + " || " + this.getName();
					if (cat3.getFatherCategory() != null) {
						cat4 = cat3.getFatherCategory();
						this.concat = cat3.getFatherCategory().getName() + " || " + cat2.getFatherCategory().getName() + " || " + cat.getFatherCategory().getName() + " || " + this.getFatherCategory().getName() + " || " + this.getName();
						if (cat4.getFatherCategory() != null)
							this.concat = cat4.getFatherCategory().getName() + " || " + cat3.getFatherCategory().getName() + " || " + cat2.getFatherCategory().getName() + " || " + cat.getFatherCategory().getName() + " || "
								+ this.getFatherCategory().getName() + " || " + this.getName();
					}
				}
			}
		} else
			this.concat = this.getName();
		return this.concat;
	}

	public void setConcat(final String concat) {
		this.concat = concat;
	}

}
