package domain;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;


public class MiscellaneousRecord extends DomainEntity{
	private String title;
	private String link;
	private Collection<String> comments;
	
	@NotNull
	@NotBlank
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@URL
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public Collection<String> getComments() {
		return comments;
	}
	
	public void setComments(Collection<String> comments) {
		this.comments = comments;
	}
	
	
}
