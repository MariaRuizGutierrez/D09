
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
	@Index(columnList = "question_id,user_id")
})
public class Answer extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String	reply;


	@NotBlank
	public String getReply() {
		return this.reply;
	}

	public void setReply(final String reply) {
		this.reply = reply;
	}


	//-----------------------------Relationships-----------------------------
	private User		user;
	private Question	question;


	@Valid
	@ManyToOne(optional = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	@Valid
	@ManyToOne(optional = false)
	public Question getQuestion() {
		return this.question;
	}

	public void setQuestion(final Question question) {
		this.question = question;
	}

}
