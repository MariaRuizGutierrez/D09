
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private String	keyWord;
	private Double	lowPrice;
	private Double	highPrice;
	private Date	initialDate;
	private Date	finalDate;
	private Date	startCacheTime;


	public String getKeyWord() {
		return this.keyWord;
	}

	public void setKeyWord(final String keyWord) {
		this.keyWord = keyWord;
	}

	public Double getLowPrice() {
		return this.lowPrice;
	}

	public void setLowPrice(final Double lowPrice) {
		this.lowPrice = lowPrice;
	}

	public Double getHighPrice() {
		return this.highPrice;
	}

	public void setHighPrice(final Double highPrice) {
		this.highPrice = highPrice;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public Date getInitialDate() {
		return this.initialDate;
	}

	public void setInitialDate(final Date initialDate) {
		this.initialDate = initialDate;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public Date getFinalDate() {
		return this.finalDate;
	}

	public void setFinalDate(final Date finalDate) {
		this.finalDate = finalDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getStartCacheTime() {
		return this.startCacheTime;
	}

	public void setStartCacheTime(final Date startCacheTime) {
		this.startCacheTime = startCacheTime;
	}


	// Relationships ----------------------------------------------------------

	private Collection<Trip>	trips;


	@NotNull
	@Valid
	@ManyToMany
	public Collection<Trip> getTrips() {
		return this.trips;
	}

	public void setTrips(final Collection<Trip> trips) {
		this.trips = trips;
	}
}
