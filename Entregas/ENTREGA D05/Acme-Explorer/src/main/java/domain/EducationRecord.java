
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class EducationRecord extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String				diplomaTitle;
	private String				studyingPeriod;
	private String				institution;
	private String				link;
	private Collection<String>	comments;


	@NotBlank
	public String getDiplomaTitle() {
		return this.diplomaTitle;
	}

	public void setDiplomaTitle(String diplomaTitle) {
		this.diplomaTitle = diplomaTitle;
	}

	@NotNull
	@Pattern(regexp = "^(1|2){1}([0-9]){1}([0-9]{1})([0-9]{1})([/])(1|2){1}([0-9]){1}([0-9]{1})([0-9]{1})$")
	public String getStudyingPeriod() {
		return this.studyingPeriod;
	}

	public void setStudyingPeriod(String studyingPeriod) {
		this.studyingPeriod = studyingPeriod;
	}

	@NotBlank
	public String getInstitution() {
		return this.institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	@URL
	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@ElementCollection
	@NotNull
	public Collection<String> getComments() {
		return this.comments;
	}

	public void setComments(Collection<String> comments) {
		this.comments = comments;
	}

	// Relationships--------------------------------------------------------------

}
