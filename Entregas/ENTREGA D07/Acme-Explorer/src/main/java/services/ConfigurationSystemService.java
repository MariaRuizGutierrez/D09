
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ConfigurationSystemRepository;
import domain.Category;
import domain.ConfigurationSystem;

@Service
@Transactional
public class ConfigurationSystemService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ConfigurationSystemRepository	configurationSystemRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public ConfigurationSystemService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Collection<ConfigurationSystem> findAll() {
		Collection<ConfigurationSystem> result;

		Assert.notNull(this.configurationSystemRepository);
		result = this.configurationSystemRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public ConfigurationSystem findOne() {
		ConfigurationSystem res;

		res = this.findAll().iterator().next();
		Assert.notNull(res);

		return res;

	}

	public ConfigurationSystem findOne(final int configurationSystemId) {
		ConfigurationSystem result;

		result = this.configurationSystemRepository.findOne(configurationSystemId);
		Assert.notNull(result);

		return result;
	}

	public ConfigurationSystem save(final ConfigurationSystem configurationSystem) {
		Assert.notNull(configurationSystem);

		ConfigurationSystem result;

		result = this.configurationSystemRepository.save(configurationSystem);

		return result;
	}

	// Other business methods -------------------------------------------------

	public Collection<String> spamWord() {
		Collection<String> res;

		res = this.configurationSystemRepository.spamWord();
		Assert.notNull(res);

		return res;
	}

	public Double getVat() {
		Double vat;
		vat = this.configurationSystemRepository.vat();

		return vat;
	}

	public Collection<Category> defaultCategories() {
		Collection<Category> res;
		res = this.configurationSystemRepository.defaultCategories();
		Assert.notNull(res);
		return res;
	}
}
