
package domain;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class MessageFolder extends DomainEntity {

	private String	name;
	private boolean	modifiable;

	@NotNull
	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isModificable() {
		return this.modifiable;
	}

	public void setModificable(boolean modifiable) {
		this.modifiable = modifiable;
	}

	

}
