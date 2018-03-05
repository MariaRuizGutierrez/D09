
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private String	name;
	private String	description;


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
	private Category				parent;
	private Collection<Category>	childrens;


	@Valid
	@ManyToOne(optional = true)
	public Category getParent() {
		return this.parent;
	}

	public void setParent(final Category parent) {
		this.parent = parent;
	}

	@Valid
	@OneToMany(mappedBy = "parent")
	public Collection<Category> getChildrens() {
		return this.childrens;
	}

	public void setChildrens(final Collection<Category> childrens) {
		this.childrens = childrens;
	}
}
