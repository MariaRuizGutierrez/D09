
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class Trip extends DomainEntity {

	private String				ticker;
	private String				title;
	private String				description;
	private double				price;
	private Collection<String>	requirementsExplorers;
	private Date				publicationDate;
	private Date				startDate;
	private Date				finishDate;
	private String				reasonWhy;


	@NotNull
	@Column(unique = true)
	@Pattern(regexp = "^[0-9]{2}(0[1-9]{1}|1[0-2]{1})((0|1|2)[0-9]{1}|3[0-1]{1})\\-[A-Z]{4}$")
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	@NotNull
	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@NotNull
	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Collection<String> getRequirementsExplorers() {
		return this.requirementsExplorers;
	}

	public void setRequirementsExplorers(Collection<String> requirementsExplorers) {
		this.requirementsExplorers = requirementsExplorers;
	}

	public Date getPublicationDate() {
		return this.publicationDate;
	}

	@NotNull
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@NotNull
	public Date getFinishDate() {
		return this.finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public String getReasonWhy() {
		return this.reasonWhy;
	}

	public void setReasonWhy(String reasonWhy) {
		if (!this.publicationDate.equals(null))
			this.reasonWhy = reasonWhy;
	}

}
