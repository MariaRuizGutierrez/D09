
package services;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Category;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CategoryServiceTest extends AbstractTest {

	// Supporting services ----------------------------------------------------

	@Autowired
	CategoryService	categoryService;

	@PersistenceContext
	EntityManager	entityManager;


	// Test CreateAndSave ----------------------------------------------------------------------------------
	// Caso de uso 11.1
	@Test
	public void driverCreateAndSave() {
		final Object testingData[][] = {
			{
				//Se crea un Category correctamente
				"admin", "name test", "description test", "category1", null
			}, {
				//Se crea un Category con name y name del padre similar a uno ya guardado
				"admin", "category 1-1", "description test", "category1", IllegalArgumentException.class
			}, {
				//Se crea un Category con name similar a uno ya guardado pero no el name del padre
				"admin", "category 1-1", "description test", "category1-2", null
			}, {
				//Se crea un Category con name similar a uno ya guardado pero no el name del padre
				"admin", "category 1", "description test", "category1-2", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreateAndSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], super.getEntityId((String) testingData[i][3]), (Class<?>) testingData[i][4]);
	}

	private void templateCreateAndSave(final String username, final String name, final String description, final int fatherCategoryId, final Class<?> expected) {
		Category category;
		Category fatherCategory;
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			fatherCategory = this.categoryService.findOne(fatherCategoryId);
			category = this.categoryService.create();
			category.setName(name);
			category.setDescription(description);
			category.setFatherCategory(fatherCategory);
			category = this.categoryService.save(category);
			this.categoryService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();

		}

		this.checkExceptions(expected, caught);
		this.unauthenticate();

	}
	// Test Edit ----------------------------------------------------------------------------------
	// Caso de uso 11.1
	@Test
	public void driverEdit() {
		final Object testingData[][] = {
			{
				//Se edita el category1 por el admin
				"admin", "category1", null
			}, {
				//Se edita el category1 por un user (ningun user puede editarla)
				"user1", "category1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateEdit((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}
	private void templateEdit(final String username, final int categoryId, final Class<?> expected) {
		Category category;
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			category = this.categoryService.findOne(categoryId);
			category.setName("Editing test name");
			category = this.categoryService.save(category);
			this.unauthenticate();
			this.categoryService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	// Test Delete ----------------------------------------------------------------------------------
	// Caso de uso 11.1
	@Test
	public void driverDelete() {
		final Object testingData[][] = {
			{
				//Se elimina el category1-2 por el admin
				"admin", "category1-2", null
			}, {
				//Se elimina el category1-1-1 por el admin (no se puede eliminar porque tiene un ServiceOffice asociado
				"admin", "category1-1-1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDelete((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}
	private void templateDelete(final String username, final int categoryId, final Class<?> expected) {
		Category category;
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			category = this.categoryService.findOne(categoryId);
			this.categoryService.delete(category);
			this.unauthenticate();
			this.categoryService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	// Test List
	// Se comprueba el list de todas las categorias
	// Caso de uso 11.1
	@Test
	public void driverFindAll() {
		final Object testingData[][] = {
			{
				// Se comprueba que la category 1 aparece en la lista y que la lista tiene tamaño 5
				"category1", 5, null
			}, {
				// Se comprueba que la category 1 aparece en la lista pero que la lista no tiene tamaño 7
				"category1-1", 3, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateFindAll(super.getEntityId((String) testingData[i][0]), (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	private void templateFindAll(final int categoryId, final int size, final Class<?> expected) {
		final Collection<Category> listCategories;
		Category category;
		Class<?> caught;

		caught = null;
		try {
			listCategories = this.categoryService.findAll();
			category = this.categoryService.findOne(categoryId);
			Assert.isTrue(listCategories.contains((category)));
			Assert.isTrue(listCategories.size() == size);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
