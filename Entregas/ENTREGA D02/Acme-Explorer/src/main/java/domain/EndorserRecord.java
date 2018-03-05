
package domain;

import java.util.Collection;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class EndorserRecord extends DomainEntity {

	private String				fullName;
	private String				email;
	private String				linkedProfile;
	private Collection<String>	comments;
	private String				phone;


	@NotNull
	@NotBlank
	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(final String fullName) {
		this.fullName = fullName;
	}

	@NotNull
	@Email
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@NotNull
	@URL
	public String getLinkedProfile() {
		return this.linkedProfile;
	}

	public void setLinkedProfile(final String linkedProfile) {
		this.linkedProfile = linkedProfile;
	}

	public Collection<String> getComments() {
		return this.comments;
	}

	public void setComments(final Collection<String> comments) {
		this.comments = comments;
	}

	@NotNull
	@Pattern(regexp = "(^(\\+([0-9]{1}|[0-9]{2}|[0-9]{3})\\(([0-9]{1}|[0-9]{2}|[0-9]{3})\\)([0-9]{4,100}))|(\\+([0-9]{1}|[0-9]{2}|[0-9]{3})\\s([0-9]{4,100}))|(([0-9] {4,100})))$")
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}
}
