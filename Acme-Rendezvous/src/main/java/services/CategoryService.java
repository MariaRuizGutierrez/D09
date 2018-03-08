
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
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

	//CREATE
	public Category create() {
		Category result;
		Collection<Category> subCategories;

		Assert.notNull(this.administratorService.findByPrincipal());
		result = new Category();
		subCategories = new ArrayList<Category>();
		result.setSubCategories(subCategories);

		return result;
	}

	//SAVE
	public Category save(final Category category) {
		Assert.notNull(category);
		this.administratorService.checkPrincipal();
		Category result;
		Collection<Category> categories;

		//Comprobamos si la category no tiene el mismo nombre y mismo padre que una ya guardada
		categories = this.findAll();
		if (category.getId() != 0) {
			categories.remove(category);
			for (final Category c : categories)
				if (c.getFatherCategory() == null)
					Assert.isTrue(!c.getName().equals(category.getName()));
				else
					Assert.isTrue(!(c.getName().equals(category.getName()) && c.getFatherCategory().equals(category.getFatherCategory())));
		} else if (category.getId() == 0)
			for (final Category c : categories)
				if (c.getFatherCategory() == null)
					Assert.isTrue(!c.getName().equals(category.getName()));
				else
					Assert.isTrue(!(c.getName().equals(category.getName()) && c.getFatherCategory().equals(category.getFatherCategory())));
		result = this.categoryRepository.save(category);

		return result;
	}
	//DELETE
	public void delete(final Category category) {
		this.administratorService.checkPrincipal();
		Assert.notNull(category);

		Assert.isTrue(category.getId() != 0);
		//Assert.isTrue(!(this.configurationSystemService.defaultCategories().contains(category)));
		//Comprobamos que para borrar esa categor�a no tenga hijos
		Assert.isTrue(category.getSubCategories().isEmpty());

		this.categoryRepository.delete(category);
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
}
