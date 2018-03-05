
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
import org.hibernate.validator.constraints.NotEmpty;

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
	public Date getPublicationDate() {
		return this.publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
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

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}


	// Relationships ----------------------------------------------------------
	private Ranger						ranger;
	private Manager						manager;
	private Collection<SurvivalClass>	classes;
	private Collection<Story>			stories;
	private Collection<ApplicationFor>	applicationsFor;
	private Collection<AuditRecord>		auditRecords;
	private Collection<Note>			notes;
	private Collection<Category>		categories;
	private Collection<Sponsorship>		sponsorships;
	private Collection<Stage>			stages;
	private LegalText					legalText;
	private Collection<Tag>				tags;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Ranger getRanger() {
		return this.ranger;
	}

	public void setRanger(final Ranger ranger) {
		this.ranger = ranger;
	}
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Manager getManager() {
		return this.manager;
	}

	public void setManager(final Manager manager) {
		this.manager = manager;
	}

	@NotNull
	@OneToMany(mappedBy = "trip")
	@Valid
	public Collection<SurvivalClass> getClasses() {
		return this.classes;
	}

	public void setClasses(final Collection<SurvivalClass> classes) {
		this.classes = classes;
	}

	@NotNull
	@OneToMany(mappedBy = "trip")
	@Valid
	public Collection<Story> getStories() {
		return this.stories;
	}

	public void setStories(final Collection<Story> stories) {
		this.stories = stories;
	}

	@NotNull
	@OneToMany(mappedBy = "trip")
	@Valid
	public Collection<ApplicationFor> getApplicationsFor() {
		return this.applicationsFor;
	}

	public void setApplicationsFor(final Collection<ApplicationFor> applicationsFor) {
		this.applicationsFor = applicationsFor;
	}

	@NotNull
	@OneToMany(mappedBy = "trip")
	@Valid
	public Collection<AuditRecord> getAuditRecords() {
		return this.auditRecords;
	}

	public void setAuditRecords(final Collection<AuditRecord> auditRecords) {
		this.auditRecords = auditRecords;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "trip")
	public Collection<Note> getNotes() {
		return this.notes;
	}

	public void setNotes(final Collection<Note> notes) {
		this.notes = notes;
	}

	@NotNull
	@NotEmpty
	@ManyToMany
	@Valid
	public Collection<Category> getCategories() {
		return this.categories;
	}

	public void setCategories(final Collection<Category> categories) {
		this.categories = categories;
	}

	@NotNull
	@OneToMany(mappedBy = "trip")
	@Valid
	public Collection<Sponsorship> getSponsorships() {
		return this.sponsorships;
	}

	public void setSponsorships(final Collection<Sponsorship> sponsorships) {
		this.sponsorships = sponsorships;
	}

	@NotNull
	@NotEmpty
	@OneToMany(cascade = CascadeType.ALL)
	@Valid
	public Collection<Stage> getStages() {
		return this.stages;
	}

	public void setStages(final Collection<Stage> stages) {
		this.stages = stages;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public LegalText getLegalText() {
		return this.legalText;
	}

	public void setLegalText(final LegalText legalText) {
		this.legalText = legalText;
	}

	@NotNull
	@ManyToMany
	@Valid
	public Collection<Tag> getTags() {
		return this.tags;
	}

	public void setTags(final Collection<Tag> tags) {
		this.tags = tags;
	}

}
