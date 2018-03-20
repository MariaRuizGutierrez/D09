
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "rendezvouse_id,user_id")
})
public class Question extends DomainEntity {

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
	private Rendezvouse	rendezvouse;
	private User		user;


	@Valid
	@ManyToOne(optional = false)
	public Rendezvouse getRendezvouse() {
		return this.rendezvouse;
	}

	public void setRendezvouse(final Rendezvouse rendezvouse) {
		this.rendezvouse = rendezvouse;
	}

	@Valid
	@ManyToOne(optional = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

}
