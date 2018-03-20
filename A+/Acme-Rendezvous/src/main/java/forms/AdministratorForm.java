
package forms;

import javax.validation.Valid;

import domain.Administrator;

public class AdministratorForm {

	@Valid
	private Administrator	administrator;
	private String			passwordCheck;
	private Boolean			conditions;


	public AdministratorForm() {
		super();
	}

	public AdministratorForm(final Administrator administrator) {
		this.administrator = administrator;
		this.passwordCheck = "";
		this.conditions = false;
	}

	public Administrator getAdministrator() {
		return this.administrator;
	}

	public void setAdministrator(final Administrator administrator) {
		this.administrator = administrator;
	}

	public String getPasswordCheck() {
		return this.passwordCheck;
	}

	public void setPasswordCheck(final String passwordCheck) {
		this.passwordCheck = passwordCheck;
	}

	public Boolean getConditions() {
		return this.conditions;
	}

	public void setConditions(final Boolean conditions) {
		this.conditions = conditions;
	}

}
