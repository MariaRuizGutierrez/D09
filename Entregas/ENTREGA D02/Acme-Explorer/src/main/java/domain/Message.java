
package domain;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class Message extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String	sender;
	private String	recipient;
	private Date	moment;
	private String	subject;
	private String	body;
	private String	priority;


	@NotBlank
	@NotNull
	public String getSender() {
		return this.sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}
	@NotBlank
	@NotNull
	public String getRecipient() {
		return this.recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	@Past
	@NotNull
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(Date moment) {
		this.moment = moment;
	}
	@NotNull
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	@NotNull
	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@NotNull
	@Pattern(regexp = "^((HIGH)|(NEUTRAL)|(LOW))$")
	public String getPriority() {
		return this.priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

}
