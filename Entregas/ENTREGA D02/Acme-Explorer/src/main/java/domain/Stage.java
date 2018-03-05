
package domain;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class Stage extends DomainEntity {

	private String		title;
	private String		description;
	private double		price;
	private double		iva;


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
	
	public void setPrice(double price){
		this.price=price;
	}

	public double getIva() {
		return iva;
	}

	public void setIva(double iva) {
		this.iva = iva;
	}
	
	public double getTotalPrice(){
		return this.price + (this.price*this.iva);
	}

}
