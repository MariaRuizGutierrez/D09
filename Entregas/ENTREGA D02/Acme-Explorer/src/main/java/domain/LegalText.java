
package domain;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class LegalText extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String	title;
	private String	body;
	private int		lawsNumber;
	private Date	moment;
	private boolean	draftMode;


	@NotBlank
	@NotNull
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		if (this.draftMode == true)
			this.title = title;
	}

	@NotBlank
	@NotNull
	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		if (this.draftMode == true)
			this.body = body;
	}

	public int getLawsNumber() {
		return this.lawsNumber;
	}

	public void setLawsNumber(int lawsNumber) {
		if (this.draftMode == true)
			this.lawsNumber = lawsNumber;
	}
	@NotNull
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(Date moment) {
		if (this.draftMode == true)
			this.moment = moment;
	}

	public boolean isDraftMode() {
		return this.draftMode;
	}

	public void setDraftMode(boolean draftMode) {
		this.draftMode = draftMode;
	}
}
