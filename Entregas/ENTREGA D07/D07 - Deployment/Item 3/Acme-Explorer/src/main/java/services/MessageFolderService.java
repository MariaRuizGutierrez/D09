
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MessageFolderRepository;
import domain.Actor;
import domain.Message;
import domain.MessageFolder;

@Service
@Transactional
public class MessageFolderService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private MessageFolderRepository	messageFolderRepository;
	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService			actorService;
	@Autowired
	private MessageService			messageService;


	// Constructors -----------------------------------------------------------
	public MessageFolderService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------
	public MessageFolder create() {

		final MessageFolder messagefolder;
		final List<Message> messages;

		messagefolder = new MessageFolder();
		messages = new ArrayList<Message>();
		messagefolder.setMessages(messages);

		return messagefolder;
	}
	public MessageFolder findOne(final int mFid) {
		Assert.isTrue(mFid != 0);

		MessageFolder result;

		result = this.messageFolderRepository.findOne(mFid);
		Assert.notNull(result);
		Assert.isTrue(this.actorService.findPrincipal().getMessagesFolders().contains(result));
		return result;

	}

	public Collection<MessageFolder> findAll() {

		Collection<MessageFolder> messagefolder;
		messagefolder = this.messageFolderRepository.findAll();
		return messagefolder;
	}
	public MessageFolder save(MessageFolder messageFolder) {
		Actor actor;
		MessageFolder res;
		Collection<MessageFolder> messageFoldersForActor;

		actor = this.actorService.findPrincipal();
		messageFoldersForActor = actor.getMessagesFolders();

		Assert.notNull(messageFolder);
		Assert.notNull(actor);

		for (MessageFolder m : messageFoldersForActor)
			Assert.isTrue(!m.getName().equals(messageFolder.getName()), "This name already exists");

		if (messageFolder.getId() != 0) {
			Assert.isTrue(this.actorService.findPrincipal().getMessagesFolders().contains(messageFolder));
			Assert.isTrue(messageFolder.isModifiable() == true, "This folder is not modifiable");
		}
		messageFolder.setModifiable(true);

		res = this.messageFolderRepository.save(messageFolder);

		if (messageFolder.getId() == 0)
			actor.getMessagesFolders().add(res);

		Assert.notNull(res);
		return res;

	}
	public void delete(final MessageFolder messageFolder) {
		final Actor actorPrincipal;
		Assert.notNull(messageFolder);
		Assert.isTrue(messageFolder.isModifiable() == true, "This folder is not modifiable");
		Assert.isTrue(messageFolder.getId() != 0);
		Assert.isTrue(this.messageFolderRepository.exists(messageFolder.getId()));

		actorPrincipal = this.actorService.findPrincipal();
		Assert.isTrue(actorPrincipal.getMessagesFolders().contains(messageFolder));
		//Se eliminan todos los messages de ese messageFolder
		for (final Message message : messageFolder.getMessages())
			this.messageService.delete(message);

		actorPrincipal.getMessagesFolders().remove(messageFolder);
		this.messageFolderRepository.delete(messageFolder);

	}
	// Other business methods -------------------------------------------------

	public Collection<MessageFolder> ActorFolders(int id) {
		Collection<MessageFolder> res;
		res = this.messageFolderRepository.ActorFolders(id);
		return res;

	}
	public Collection<MessageFolder> findAllByActorAutenticate() {
		Actor actor;
		actor = this.actorService.findPrincipal();
		final Collection<MessageFolder> messagesFolders = this.messageFolderRepository.ActorFolders(actor.getId());
		return messagesFolders;

	}

	public Collection<MessageFolder> createDefaultFolders() {
		Actor actor;
		actor = this.actorService.findPrincipal();
		MessageFolder inbox;
		MessageFolder outbox;
		MessageFolder trashbox;
		MessageFolder spambox;
		MessageFolder notificationbox;
		Collection<MessageFolder> res;
		Collection<Message> messages;

		res = new ArrayList<MessageFolder>();
		messages = new ArrayList<>();
		inbox = new MessageFolder();
		notificationbox = new MessageFolder();
		outbox = new MessageFolder();
		trashbox = new MessageFolder();
		spambox = new MessageFolder();

		inbox.setName("In box");
		outbox.setName("Out box");
		trashbox.setName("Trash box");
		spambox.setName("Spam box");
		notificationbox.setName("Notification box");

		inbox.setModifiable(false);
		outbox.setModifiable(false);
		notificationbox.setModifiable(false);
		trashbox.setModifiable(false);
		spambox.setModifiable(false);

		inbox.setMessages(messages);
		outbox.setMessages(messages);
		notificationbox.setMessages(messages);
		trashbox.setMessages(messages);
		spambox.setMessages(messages);

		inbox = this.messageFolderRepository.save(inbox);
		outbox = this.messageFolderRepository.save(outbox);
		notificationbox = this.messageFolderRepository.save(notificationbox);
		trashbox = this.messageFolderRepository.save(trashbox);
		spambox = this.messageFolderRepository.save(spambox);

		res.add(inbox);
		res.add(outbox);
		res.add(notificationbox);
		res.add(trashbox);
		res.add(spambox);

		actor.setMessagesFolders(res);

		return res;

	}
	public MessageFolder returnDefaultFolder(final Actor actor, final String name) {
		MessageFolder res;

		res = null;
		Assert.isTrue(name.equals("In box") || name.equals("Out box") || name.equals("Notification box") || name.equals("Trash box") || name.equals("Spam box"));
		for (final MessageFolder folder : actor.getMessagesFolders())
			if (folder.getName().equals(name)) {
				res = folder;
				break;
			}
		Assert.notNull(res);
		return res;
	}

	public Collection<MessageFolder> createDefaultFoldersForRegister(Actor actor) {

		MessageFolder inbox;
		MessageFolder outbox;
		MessageFolder trashbox;
		MessageFolder spambox;
		MessageFolder notificationbox;
		Collection<MessageFolder> res;
		Collection<Message> messages;

		res = new ArrayList<MessageFolder>();
		messages = new ArrayList<>();
		inbox = new MessageFolder();
		notificationbox = new MessageFolder();
		outbox = new MessageFolder();
		trashbox = new MessageFolder();
		spambox = new MessageFolder();

		inbox.setName("In box");
		outbox.setName("Out box");
		trashbox.setName("Trash box");
		spambox.setName("Spam box");
		notificationbox.setName("Notification box");

		inbox.setModifiable(false);
		outbox.setModifiable(false);
		notificationbox.setModifiable(false);
		trashbox.setModifiable(false);
		spambox.setModifiable(false);

		inbox.setMessages(messages);
		outbox.setMessages(messages);
		notificationbox.setMessages(messages);
		trashbox.setMessages(messages);
		spambox.setMessages(messages);

		inbox = this.messageFolderRepository.save(inbox);
		outbox = this.messageFolderRepository.save(outbox);
		notificationbox = this.messageFolderRepository.save(notificationbox);
		trashbox = this.messageFolderRepository.save(trashbox);
		spambox = this.messageFolderRepository.save(spambox);

		res.add(inbox);
		res.add(outbox);
		res.add(notificationbox);
		res.add(trashbox);
		res.add(spambox);

		actor.setMessagesFolders(res);

		return res;

	}

}
