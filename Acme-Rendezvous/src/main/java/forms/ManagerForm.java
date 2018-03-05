package forms;

import javax.validation.Valid;

import domain.Manager;
import domain.User;


public class ManagerForm {
	
	@Valid
	private Manager		manager;
	private String		passwordCheck;
	private Boolean		conditions;
	
	public ManagerForm(){
		super();
	}
	
	public ManagerForm(final Manager manager) {
		this.manager = manager;
		this.passwordCheck = "";
		this.conditions = false;
	}

	
	public Manager getManager() {
		return manager;
	}

	
	public void setManager(Manager manager) {
		this.manager = manager;
	}

	
	public String getPasswordCheck() {
		return passwordCheck;
	}

	
	public void setPasswordCheck(String passwordCheck) {
		this.passwordCheck = passwordCheck;
	}

	
	public Boolean getConditions() {
		return conditions;
	}

	
	public void setConditions(Boolean conditions) {
		this.conditions = conditions;
	}

}
