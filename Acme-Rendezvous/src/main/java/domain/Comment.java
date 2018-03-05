
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Comment extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private Date	writtenMoment;
	private String	text;
	private String	picture;


	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getWrittenMoment() {
		return this.writtenMoment;
	}

	public void setWrittenMoment(final Date writtenMoment) {
		this.writtenMoment = writtenMoment;
	}

	@NotBlank
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}
	@URL
	public String getPicture() {
		return this.picture;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}


	// Relationships ----------------------------------------------------------
	private Collection<Comment>	replys;
	private Comment				commentTo;
	private Rendezvouse			rendezvouse;
	private User				user;


	@Valid
	@NotNull
	@OneToMany(mappedBy = "commentTo")
	public Collection<Comment> getReplys() {
		return this.replys;
	}

	public void setReplys(final Collection<Comment> replys) {
		this.replys = replys;
	}

	@Valid
	@ManyToOne(optional = true)
	public Comment getCommentTo() {
		return this.commentTo;
	}

	public void setCommentTo(final Comment commentTo) {
		this.commentTo = commentTo;
	}

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
