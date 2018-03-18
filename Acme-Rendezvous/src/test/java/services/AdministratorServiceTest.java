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
import domain.Administrator;
import domain.Announcement;
import domain.Comment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AdministratorServiceTest extends AbstractTest{
	
	// Supporting services ----------------------------------------------------
	
	@Autowired
	private AdministratorService	administratorService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private RendezvouseService rendezvousService;
	
	@Autowired
	private AnnouncementService	announcementService;
	
	@PersistenceContext
	EntityManager				entityManager;
	
	@Test
	public void driveLoginAdministrator(){
		
		Object testingData[][] = {
			//admin está registrado
			{"admin", null},
			{"adminNoRegistrado", IllegalArgumentException.class}
		};
		
		for(int i = 0; i < testingData.length; i++){
			this.templateLoginAdminsitrator((String) testingData[i][0], (Class<?>) testingData[i][1]);
		}
		
	}
	
	public void templateLoginAdminsitrator(final String username, final Class<?> expected){
		
		Class<?> caught;
		
		caught = null;
		
		try{
			authenticate(username);	
			this.unauthenticate();
		}catch(Throwable oops){
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}
		
		checkExceptions(expected, caught);
	}
	
	@Test
	public void driveEditNameAdministrator(){
		
		Object testingData[][] = {
			//admin está registrado
			{"administrator", "admin", "adminPrueba", "surnamePrueba", null, null, "prueba@gmail.com", null},
			{"administrator", "admin", "adminPrueba", "surnamePrueba", "c/prueba", null, "prueba@gmail.com", null},
			{"administrator", "admin", "adminPrueba", "surnamePrueba", "c/prueba", "+34657896576", "prueba@gmail.com", null},
			{"administrator", "admin", "", "surnamePrueba", null, null, "prueba@gmail.com", javax.validation.ConstraintViolationException.class},
			{"administrator", "admin", null, "surnamePrueba", null, null, "prueba@gmail.com", javax.validation.ConstraintViolationException.class},
			{"administrator", "admin", "adminPrueba", "surnamePrueba", null, null, "prueba@gmail.com", null},
			{"administrator", "admin", "adminPrueba", "", null, null, "prueba@gmail.com", javax.validation.ConstraintViolationException.class},
			{"administrator", "admin", "adminPrueba", null, null, null, "prueba@gmail.com", javax.validation.ConstraintViolationException.class},
			{"administrator", "admin", "adminPrueba", null, null, null, "pruebagmail.com", javax.validation.ConstraintViolationException.class}
		};
		
		for(int i = 0; i < testingData.length; i++){ 
			this.templateEditAdministrator((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], 
				(String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(Class<?>) testingData[i][7]);
		}
		
	}
	
	public void templateEditAdministrator(String entity ,String username, String name, String surname, String postalAddress, 
		String phoneNumber, String emailAddress, Class<?> expected){
		
		Class<?> caught;
		Administrator admin;
		
		caught = null;
		admin = this.administratorService.findOne(super.getEntityId(entity));
		
		
		
		try{
			super.authenticate(username);
			admin.setName(name);
			admin.setSurname(surname);
			admin.setPostalAddress(postalAddress);
			admin.setPhoneNumber(phoneNumber);
			admin.setEmailAddress(emailAddress);
			this.administratorService.save(admin);
			this.unauthenticate();
			this.administratorService.flush();
		}catch (Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}
		
		checkExceptions(expected, caught);
		
	}
	
	@Test
	public void driveRemoveCommentAdministrator(){
		
		Object testingData[][] = {
			//admin está registrado
			{"admin", "comment5", null},
			{"user1", "comment5", IllegalArgumentException.class}
		};
		
		for(int i = 0; i < testingData.length; i++){ 
			this.templateRemoveCommentAdministrator((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		}
		
	}
	
	public void templateRemoveCommentAdministrator(String username, String commentId, Class<?> expected){
		
		Class<?> caught;
		Comment comment;
		
		caught = null;
		comment = this.commentService.findOne(super.getEntityId(commentId));
		
		
		
		try{
			super.authenticate(username);
			this.commentService.delete(comment);
			this.unauthenticate();
			this.commentService.flush();
		}catch (Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}
		
		checkExceptions(expected, caught);
		
	}
	
//	@Test
//	public void driveRemoveRendezvousAdministrator(){
//		
//		Object testingData[][] = {
//			//admin está registrado
//			{"admin", "rendezvouse1", null},
//			{"user2", "rendezvouse4", IllegalArgumentException.class}
//		};
//		
//		for(int i = 0; i < testingData.length; i++){ 
//			this.templateRemoveRendezvousAdministrator((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
//		}
//		
//	}
//	
//	public void templateRemoveRendezvousAdministrator(String username, String rendezvousId, Class<?> expected){
//		
//		Class<?> caught;
//		Rendezvouse rendezvous;
//		
//		
//		caught = null;
//		rendezvous = this.rendezvousService.findOne(super.getEntityId(rendezvousId));
//		
//		
//		
//		try{
//			super.authenticate(username);
//			this.rendezvousService.delete(rendezvous);
//			this.unauthenticate();
//			this.rendezvousService.flush();
//		}catch (Throwable oops) {
//			caught = oops.getClass();
//			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
//			this.entityManager.clear();
//		}
//		
//		checkExceptions(expected, caught);
//		
//	}

	
	@Test
	public void driveRemoveAnnouncementAdministrator(){
		
		Object testingData[][] = {
			//admin está registrado
			{"admin", "announcement3", null},
			{"user1", "announcement3", IllegalArgumentException.class}
		};
		
		for(int i = 0; i < testingData.length; i++){ 
			this.templateRemoveAnnouncementAdministrator((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		}
		
	}
	
	public void templateRemoveAnnouncementAdministrator(String username, String aunocementId, Class<?> expected){
		
		Class<?> caught;
		Announcement announcement;
		
		caught = null;
		announcement = this.announcementService.findOne(super.getEntityId(aunocementId));
		
		
		
		try{
			super.authenticate(username);
			this.announcementService.delete(announcement);
			this.unauthenticate();
			this.announcementService.flush();
		}catch (Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}
		
		checkExceptions(expected, caught);
		
	}
}
