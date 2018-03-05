
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Actor;
import domain.Administrator;
import domain.ApplicationFor;
import domain.Explorer;
import domain.Manager;
import domain.Message;
import domain.MessageFolder;

@Service
@Transactional
public class MessageService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private MessageRepository			messageRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService				actorService;

	@Autowired
	private MessageFolderService		messageFolderService;

	@Autowired
	private ConfigurationSystemService	configurationSystemService;

	@Autowired
	private AdministratorService		administratorService;


	// Constructors -----------------------------------------------------------
	public MessageService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Message create() {
		final Actor sender = this.actorService.findPrincipal();
		Message message;
		message = new Message();
		message.setMoment(new Date());
		message.setSender(sender);
		message.setMessageFolder(this.messageFolderService.returnDefaultFolder(sender, "Out box"));
		return message;
	}
	public Message findOne(final int messageId) {
		Assert.isTrue(messageId != 0);

		Message result;
		Actor actor;
		Actor actorMessageSender;
		Actor actorMessageRecipient;
		Message messageFind;
		Collection<MessageFolder> messageFolders;

		messageFind = this.messageRepository.findOne(messageId);
		actor = this.actorService.findPrincipal();
		actorMessageSender = messageFind.getSender();
		actorMessageRecipient = messageFind.getRecipient();
		messageFolders = actor.getMessagesFolders();

		Assert.isTrue((actor == actorMessageRecipient || actor == actorMessageSender) && messageFolders.contains(messageFind.getMessageFolder()));

		result = this.messageRepository.findOne(messageId);
		Assert.notNull(result);

		return result;
	}
	public Collection<Message> findAll() {
		Collection<Message> result;
		result = this.messageRepository.findAll();
		return result;

	}

	public Message save(Message message) {
		Assert.notNull(message.getSender());
		Assert.notNull(message.getRecipient());
		Assert.notNull(message);

		Message messageBD;
		Message messageNew;
		Date current;

		messageNew = null;

		if (message.getId() != 0) {
			Assert.notNull(message.getMessageFolder());
			messageBD = this.messageRepository.findOne(message.getId());

			if (message.getMessageFolder() != messageBD.getMessageFolder()) {
				messageNew = this.messageRepository.save(message);
				Assert.notNull(messageNew);
				message = messageNew;

			}
		}
		if (message.getId() == 0) {
			current = new Date(System.currentTimeMillis() - 1000);
			message.setMoment(current);
			Actor sender;
			Actor recipient;

			sender = message.getSender();
			recipient = message.getRecipient();

			MessageFolder messageFolderInboxOfRecipient;
			MessageFolder messageFolderOutboxOfSender;
			MessageFolder messageFolderSpamOfRecipient;

			Message messageNewInbox;
			Message messageNewOutbox;
			Message messageNewSpambox;

			messageFolderInboxOfRecipient = this.messageFolderService.returnDefaultFolder(recipient, "In box");
			messageFolderOutboxOfSender = this.messageFolderService.returnDefaultFolder(sender, "Out box");
			messageFolderSpamOfRecipient = this.messageFolderService.returnDefaultFolder(recipient, "Spam box");

			if (this.MessageisSpam(message)) {

				message.setMessageFolder(messageFolderSpamOfRecipient);
				messageNewSpambox = this.messageRepository.save(message);

				Assert.notNull(messageNewSpambox);
				messageFolderSpamOfRecipient.getMessages().add(messageNewSpambox);

			} else {
				message.setMessageFolder(messageFolderInboxOfRecipient);
				messageNewInbox = this.messageRepository.save(message);

				Assert.notNull(messageNewInbox);
				messageFolderInboxOfRecipient.getMessages().add(messageNewInbox);
			}

			message.setMessageFolder(messageFolderOutboxOfSender);
			messageNewOutbox = this.messageRepository.save(message);

			Assert.notNull(messageNewOutbox);
			messageFolderOutboxOfSender.getMessages().add(messageNewOutbox);

			message = messageNewOutbox;
		}
		return message;

	}
	public void delete(final Message message) {
		Assert.notNull(message);
		Assert.isTrue(message.getId() != 0);
		Actor actorPrincipal;
		actorPrincipal = this.actorService.findPrincipal();
		Assert.isTrue(actorPrincipal.getMessagesFolders().contains(message.getMessageFolder()));

		if (message.getMessageFolder().getName().equals("Trash box"))
			this.messageRepository.delete(message);
		else {
			Message messageToTrash;
			MessageFolder trashActor;

			trashActor = this.messageFolderService.returnDefaultFolder(actorPrincipal, "Trash box");
			messageToTrash = message;
			messageToTrash.setMessageFolder(trashActor);
			this.messageRepository.save(messageToTrash);
		}
	}
	// Other business methods -------------------------------------------------
	public boolean MessageisSpam(final Message message) {
		//Comprueba si un mensaje es spam
		boolean bool;
		final Collection<String> words;
		Collection<String> spams;

		bool = false;
		words = new ArrayList<String>();
		words.add(message.getBody().toLowerCase());
		words.add(message.getSubject().toLowerCase());

		spams = this.SpamWord();

		for (final String w : words)
			for (final String spam : spams) {
				final int i = w.indexOf(spam);
				if (i != -1)
					bool = true;
			}

		return bool;

	}
	public Collection<String> SpamWord() {
		//devuelve la collecion de palabras spam
		Collection<String> res;
		res = this.configurationSystemService.spamWord();
		return res;

	}
	public Collection<Message> recipientAllByActor(final int idActor) {
		//Todos los mensajes recibidos de un actor
		Collection<Message> res;
		res = this.messageRepository.recipientAllByActor(idActor);
		return res;

	}
	public Collection<Message> senderAllByActor(final int idActor) {
		//Todos los mensajes enviados de un actor
		Collection<Message> res;
		res = this.messageRepository.senderAllByActor(idActor);
		return res;

	}

	public void ChangeMessageOfFolder(final Message message, final MessageFolder folder) {

		if (!message.getMessageFolder().equals(folder))
			message.setMessageFolder(folder);
	}

	public String recivemessage(final Message message) {
		//Comprueba si el mensaje es spam y devuelve el nombre de la carpeta a la que debe ir
		String folderName = "in box";
		if (this.MessageisSpam(message) == true)
			folderName = "spam box";

		return folderName;

	}

	public Collection<Message> messagesOfFolder(int messageFolderId) {
		Collection<Message> messages;

		messages = this.messageRepository.messagesOfFolder(messageFolderId);
		return messages;
	}

	public void sendMessageToActorOfApplicationFor(ApplicationFor applicationFor) {

		switch (applicationFor.getStatus()) {
		case "PENDING":
			this.messageForNotificationToStatusPending(applicationFor);
			break;
		case "REJECTED":
			this.messageForNotificationToStatusRejected(applicationFor);
			break;
		case "DUE":
			this.messageForNotificationToStatusDue(applicationFor);
			break;
		case "ACCEPTED":
			this.messageForNotificationToStatusAccepted(applicationFor);
			break;
		case "CANCELLED":
			this.messageForNotificationToStatusCancelled(applicationFor);
			break;
		}

	}
	public void messageForNotificationToStatusPending(ApplicationFor applicationFor) {

		Message messageForExplorer;
		Message messageForManager;
		Message messageBdOfExplorer;
		Message messageBdOfManager;
		Administrator administratorSender;

		Explorer explorerOfApplicationFor;
		Manager managerOfApplicationFor;

		explorerOfApplicationFor = applicationFor.getExplorer();
		managerOfApplicationFor = applicationFor.getManager();

		MessageFolder notificationBoxOfExplorer;
		MessageFolder notificationBoxOfManager;

		notificationBoxOfExplorer = this.messageFolderService.returnDefaultFolder(explorerOfApplicationFor, "Notification box");
		notificationBoxOfManager = this.messageFolderService.returnDefaultFolder(managerOfApplicationFor, "Notification box");
		messageForExplorer = new Message();
		messageForManager = new Message();
		administratorSender = this.administratorService.findAll().iterator().next();

		messageForExplorer.setSender(administratorSender);
		messageForExplorer.setSubject("Status changed");
		messageForExplorer.setBody("The status for application for " + applicationFor.getId() + " is change to pending status");
		messageForExplorer.setPriority("HIGH");
		messageForExplorer.setMessageFolder(notificationBoxOfExplorer);
		Date current;
		current = new Date(System.currentTimeMillis() - 1000);
		messageForExplorer.setMoment(current);
		messageForExplorer.setRecipient(explorerOfApplicationFor);

		messageBdOfExplorer = this.messageRepository.save(messageForExplorer);

		messageForManager.setSender(administratorSender);
		messageForManager.setSubject("Status changed");
		messageForManager.setBody("The status for application for " + applicationFor.getId() + " is change to pending status");
		messageForManager.setPriority("HIGH");
		messageForManager.setMessageFolder(notificationBoxOfManager);
		Date current2;
		current2 = new Date(System.currentTimeMillis() - 1000);
		messageForManager.setMoment(current2);
		messageForManager.setRecipient(managerOfApplicationFor);

		messageBdOfManager = this.messageRepository.save(messageForManager);

		Assert.notNull(messageBdOfExplorer);
		Assert.notNull(messageBdOfManager);
	}
	public void messageForNotificationToStatusAccepted(ApplicationFor applicationFor) {

		Message messageForExplorer;
		Message messageForManager;
		Message messageBdOfExplorer;
		Message messageBdOfManager;
		Administrator administratorSender;

		Explorer explorerOfApplicationFor;
		Manager managerOfApplicationFor;

		explorerOfApplicationFor = applicationFor.getExplorer();
		managerOfApplicationFor = applicationFor.getManager();

		MessageFolder notificationBoxOfExplorer;
		MessageFolder notificationBoxOfManager;

		notificationBoxOfExplorer = this.messageFolderService.returnDefaultFolder(explorerOfApplicationFor, "Notification box");
		notificationBoxOfManager = this.messageFolderService.returnDefaultFolder(managerOfApplicationFor, "Notification box");
		messageForExplorer = new Message();
		messageForManager = new Message();
		administratorSender = this.administratorService.findAll().iterator().next();

		messageForExplorer.setSender(administratorSender);
		messageForExplorer.setSubject("Status changed");
		messageForExplorer.setBody("The status for application for " + applicationFor.getId() + " is change to accepted status");
		messageForExplorer.setPriority("HIGH");
		messageForExplorer.setMessageFolder(notificationBoxOfExplorer);
		Date current;
		current = new Date(System.currentTimeMillis() - 1000);
		messageForExplorer.setMoment(current);
		messageForExplorer.setRecipient(explorerOfApplicationFor);

		messageBdOfExplorer = this.messageRepository.save(messageForExplorer);

		messageForManager.setSender(administratorSender);
		messageForManager.setSubject("Status changed");
		messageForManager.setBody("The status for application for " + applicationFor.getId() + " is change to accepted status");
		messageForManager.setPriority("HIGH");
		messageForManager.setMessageFolder(notificationBoxOfManager);
		Date current2;
		current2 = new Date(System.currentTimeMillis() - 1000);
		messageForManager.setMoment(current2);
		messageForManager.setRecipient(managerOfApplicationFor);

		messageBdOfManager = this.messageRepository.save(messageForManager);

		Assert.notNull(messageBdOfExplorer);
		Assert.notNull(messageBdOfManager);
	}

	public void messageForNotificationToStatusRejected(ApplicationFor applicationFor) {

		Message messageForExplorer;
		Message messageForManager;
		Message messageBdOfExplorer;
		Message messageBdOfManager;
		Administrator administratorSender;

		Explorer explorerOfApplicationFor;
		Manager managerOfApplicationFor;

		explorerOfApplicationFor = applicationFor.getExplorer();
		managerOfApplicationFor = applicationFor.getManager();

		MessageFolder notificationBoxOfExplorer;
		MessageFolder notificationBoxOfManager;

		notificationBoxOfExplorer = this.messageFolderService.returnDefaultFolder(explorerOfApplicationFor, "Notification box");
		notificationBoxOfManager = this.messageFolderService.returnDefaultFolder(managerOfApplicationFor, "Notification box");
		messageForExplorer = new Message();
		messageForManager = new Message();
		administratorSender = this.administratorService.findAll().iterator().next();

		messageForExplorer.setSender(administratorSender);
		messageForExplorer.setSubject("Status changed");
		messageForExplorer.setBody("The status for application for " + applicationFor.getId() + " is change to rejected status");
		messageForExplorer.setPriority("HIGH");
		messageForExplorer.setMessageFolder(notificationBoxOfExplorer);
		Date current;
		current = new Date(System.currentTimeMillis() - 1000);
		messageForExplorer.setMoment(current);
		messageForExplorer.setRecipient(explorerOfApplicationFor);

		messageBdOfExplorer = this.messageRepository.save(messageForExplorer);

		messageForManager.setSender(administratorSender);
		messageForManager.setSubject("Status changed");
		messageForManager.setBody("The status for application for " + applicationFor.getId() + " is change to rejected status");
		messageForManager.setPriority("HIGH");
		messageForManager.setMessageFolder(notificationBoxOfManager);
		Date current2;
		current2 = new Date(System.currentTimeMillis() - 1000);
		messageForManager.setMoment(current2);
		messageForManager.setRecipient(managerOfApplicationFor);

		messageBdOfManager = this.messageRepository.save(messageForManager);

		Assert.notNull(messageBdOfExplorer);
		Assert.notNull(messageBdOfManager);
	}

	public void messageForNotificationToStatusDue(ApplicationFor applicationFor) {

		Message messageForExplorer;
		Message messageForManager;
		Message messageBdOfExplorer;
		Message messageBdOfManager;
		Administrator administratorSender;

		Explorer explorerOfApplicationFor;
		Manager managerOfApplicationFor;

		explorerOfApplicationFor = applicationFor.getExplorer();
		managerOfApplicationFor = applicationFor.getManager();

		MessageFolder notificationBoxOfExplorer;
		MessageFolder notificationBoxOfManager;

		notificationBoxOfExplorer = this.messageFolderService.returnDefaultFolder(explorerOfApplicationFor, "Notification box");
		notificationBoxOfManager = this.messageFolderService.returnDefaultFolder(managerOfApplicationFor, "Notification box");
		messageForExplorer = new Message();
		messageForManager = new Message();
		administratorSender = this.administratorService.findAll().iterator().next();

		messageForExplorer.setSender(administratorSender);
		messageForExplorer.setSubject("Status changed");
		messageForExplorer.setBody("The status for application for " + applicationFor.getId() + " is change to due status");
		messageForExplorer.setPriority("HIGH");
		messageForExplorer.setMessageFolder(notificationBoxOfExplorer);
		Date current;
		current = new Date(System.currentTimeMillis() - 1000);
		messageForExplorer.setMoment(current);
		messageForExplorer.setRecipient(explorerOfApplicationFor);

		messageBdOfExplorer = this.messageRepository.save(messageForExplorer);

		messageForManager.setSender(administratorSender);
		messageForManager.setSubject("Status changed");
		messageForManager.setBody("The status for application for " + applicationFor.getId() + " is change to due status");
		messageForManager.setPriority("HIGH");
		messageForManager.setMessageFolder(notificationBoxOfManager);
		Date current2;
		current2 = new Date(System.currentTimeMillis() - 1000);
		messageForManager.setMoment(current2);
		messageForManager.setRecipient(managerOfApplicationFor);

		messageBdOfManager = this.messageRepository.save(messageForManager);

		Assert.notNull(messageBdOfExplorer);
		Assert.notNull(messageBdOfManager);
	}

	public void messageForNotificationToStatusCancelled(ApplicationFor applicationFor) {

		Message messageForExplorer;
		Message messageForManager;
		Message messageBdOfExplorer;
		Message messageBdOfManager;
		Administrator administratorSender;

		Explorer explorerOfApplicationFor;
		Manager managerOfApplicationFor;

		explorerOfApplicationFor = applicationFor.getExplorer();
		managerOfApplicationFor = applicationFor.getManager();

		MessageFolder notificationBoxOfExplorer;
		MessageFolder notificationBoxOfManager;

		notificationBoxOfExplorer = this.messageFolderService.returnDefaultFolder(explorerOfApplicationFor, "Notification box");
		notificationBoxOfManager = this.messageFolderService.returnDefaultFolder(managerOfApplicationFor, "Notification box");
		messageForExplorer = new Message();
		messageForManager = new Message();
		administratorSender = this.administratorService.findAll().iterator().next();

		messageForExplorer.setSender(administratorSender);
		messageForExplorer.setSubject("Status changed");
		messageForExplorer.setBody("The status for application for " + applicationFor.getId() + " is change to cancelled status");
		messageForExplorer.setPriority("HIGH");
		messageForExplorer.setMessageFolder(notificationBoxOfExplorer);
		Date current;
		current = new Date(System.currentTimeMillis() - 1000);
		messageForExplorer.setMoment(current);
		messageForExplorer.setRecipient(explorerOfApplicationFor);

		messageBdOfExplorer = this.messageRepository.save(messageForExplorer);

		messageForManager.setSender(administratorSender);
		messageForManager.setSubject("Status changed");
		messageForManager.setBody("The status for application for " + applicationFor.getId() + " is change to cancelled status");
		messageForManager.setPriority("HIGH");
		messageForManager.setMessageFolder(notificationBoxOfManager);
		Date current2;
		current2 = new Date(System.currentTimeMillis() - 1000);
		messageForManager.setMoment(current2);
		messageForManager.setRecipient(managerOfApplicationFor);

		messageBdOfManager = this.messageRepository.save(messageForManager);

		Assert.notNull(messageBdOfExplorer);
		Assert.notNull(messageBdOfManager);
	}

	public void sendNotificationBroadcast(String subject, String body, String priority) {
		Assert.notNull(priority);
		Collection<Actor> allActors;
		Actor sender;

		sender = this.actorService.findPrincipal();
		allActors = this.actorService.findAll();
		allActors.remove(sender);

		for (Actor recipient : allActors) {
			Message messageToActor;
			Date current;
			Message messageNewInbox;
			Message messageNewOutbox;
			MessageFolder messageFolderInboxOfRecipient;
			MessageFolder messageFolderOutboxOfSender;

			messageToActor = new Message();
			current = new Date(System.currentTimeMillis() - 1000);

			messageToActor.setSubject(subject);
			messageToActor.setBody(body);
			messageToActor.setPriority(priority);
			messageToActor.setMoment(current);
			messageToActor.setSender(sender);
			messageToActor.setRecipient(recipient);

			messageFolderInboxOfRecipient = this.messageFolderService.returnDefaultFolder(recipient, "Notification box");
			messageFolderOutboxOfSender = this.messageFolderService.returnDefaultFolder(sender, "Out box");

			messageToActor.setMessageFolder(messageFolderInboxOfRecipient);
			messageNewInbox = this.messageRepository.save(messageToActor);

			Assert.notNull(messageNewInbox);
			messageFolderInboxOfRecipient.getMessages().add(messageNewInbox);

			messageToActor.setMessageFolder(messageFolderOutboxOfSender);
			messageNewOutbox = this.messageRepository.save(messageToActor);

			Assert.notNull(messageNewOutbox);
			messageFolderOutboxOfSender.getMessages().add(messageNewOutbox);

		}

	}
}
