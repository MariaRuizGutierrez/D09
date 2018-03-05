
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Trip extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private String				ticker;
	private String				title;
	private String				description;
	private double				price;
	private Collection<String>	requirementsExplorers;
	private Date				publicationDate;
	private Date				startDate;
	private Date				finishDate;
	private String				reasonWhy;
	private boolean				cancelled;


	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^[0-9]{2}(0[1-9]{1}|1[0-2]{1})((0|1|2)[0-9]{1}|3[0-1]{1})\\-[A-Z]{4}$")
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public double getPrice() {

		return this.price;
	}

	public void setPrice(final double price) {
		this.price = price;
	}

	@NotNull
	@ElementCollection
	public Collection<String> getRequirementsExplorers() {
		return this.requirementsExplorers;
	}

	public void setRequirementsExplorers(final Collection<String> requirementsExplorers) {
		this.requirementsExplorers = requirementsExplorers;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getPublicationDate() {
		return this.publicationDate;
	}

	public void setPublicationDate(final Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public Date getFinishDate() {
		return this.finishDate;
	}

	public void setFinishDate(final Date finishDate) {
		this.finishDate = finishDate;
	}

	public String getReasonWhy() {
		return this.reasonWhy;
	}

	public void setReasonWhy(final String reasonWhy) {
		this.reasonWhy = reasonWhy;
	}

	public boolean isCancelled() {
		return this.cancelled;
	}

	public void setCancelled(final boolean cancelled) {
		this.cancelled = cancelled;
	}


	// Relationships ----------------------------------------------------------
	private Manager						manager;
	private Collection<Stage>			stages;
	private Collection<Tag>				tags;
	private Collection<ApplicationFor>	applicationsFor;
	private Collection<Note>			notes;
	private Collection<AuditRecord>		auditRecords;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Manager getManager() {
		return this.manager;
	}

	public void setManager(final Manager manager) {
		this.manager = manager;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@Valid
	public Collection<Stage> getStages() {
		return this.stages;
	}

	public void setStages(final Collection<Stage> stages) {
		this.stages = stages;
	}

	@ManyToMany
	@Valid
	public Collection<Tag> getTags() {
		return this.tags;
	}

	public void setTags(final Collection<Tag> tags) {
		this.tags = tags;
	}

	@Valid
	@OneToMany
	public Collection<ApplicationFor> getApplicationsFor() {
		return this.applicationsFor;
	}

	public void setApplicationsFor(Collection<ApplicationFor> applicationsFor) {
		this.applicationsFor = applicationsFor;
	}

	@Valid
	@OneToMany
	public Collection<Note> getNotes() {
		return this.notes;
	}

	public void setNotes(Collection<Note> notes) {
		this.notes = notes;
	}

	@Valid
	@OneToMany
	public Collection<AuditRecord> getAuditRecords() {
		return this.auditRecords;
	}

	public void setAuditRecords(Collection<AuditRecord> auditRecords) {
		this.auditRecords = auditRecords;
	}

}
