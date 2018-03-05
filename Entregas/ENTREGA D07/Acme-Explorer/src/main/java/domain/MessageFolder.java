
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
public class MessageFolder extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private String	name;
	private boolean	modifiable;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public boolean isModifiable() {
		return this.modifiable;
	}

	public void setModifiable(final boolean modifiable) {
		this.modifiable = modifiable;
	}


	//Relationships-------------------------------------------------------------------------------------
	private Collection<Message>	messages;


	@NotNull
	@Valid
	@OneToMany(mappedBy = "messageFolder")
	public Collection<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(final Collection<Message> messages) {
		this.messages = messages;
	}

}
