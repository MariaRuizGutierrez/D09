
package services;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Manager;
import domain.Rendezvouse;
import domain.ServiceOffered;
import forms.AdministratorForm;

@Service
@Transactional
public class AdministratorService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private AdministratorRepository	administratorRepository;

	//Importar la que pertenece a Spring
	@Autowired
	private Validator				validator;


	// Simple CRUD methods------------------------------------------------
	public Administrator create() {
		Administrator result;
		UserAccount userAccount;
		Authority authority;

		result = new Administrator();
		userAccount = new UserAccount();
		authority = new Authority();

		authority.setAuthority(Authority.ADMINISTRATOR);
		userAccount.addAuthority(authority);
		result.setUserAccount(userAccount);

		return result;
	}

	public Collection<Administrator> findAll() {
		Collection<Administrator> result;
		result = this.administratorRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Administrator findOne(final int administratorId) {
		Assert.isTrue(administratorId != 0);
		Administrator result;
		result = this.administratorRepository.findOne(administratorId);
		return result;
	}

	public Administrator save(final Administrator administrator) {

		Assert.notNull(administrator);
		final Administrator result;
		final Md5PasswordEncoder encoder;
		final String passwordHash;

		if (administrator.getId() == 0) {
			final String password = administrator.getUserAccount().getPassword();
			encoder = new Md5PasswordEncoder();
			passwordHash = encoder.encodePassword(password, null);
			administrator.getUserAccount().setPassword(passwordHash);
		}
		result = this.administratorRepository.save(administrator);

		Assert.notNull(result);

		return result;
	}

	// Other business methods------------------------------------------------------
	public Administrator findByPrincipal() {
		Administrator result;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.administratorRepository.findByUserAccountId(userAccount.getId());
		return result;
	}

	public void checkPrincipal() {

		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		final Collection<Authority> authorities = userAccount.getAuthorities();
		Assert.notNull(authorities);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMINISTRATOR);

		Assert.isTrue(authorities.contains(auth));
	}

	public Double[] findAvgStddevOfTheNumOfRendezvouseCreatedPerUser() {
		Double[] result;
		result = this.administratorRepository.findAvgStddevOfTheNumOfRendezvouseCreatedPerUser();
		if (result[0] == null && result[1] == null) {
			result[1] = 0.;
			result[0] = 0.;
		} else if (result[0] == null)
			result[0] = 0.;
		else if (result[1] == null)
			result[1] = 0.;

		return result;
	}

	public Double findRatioUsersWithRendezvousesAndNotRendezvouses() {
		Double result;
		result = this.administratorRepository.findRatioUsersWithRendezvousesAndNotRendezvouses();
		if (result == null)
			result = 0.;
		return result;
	}

	public Double findAvgStddevOfTheNumOfAssistansPerRendezvouse() {
		Double result;
		result = this.administratorRepository.findAvgStddevOfTheNumOfAssistansPerRendezvouse();
		if (result == null)
			result = 0.;
		return result;
	}

	public Double[] findAvgStddevOfTheNumOfRendezvouseAssitedPerUser() {
		Double[] result;
		result = this.administratorRepository.findAvgStddevOfTheNumOfRendezvouseAssitedPerUser();
		if (result[0] == null && result[1] == null) {
			result[1] = 0.;
			result[0] = 0.;
		} else if (result[0] == null)
			result[0] = 0.;
		else if (result[1] == null)
			result[1] = 0.;
		return result;
	}

	public Collection<Rendezvouse> findTop10RendezvouseWithRSVPd() {
		Collection<Rendezvouse> result;
		final Page<Rendezvouse> resPage;
		final Pageable pageable;

		pageable = new PageRequest(0, 10);
		resPage = this.administratorRepository.findTop10RendezvouseWithRSVPd(pageable);
		result = resPage.getContent();
		return result;
	}

	public Double[] findAvgStddevOfTheNumOfAnnouncementsPerRendezvous() {
		Double[] result;
		result = this.administratorRepository.findAvgStddevOfTheNumOfAnnouncementsPerRendezvous();
		if (result[0] == null && result[1] == null) {
			result[1] = 0.;
			result[0] = 0.;
		} else if (result[0] == null)
			result[0] = 0.;
		else if (result[1] == null)
			result[1] = 0.;
		return result;
	}
	public Collection<Rendezvouse> findRendezvousesWithMore75PerCent() {
		Collection<Rendezvouse> result;
		result = this.administratorRepository.findRendezvousesWithMore75PerCent();
		Assert.notNull(result);
		return result;
	}

	public Collection<Rendezvouse> findRendezvousesWithAreLinked() {
		Collection<Rendezvouse> result;
		result = this.administratorRepository.findRendezvousesWithAreLinked();
		Assert.notNull(result);
		return result;
	}

	public Double[] findAvgStddevOfTheNumOfQuestionsPerRendezvous() {
		Collection<Long> resultQuery;
		final Double[] result;
		final Double avg;
		final Double stdev;

		resultQuery = this.administratorRepository.findAvgStddevOfTheNumOfQuestionsPerRendezvous();
		Assert.notNull(resultQuery);
		avg = AdministratorService.calculateAvg(resultQuery);
		stdev = AdministratorService.calculateStdev(resultQuery);
		result = new Double[] {
			avg, stdev
		};

		return result;
	}
	public Double[] findAvgStddevOfTheNumOfAnswerToQuestionsPerRendezvous() {
		Collection<Long> resultQuery;
		final Double[] result;
		Double avg;
		Double stdev;

		resultQuery = this.administratorRepository.findAvgStddevOfTheNumOfAnswerToQuestionsPerRendezvous();
		Assert.notNull(resultQuery);
		avg = AdministratorService.calculateAvg(resultQuery);
		if (avg == null)
			avg = 0.;
		stdev = AdministratorService.calculateStdev(resultQuery);
		result = new Double[] {
			avg, stdev
		};
		return result;
	}

	public Double[] findAvgStddevOfTheNumOfRepliesPerComment() {
		Double[] result;
		result = this.administratorRepository.findAvgStddevOfTheNumOfRepliesPerComment();
		if (result[0] == null && result[1] == null) {
			result[1] = 0.;
			result[0] = 0.;
		} else if (result[0] == null)
			result[0] = 0.;
		else if (result[1] == null)
			result[1] = 0.;
		return result;
	}

	public static double calculateAvg(final Collection<Long> resultQuery) {
		double sum = 0;
		for (final Long num : resultQuery)
			sum += num;
		return sum / resultQuery.size();
	}
	public static double calculateStdev(final Collection<Long> resultQuery) {
		double sum = 0.0;
		double standardDeviation = 0.0;
		final double avg = AdministratorService.calculateAvg(resultQuery);
		for (final double num : resultQuery)
			sum += (num - avg) * (num - avg);
		standardDeviation = Math.sqrt(sum / resultQuery.size());
		return standardDeviation;
	}

	//	C1
	public Collection<ServiceOffered> bestSellingServices() {
		Collection<ServiceOffered> result;
		result = this.administratorRepository.bestSellingServices();
		return result;
	}

	//	C2
	public Collection<Manager> managerProvidesMoreServicesThanAverage() {
		Collection<Manager> result;
		result = this.administratorRepository.managerProvidesMoreServicesThanAverage();
		return result;
	}

	//  C3
	public Collection<Manager> managersWhohaveMoreServicesCancelled() {
		Collection<Manager> resultQuery2;
		Collection<Long> resultQuery1;
		Long maxNumber;

		resultQuery1 = this.administratorRepository.managersWhohaveMoreServicesCancelled1();
		maxNumber = Collections.max(resultQuery1);
		resultQuery2 = this.administratorRepository.managersWhohaveMoreServicesCancelled2(maxNumber);

		return resultQuery2;
	}

	//	B1
	public Double findAvgMNumOfCategoriesPerRendezvous() {
		Double result;
		result = this.administratorRepository.findAvgNumOfCategoriesPerRendezvous();
		return result;
	}

	//	B2
	public Double findAvgNumOfServicesPerCategories() {
		Double result;
		result = this.administratorRepository.findAvgNumOfServicesPerCategories();
		return result;
	}

	// B3
	public Double[] findAvgMinMaxStddevOfTheNumOfRequestedPerRendezvouse() {
		Double[] result;
		result = this.administratorRepository.findAvgMinMaxStddevOfTheNumOfRequestedPerRendezvouse();
		return result;
	}

	//	B4
	public Collection<Rendezvouse> findTop10Services() {
		Collection<Rendezvouse> result;
		final Page<Rendezvouse> resPage;
		final Pageable pageable;

		pageable = new PageRequest(0, 3);
		resPage = this.administratorRepository.findTop10Services(pageable);
		result = resPage.getContent();
		return result;
	}

	public AdministratorForm reconstruct(final AdministratorForm administratorForm, final BindingResult bindingResult) {
		final AdministratorForm result;
		final Administrator adminBD;

		if (administratorForm.getAdministrator().getId() == 0) {
			UserAccount userAccount;
			Authority authority;

			userAccount = administratorForm.getAdministrator().getUserAccount();
			authority = new Authority();
			authority.setAuthority(Authority.ADMINISTRATOR);
			userAccount.addAuthority(authority);
			administratorForm.getAdministrator().setUserAccount(userAccount);
			result = administratorForm;
		} else {
			adminBD = this.administratorRepository.findOne(administratorForm.getAdministrator().getId());
			administratorForm.getAdministrator().setId(adminBD.getId());
			administratorForm.getAdministrator().setVersion(adminBD.getVersion());
			administratorForm.getAdministrator().setUserAccount(adminBD.getUserAccount());
			result = administratorForm;
		}
		this.validator.validate(result, bindingResult);
		return result;
	}
}
