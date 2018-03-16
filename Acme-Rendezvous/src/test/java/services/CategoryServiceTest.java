
package services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

	@Test
	public void driverCreateAndSave() {
		final Object testingData[][] = {
			{
				//Se crea un Category correctamente
				"admin", "name test", "description test", "category1", null
			}
		//			, {
		//				//Se crea un Category para un User que no le pertenece ese Rendezvous (Hacking get)
		//				"user5", "rendezvouse1", "title test", "description test", IllegalArgumentException.class
		//			}, {
		//				//Se crea un Category con el title en null
		//				"user1", "rendezvouse1", null, "description test", javax.validation.ConstraintViolationException.class
		//			}, {
		//				//Se crea un Category con el title en blanco
		//				"user1", "rendezvouse1", "", "description test", javax.validation.ConstraintViolationException.class
		//			}, {
		//				//Se crea un Category con el description en null
		//				"user1", "rendezvouse1", "title test", null, javax.validation.ConstraintViolationException.class
		//			}, {
		//				//Se crea un Category con el description en blanco
		//				"user1", "rendezvouse1", "title test", "", javax.validation.ConstraintViolationException.class
		//			}
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

	//		@Test
	//		public void driverEdit() {
	//			final Object testingData[][] = {
	//				{
	//					//Se edita el category1 por el user que la ha creado
	//					"user1", "category1", null
	//				}, {
	//					//Se edita el category1 por el user que NO la ha creado (Hacking get)
	//					"user5", "category1", IllegalArgumentException.class
	//				}
	//			};
	//			for (int i = 0; i < testingData.length; i++)
	//				this.templateEdit((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	//		}
	//		private void templateEdit(final String username, final int categoryId, final Class<?> expected) {
	//			Category category;
	//			Class<?> caught;
	//
	//			caught = null;
	//			try {
	//				super.authenticate(username);
	//				category = this.categoryService.findOne(categoryId);
	//				category.setTitle("Editing test title");
	//				category = this.categoryService.save(category);
	//				this.unauthenticate();
	//				this.categoryService.flush();
	//			} catch (final Throwable oops) {
	//				caught = oops.getClass();
	//			}
	//
	//			this.checkExceptions(expected, caught);
	//
	//		}
	//
	//		// Test Delete ----------------------------------------------------------------------------------
	//		// Se comprueba que solo el Admin es el unico que puede eliminar un Category
	//		@Test
	//		public void driverDelete() {
	//			final Object testingData[][] = {
	//				{
	//					//Se elimina el category1 por el user que la ha creado (ningun user puede eliminar un category)
	//					"user1", "category1", IllegalArgumentException.class
	//				}, {
	//					//Se elimina el category1 por el user que NO la ha creado(Hacking get) (ningun user puede eliminar un category)
	//					"user5", "category1", IllegalArgumentException.class
	//				}, {
	//					//Se elimina el category1 por un admin cualquiera (Cualquier admin puede eliminar cualquier category)
	//					"admin", "category1", null
	//				}
	//			};
	//			for (int i = 0; i < testingData.length; i++)
	//				this.templateDelete((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	//		}
	//		private void templateDelete(final String username, final int categoryId, final Class<?> expected) {
	//			Category category;
	//			Class<?> caught;
	//
	//			caught = null;
	//			try {
	//				super.authenticate(username);
	//				category = this.categoryService.findOne(categoryId);
	//				this.categoryService.delete(category);
	//				this.unauthenticate();
	//				this.categoryService.flush();
	//			} catch (final Throwable oops) {
	//				caught = oops.getClass();
	//			}
	//
	//			this.checkExceptions(expected, caught);
	//
	//		}
	//
	//		// Test findCategoryByUserIdForRendezvousesAssits
	//		// Se comprueba el list que devuelve todos los categorys de las rendezvouses a la que un user ha asistido en orden descendiente cronologicamente
	//		@Test
	//		public void driverFindCategoryByUserIdForRendezvousesAssits() {
	//			final Object testingData[][] = {
	//				{
	//					//El user 1 comprueba si asiste al category2 que aparece la primera en la lista al ser ordenada cronologicamente en orden descendiente
	//					"user1", "category2", null
	//				}, {
	//					//El user 1 comprueba si asiste category1 pero no aparece la primera en la lista al ser ordenada cronologicamente en orden descendiente
	//					"user1", "category1", IllegalArgumentException.class
	//				}
	//			};
	//			for (int i = 0; i < testingData.length; i++)
	//				this.templateFindCategoryByUserIdForRendezvousesAssits((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	//		}
	//		private void templateFindCategoryByUserIdForRendezvousesAssits(final String username, final int categoryId, final Class<?> expected) {
	//			final Collection<Category> listCategorys;
	//			Category category;
	//			Class<?> caught;
	//
	//			caught = null;
	//			try {
	//				super.authenticate(username);
	//				listCategorys = this.categoryService.findCategoryByUserIdForRendezvousesAssits();
	//				category = this.categoryService.findOne(categoryId);
	//				Assert.isTrue(listCategorys.iterator().next().equals(category));
	//			} catch (final Throwable oops) {
	//				caught = oops.getClass();
	//			}
	//
	//			this.checkExceptions(expected, caught);
	//			super.unauthenticate();
	//		}
	//	}

}
