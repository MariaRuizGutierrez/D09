
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
import domain.Actor;
import domain.Administrator;
import domain.ApplicationFor;
import domain.Message;
import domain.MessageFolder;

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

	@Autowired
	private ApplicationForService	applicationForService;

	@Autowired
	private ActorService			actorService;


	@Test
	public void testCreate() {
		this.authenticate("admin");
		Message message;
		message = this.messageService.create();
		Assert.notNull(message);
		this.unauthenticate();
	}

	@Test
	public void testFindOneAndFindAllPositive() {
		this.authenticate("auditor2");
		Collection<Message> messages;
		Message messageFind;

		messages = this.messageService.findAll();
		messageFind = this.messageService.findOne(this.getEntityId("message2"));

		Assert.notNull(messages);
		Assert.notNull(messageFind);
		this.authenticate(null);

	}
	@Test
	public void testSave() {
		super.authenticate("admin");

		Administrator adminSend;
		Administrator adminRecip;
		Message message;

		adminSend = this.administratorService.findByPrincipal();
		adminRecip = this.administratorService.findOne(super.getEntityId("administrator2"));

		//Creo el mensaje y lo guardo
		message = this.messageService.create();
		message.setBody("hola caracola");
		message.setRecipient(adminRecip);
		message.setSender(adminSend);
		message.setPriority("NEUTRAL");
		message.setSubject("hola");

		message = this.messageService.save(message);

		super.unauthenticate();
	}

	@Test
	public void testChangeFolderMessage() {
		this.authenticate("administrator2");
		Message mes;
		Message messageAfterChangeFolder;
		MessageFolder messageFolder;
		messageFolder = this.messageFolderService.findOne(this.getEntityId("TrashBoxAdministrator2"));

		mes = this.messageService.findOne(this.getEntityId("message1"));

		this.messageService.ChangeMessageOfFolder(mes, messageFolder);

		messageAfterChangeFolder = this.messageService.findOne(this.getEntityId("message1"));

		Assert.isTrue(messageAfterChangeFolder.getMessageFolder().equals(messageFolder));

	}

	@Test
	public void testSaveMessageWithWordSpam() {
		super.authenticate("admin");

		Administrator adminSend;
		Administrator adminRecip;
		Message message;
		Integer numberOfMessageInSpamBeforeSave;
		Integer numberOfMessageInSpamAfterSave;

		adminSend = this.administratorService.findByPrincipal();
		adminRecip = this.administratorService.findOne(super.getEntityId("administrator2"));

		//Creo el mensaje y lo guardo
		message = this.messageService.create();
		message.setBody("viagra");
		message.setRecipient(adminRecip);
		message.setSender(adminSend);
		message.setPriority("NEUTRAL");
		message.setSubject("hola");

		numberOfMessageInSpamBeforeSave = this.messageService.messagesOfFolder(this.messageFolderService.returnDefaultFolder(adminRecip, "Spam box").getId()).size();
		message = this.messageService.save(message);
		numberOfMessageInSpamAfterSave = this.messageService.messagesOfFolder(this.messageFolderService.returnDefaultFolder(adminRecip, "Spam box").getId()).size();

		Assert.isTrue(numberOfMessageInSpamBeforeSave < numberOfMessageInSpamAfterSave);
		super.unauthenticate();
	}

	@Test
	public void testApplicationForChanged() {
		this.authenticate("admin");
		ApplicationFor applicationFor;
		MessageFolder messagesOfNotificationBoxBeforeSendMessage;
		MessageFolder messagesOfNotificationBoxAfterSendMessage;

		Integer beforeSend;
		Integer afterSend;

		applicationFor = this.applicationForService.findOne(this.getEntityId("applicationFor3"));
		messagesOfNotificationBoxBeforeSendMessage = this.messageFolderService.returnDefaultFolder(applicationFor.getExplorer(), "Notification box");
		beforeSend = this.messageService.messagesOfFolder(messagesOfNotificationBoxBeforeSendMessage.getId()).size();

		this.messageService.sendMessageToActorOfApplicationFor(applicationFor);
		messagesOfNotificationBoxAfterSendMessage = this.messageFolderService.returnDefaultFolder(applicationFor.getExplorer(), "Notification box");
		afterSend = this.messageService.messagesOfFolder(messagesOfNotificationBoxAfterSendMessage.getId()).size();
		Assert.isTrue(beforeSend + 1 == afterSend);

		this.unauthenticate();
	}
	@Test
	public void testSendNotificationToAllActors() {
		this.authenticate("admin");

		MessageFolder notificationBoxOfSomeActor;
		Collection<Message> messageOfNotificationBoxOfSomeActor;
		Integer contador;
		Actor someActor;
		String subject;
		String body;
		String priority;

		subject = "Notification";
		body = "Esto es una notificacion de prueba";
		priority = "NEUTRAL";
		this.messageService.sendNotificationBroadcast(subject, body, priority);
		someActor = this.actorService.findOne(this.getEntityId("explorer1"));
		notificationBoxOfSomeActor = this.messageFolderService.returnDefaultFolder(someActor, "Notification box");
		messageOfNotificationBoxOfSomeActor = this.messageService.messagesOfFolder(notificationBoxOfSomeActor.getId());

		contador = 0;
		for (Message m : messageOfNotificationBoxOfSomeActor)
			if (m.getSubject().equals("Notification"))
				contador = contador + 1;
		Assert.isTrue(contador == 1);

	}

	@Test
	public void testRecipientAllByActor() {
		this.authenticate("administrator2");
		Actor administrator2;
		administrator2 = this.actorService.findPrincipal();

		Collection<Message> messagesRecipientAllByActor;
		messagesRecipientAllByActor = this.messageService.recipientAllByActor(administrator2.getId());
		Assert.notEmpty(messagesRecipientAllByActor);
	}

	@Test
	public void testSenderAllByActor() {
		this.authenticate("admin");
		Actor administrator1;
		administrator1 = this.actorService.findPrincipal();

		Collection<Message> messagesRecipientAllByActor;
		messagesRecipientAllByActor = this.messageService.senderAllByActor(administrator1.getId());
		Assert.notEmpty(messagesRecipientAllByActor);
	}

	@Test
	public void testDeleteOfOutBoxAndDeleteOfBD() {
		this.authenticate("admin");
		Integer idMessage;
		Message message;

		message = this.messageService.findOne(this.getEntityId("message1Copy"));
		idMessage = message.getId();

		this.messageService.delete(message);

		Assert.notNull(this.messageService.findOne(message.getId()));

		this.messageService.delete(this.messageService.findOne(idMessage));

	}
}
