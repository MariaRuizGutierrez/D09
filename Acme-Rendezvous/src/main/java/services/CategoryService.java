
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import domain.Category;
import domain.ServiceOffered;

@Service
@Transactional
public class CategoryService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private CategoryRepository		categoryRepository;

	// Supporting services ---------------------------------------------------
	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private ServiceOfferedService	serviceOfferedService;


	// Constructors------------------------------------------------------------
	public CategoryService() {
		super();
	}

	// Simple CRUD methods-----------------------------------------------------

	//CREATE
	public Category create() {
		Category result;
		Collection<Category> subCategories;
		Collection<ServiceOffered> servicesOffered;

		Assert.notNull(this.administratorService.findByPrincipal());

		result = new Category();
		subCategories = new ArrayList<Category>();
		servicesOffered = new ArrayList<ServiceOffered>();
		result.setSubCategories(subCategories);
		result.setServicesOffered(servicesOffered);

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
				if (c.getFatherCategory() == null) {
					if (category.getFatherCategory() == null)
						Assert.isTrue(!c.getName().equals(category.getName()));
				} else
					Assert.isTrue(!(c.getName().equals(category.getName()) && c.getFatherCategory().equals(category.getFatherCategory())));
		result = this.categoryRepository.save(category);

		return result;
	}
	//DELETE
	public void delete(final Category category) {
		this.administratorService.checkPrincipal();
		Assert.notNull(category);

		Assert.isTrue(category.getServicesOffered().size() == 0);

		if (category.getSubCategories().size() > 0)
			for (final Category c : category.getSubCategories())
				this.delete(c);
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

	public void flush() {
		this.categoryRepository.flush();
	}
}
