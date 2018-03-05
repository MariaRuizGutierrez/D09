
package domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class PersonalRecord extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String	fullName;
	private String	photo;
	private String	email;
	private String	phone;
	private String	linkedProfile;


	@NotBlank
	@NotNull
	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@NotNull
	@URL
	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@NotNull
	@Email
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@NotNull
	@Pattern(regexp = "(^(\\+([0-9]{1}|[0-9]{2}|[0-9]{3})\\(([0-9]{1}|[0-9]{2}|[0-9]{3})\\)([0-9]{4,100}))|(\\+([0-9]{1}|[0-9]{2}|[0-9]{3})\\s([0-9]{4,100}))|(([0-9] {4,100})))$")
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@NotNull
	@URL
	public String getLinkedProfile() {
		return this.linkedProfile;
	}

	public void setLinkedProfile(String linkedProfile) {
		this.linkedProfile = linkedProfile;
	}

}
