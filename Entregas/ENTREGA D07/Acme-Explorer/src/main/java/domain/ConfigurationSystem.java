
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class ConfigurationSystem extends DomainEntity {

	private double					VAT;
	private String					banner;
	private Collection<String>		spamWords;
	private Collection<Category>	defaultCategories;
	private Collection<Tag>			defaultTags;
	private int						maxNumberFinder;
	private int						cacheMaxTime;
	private String					defaultPhone;


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
	@NotNull
	@NotEmpty
	public Collection<Category> getDefaultCategories() {
		return this.defaultCategories;
	}

	@ElementCollection
	@NotNull
	@NotEmpty
	public void setDefaultCategories(final Collection<Category> defaultCategories) {
		this.defaultCategories = defaultCategories;
	}

	@ElementCollection
	@NotNull
	@NotEmpty
	public Collection<Tag> getDefaultTags() {
		return this.defaultTags;
	}

	public void setDefaultTags(final Collection<Tag> defaultTags) {
		this.defaultTags = defaultTags;
	}

	@NotNull
	@Range(min = 1, max = 100)
	public int getMaxNumberFinder() {
		return this.maxNumberFinder;
	}

	public void setMaxNumberFinder(final int maxNumberFinder) {
		this.maxNumberFinder = maxNumberFinder;
	}

	@NotNull
	@Range(min = 1, max = 24)
	public int getCacheMaxTime() {
		return this.cacheMaxTime;
	}

	public void setCacheMaxTime(final int cacheMaxTime) {
		this.cacheMaxTime = cacheMaxTime;
	}

	@NotNull
	@NotBlank
	public String getDefaultPhone() {
		return this.defaultPhone;
	}

	public void setDefaultPhone(final String defaultPhone) {
		this.defaultPhone = defaultPhone;
	}

}
