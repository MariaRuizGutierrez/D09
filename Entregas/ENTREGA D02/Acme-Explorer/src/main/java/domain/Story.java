
package domain;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class Story extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String				title;
	private String				text;
	private Collection<String>	attachments;


	@NotBlank
	@NotNull
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@NotBlank
	@NotNull
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Collection<String> getAttachments() {
		return this.attachments;
	}

	public void setAttachments(Collection<String> attachments) {
		this.attachments = attachments;
	}

}
