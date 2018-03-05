
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.UserRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Rendezvouse;
import domain.User;
import forms.UserForm;

@Service
@Transactional
public class UserService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private UserRepository	userRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public UserService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public User create() {

		User result;
		UserAccount userAccount;
		Authority authority;
		Collection<Rendezvouse> rendezvousesCreated;
		Collection<Rendezvouse> rendezvousesAssisted;

		result = new User();
		userAccount = new UserAccount();
		authority = new Authority();
		rendezvousesCreated = new ArrayList<>();
		rendezvousesAssisted = new ArrayList<>();

		authority.setAuthority(Authority.USER);
		userAccount.addAuthority(authority);
		result.setUserAccount(userAccount);
		result.setRendezvousesCreated(rendezvousesCreated);
		result.setRendezvousesAssisted(rendezvousesAssisted);

		return result;

	}

	public Collection<User> findAll() {

		Collection<User> result;

		result = this.userRepository.findAll();

		return result;

	}

	public User findOne(int userId) {

		Assert.isTrue(userId != 0);
		User result;
		result = this.userRepository.findOne(userId);

		return result;

	}

	public User save(User user) {

		Assert.notNull(user);

		User result;
		Md5PasswordEncoder encoder;
		String passwordHash;

		if(user.getId()==0){
			encoder = new Md5PasswordEncoder();
			passwordHash = encoder.encodePassword(user.getUserAccount().getPassword(), null);
			user.getUserAccount().setPassword(passwordHash);
		}
		

		result = this.userRepository.save(user);

		Assert.notNull(result);

		return result;

	}

	public void delete(final User user) {

		Assert.notNull(user);
		Assert.isTrue(user.getId() != 0);
		this.userRepository.delete(user);

	}

	// Other business methods----------------------------------

	public User findByPrincipal() {

		User result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.userRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	public void checkPrincipal() {

		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		final Collection<Authority> authorities = userAccount.getAuthorities();
		Assert.notNull(authorities);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.USER);

		Assert.isTrue(authorities.contains(auth));
	}

	public User findUserByRendezvousId(int rendezvousId) {
		User user;
		user = this.userRepository.findUserByRendezvousId(rendezvousId);
		return user;
	}
	
	@Autowired
	private Validator validator;
	
	public UserForm reconstruct(UserForm userForm, BindingResult binding){
		
		UserForm result = null;
		User user;
		user = userForm.getUser();
		
		if(user.getId() == 0){
			UserAccount userAccount;
			Authority authority;
			
			userAccount = userForm.getUser().getUserAccount();
			authority = new Authority();
			authority.setAuthority(Authority.USER);
			userAccount.addAuthority(authority);
			userForm.getUser().setUserAccount(userAccount);
			result = userForm;
			
		}else{
			
			user = this.userRepository.findOne(userForm.getUser().getId());
			userForm.getUser().setId(user.getId());
			userForm.getUser().setVersion(user.getVersion());
			userForm.getUser().setUserAccount(user.getUserAccount());
			
			result = userForm;
			
			
		}
		

		this.validator.validate(result, binding);

		return result;
		
	}

}
