
package domain;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class Class extends DomainEntity {

	private String	title;
	private String	description;
	private Date	organisedMoment;
	private GPS		location;
	
	@NotNull
	@NotBlank
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@NotNull
	@NotBlank
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@NotNull
	public Date getOrganisedMoment() {
		return organisedMoment;
	}
	
	public void setOrganisedMoment(Date organisedMoment) {
		this.organisedMoment = organisedMoment;
	}
	
	@NotNull
	@NotBlank
	public GPS getLocation() {
		return location;
	}
	
	public void setLocation(GPS location) {
		this.location = location;
	}
	
	
}
