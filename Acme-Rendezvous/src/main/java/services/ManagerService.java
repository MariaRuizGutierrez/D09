package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ManagerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Manager;
import forms.ManagerForm;

@Service
@Transactional
public class ManagerService {
	
	// Managed repository -----------------------------------------------------
	@Autowired
	private ManagerRepository	managerRepository;
	
	// Supporting services ----------------------------------------------------
	
	@Autowired
	private Validator validator;

	// Constructors -----------------------------------------------------------
	
	public ManagerService(){
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------
	
//	public Manager create(){
//		
//		Manager result;
//		UserAccount userAccount;
//		Authority authority;
//		Collection<Service> services;
//		
//		result = new Manager();
//		userAccount = new UserAccount();
//		authority = new Authority();
//		services = new ArrayList<>();
//		
//		authority.setAuthority(Authority.MANAGER);
//		userAccount.addAuthority(authority);
//		result.setUserAccount(userAccount);
//		result.setServices(services);
//		
//		return result;
//		
//	}
	
	public Collection<Manager> findAll(){
		
		Collection<Manager> result;
		
		result = this.managerRepository.findAll();
		
		return result;
		
	}
	
	public Manager findOne(int managerId){
		
		Manager result;
		
		result = this.managerRepository.findOne(managerId);
		
		return result;
	}
	
	public Manager save(Manager manager){
		
		Assert.notNull(manager);
		
		Manager result;
		Md5PasswordEncoder encoder;
		String passwordHash;
		
		if(manager.getId()==0){
			encoder = new Md5PasswordEncoder();
			passwordHash = encoder.encodePassword(manager.getUserAccount().getPassword(), null);
			manager.getUserAccount().setPassword(passwordHash);
		}
		

		result = this.managerRepository.save(manager);

		Assert.notNull(result);

		return result;
		
	}
	
	public void delete(Manager manager){
		
		Assert.notNull(manager);
		Assert.isTrue(manager.getId() != 0);
		
		this.managerRepository.delete(manager);
		
	}

	
	// Other business methods----------------------------------
	
	public Manager findByPrincipal() {

		Manager result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.managerRepository.findByManagerAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}
	
	public void checkPrincipal() {

		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		final Collection<Authority> authorities = userAccount.getAuthorities();
		Assert.notNull(authorities);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.MANAGER);

		Assert.isTrue(authorities.contains(auth));
	}
	
	public ManagerForm reconstruct(ManagerForm managerForm, BindingResult binding){
		
		ManagerForm result = null;
		Manager manager;
		manager = managerForm.getManager();
		
		if(manager.getId() == 0){
			UserAccount userAccount;
			Authority authority;
			
			userAccount = managerForm.getManager().getUserAccount();
			authority = new Authority();
			authority.setAuthority(Authority.MANAGER);
			userAccount.addAuthority(authority);
			managerForm.getManager().setUserAccount(userAccount);
			result = managerForm;
			
		}else{
			
			manager = this.managerRepository.findOne(managerForm.getManager().getId());
			managerForm.getManager().setId(manager.getId());
			managerForm.getManager().setVersion(manager.getVersion());
			managerForm.getManager().setUserAccount(manager.getUserAccount());
			
			result = managerForm;
			
			
		}
		

		this.validator.validate(result, binding);

		return result;
		
	}

}
