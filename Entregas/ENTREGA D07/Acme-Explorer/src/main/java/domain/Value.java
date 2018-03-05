
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Value extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private Integer	value;


	@Range(min = 0, max = 10)
	public Integer getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = value;
	}


	// Relationships ----------------------------------------------------------
	private Tag		tag;
	private Trip	trip;


	@ManyToOne(optional = true)
	public Tag getTag() {
		return this.tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	@ManyToOne(optional = true)
	public Trip getTrip() {
		return this.trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}

}
