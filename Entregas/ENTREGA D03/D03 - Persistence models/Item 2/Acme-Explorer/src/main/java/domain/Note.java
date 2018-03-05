
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
import javax.validation.constraints.Past;

@Entity
@Access(AccessType.PROPERTY)
public class Note extends DomainEntity {

	//----------------------------Atibuttes----------------------------------------

	private Date	createdMoment;
	private int		remark;
	private String	reply;
	private Date	replyMoment;


	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatedMoment() {
		return this.createdMoment;
	}

	public void setCreatedMoment(final Date createdMoment) {
		this.createdMoment = createdMoment;
	}

	public int getRemark() {
		return this.remark;
	}

	public void setRemark(final int remark) {
		this.remark = remark;
	}

	public String getReply() {
		return this.reply;
	}

	public void setReply(final String reply) {
		this.reply = reply;
	}

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	public Date getReplyMoment() {
		return this.replyMoment;
	}

	public void setReplyMoment(final Date replyMoment) {
		this.replyMoment = replyMoment;
	}


	//---------------------------Relationships--------------------------------------------------

	private Trip	trip;
	private Auditor	auditor;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Trip getTrip() {
		return this.trip;
	}

	public void setTrip(final Trip trip) {
		this.trip = trip;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Auditor getAuditor() {
		return this.auditor;
	}

	public void setAuditor(final Auditor auditor) {
		this.auditor = auditor;
	}

}
