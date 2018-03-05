
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class ConfigurationSystem extends DomainEntity {

	private double					VAT;
	private String					banner;
	private Collection<String>		spamWords;
	private Collection<String>		welcomeMessages;
	private Collection<Category>	defaultCategories;


	public double getVAT() {
		return this.VAT;
	}

	public void setVAT(final double vAT) {
		this.VAT = vAT;
	}

	@URL
	@NotBlank
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@ElementCollection
	@NotEmpty
	@NotNull
	public Collection<String> getSpamWords() {
		return this.spamWords;
	}

	public void setSpamWords(final Collection<String> spamWords) {
		this.spamWords = spamWords;
	}

	@ElementCollection
	@NotEmpty
	@NotNull
	public Collection<String> getWelcomeMessages() {
		return this.welcomeMessages;
	}

	public void setWelcomeMessages(final Collection<String> welcomeMessages) {
		this.welcomeMessages = welcomeMessages;
	}

	@ElementCollection
	@NotNull
	@NotEmpty
	public Collection<Category> getDefaultCategories() {
		return this.defaultCategories;
	}

	public void setDefaultCategories(final Collection<Category> defaultCategories) {
		this.defaultCategories = defaultCategories;
	}

}
