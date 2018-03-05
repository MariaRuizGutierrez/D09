
package domain;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class Category extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String	name;


	@NotBlank
	@NotNull
	@Column(unique = true)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
