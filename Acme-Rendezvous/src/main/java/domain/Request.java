
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Request extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private String		comment;
	private CreditCard	creditCard;
	private Date		requestMoment;


	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getRequestMoment() {
		return this.requestMoment;
	}

	public void setRequestMoment(Date requestMoment) {
		this.requestMoment = requestMoment;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	@Valid
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}


	//Relationships------------------------------------------------------------
	private ServiceOffered	serviceOffered;
	private User			user;


	@Valid
	@ManyToOne(optional = false)
	public ServiceOffered getServiceOffered() {
		return this.serviceOffered;
	}

	public void setServiceOffered(final ServiceOffered serviceOffered) {
		this.serviceOffered = serviceOffered;
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
