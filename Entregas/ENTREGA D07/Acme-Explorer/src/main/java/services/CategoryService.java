
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import domain.Administrator;
import domain.Category;

@Service
@Transactional
public class CategoryService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private CategoryRepository			categoryRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ConfigurationSystemService	configurationSystemService;
	@Autowired
	private AdministratorService		administratorService;


	// Constructors------------------------------------------------------------
	public CategoryService() {
		super();
	}

	// Simple CRUD methods-----------------------------------------------------
	public Category create() {
		Category result;
		Collection<Category> subCategories;

		Assert.notNull(this.administratorService.findByPrincipal());
		result = new Category();
		subCategories = new ArrayList<Category>();
		result.setSubCategories(subCategories);

		return result;
	}

	public Collection<Category> findAll() {
		Collection<Category> result;
		Assert.notNull(this.categoryRepository);
		result = this.categoryRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Category findOne(final int categoryId) {
		Category result;
		result = this.categoryRepository.findOne(categoryId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Category> findAllCategoriesByTripId(int tripId) {
		Collection<Category> result;
		result = new ArrayList<Category>(this.categoryRepository.findAllCategoriesByTripId(tripId));
		return result;
	}

	public Category save(final Category category) {
		Assert.notNull(category);
		Assert.notNull(this.administratorService.findByPrincipal());
		Category result;
		Collection<Category> categories;

		//Comprobamos si la category no tiene el mismo nombre y mismo padre que una ya guardada
		categories = this.findAll();
		for (final Category c : categories)
			Assert.isTrue(!(c.getName().equals(category.getName()) && c.getFatherCategory().equals(category.getFatherCategory())));
		result = this.categoryRepository.save(category);

		return result;
	}

	public void delete(final Category category) {
		Administrator admin;
		//Category father;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		Assert.notNull(category);

		Assert.isTrue(category.getId() != 0);
		Assert.isTrue(!(this.configurationSystemService.defaultCategories().contains(category)));

		Assert.isTrue(category.getSubCategories().isEmpty());

		this.categoryRepository.delete(category);
	}
}
