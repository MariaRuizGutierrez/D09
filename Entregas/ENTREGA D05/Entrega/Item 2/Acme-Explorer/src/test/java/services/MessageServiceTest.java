
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Administrator;
import domain.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class MessageServiceTest extends AbstractTest {

	//Service under test----------------------------------------------------------
	@Autowired
	private MessageService			messageService;
	// Supporting services ----------------------------------------------------
	@Autowired
	private MessageFolderService	messageFolderService;
	@Autowired
	private AdministratorService	administratorService;


	@Test
	public void testCreate() {
		this.authenticate("administrator1");
		Message message;
		message = this.messageService.create();
		Assert.notNull(message);
		this.unauthenticate();
	}

	@Test
	public void testFindOneAndFindAllPositive() {
		Collection<Message> messages;
		Message message;
		int id;
		messages = this.messageService.findAll();
		id = messages.iterator().next().getId();
		message = this.messageService.findOne(id);
		Assert.notNull(message);

	}

	@Test
	public void testSave() {
		super.authenticate("administrator1");
		Administrator adminSend;
		Administrator adminRecip;
		Message message;
		//Añado las carpetas por defecto a los dos administradores
		adminSend = this.administratorService.findByPrincipal();
		adminRecip = this.administratorService.findOne(super.getEntityId("administrator2"));
		adminSend.getMessagesFolders().addAll(this.messageFolderService.createDefaultFolders());
		adminRecip.getMessagesFolders().addAll(this.messageFolderService.createDefaultFolders());
		adminSend = this.administratorService.save(adminSend);
		adminRecip = this.administratorService.save(adminRecip);
		//Creo el mensaje y lo guardo
		message = this.messageService.create();
		message.setBody("hola caracola");
		message.setRecipient(adminRecip);
		message.setPriority("NEUTRAL");
		message.setSubject("hola");
		message = this.messageService.save(message);

		super.unauthenticate();
	}

	@Test
	public void testDelete() {
		super.authenticate("administrator1");
		Administrator adminPrincipal;
		Message message;

		adminPrincipal = this.administratorService.findByPrincipal();
		message = adminPrincipal.getMessagesFolders().iterator().next().getMessages().iterator().next();
		Assert.notNull(message);
		this.messageService.delete(message);

		super.unauthenticate();
	}
}
