
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "rendezvouse_id")
})
public class Announcement extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private Date	madeMoment;
	private String	title;
	private String	description;


	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getMadeMoment() {
		return this.madeMoment;
	}

	public void setMadeMoment(final Date madeMoment) {
		this.madeMoment = madeMoment;
	}

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}


	//-----------------------------Relationships-----------------------------
	private Rendezvouse	rendezvouse;


	@Valid
	@ManyToOne(optional = false)
	public Rendezvouse getRendezvouse() {
		return this.rendezvouse;
	}

	public void setRendezvouse(final Rendezvouse rendezvouse) {
		this.rendezvouse = rendezvouse;
	}

}
