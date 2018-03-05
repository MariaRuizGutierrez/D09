
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Tag extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private String	name;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}


	// Relationships ----------------------------------------------------------
	private Collection<Value>	values;


	@Valid
	@OneToMany(mappedBy = "tag", cascade = CascadeType.REMOVE)
	public Collection<Value> getValues() {
		return this.values;
	}

	public void setValues(Collection<Value> values) {
		this.values = values;
	}

}
