
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
		//final Actor actorcreator = this.actorService.findPrincipal();
		final MessageFolder messagefolder;
		final List<Message> messages;

		messagefolder = new MessageFolder();
		messages = new ArrayList<>();
		messagefolder.setMessages(messages);
		//actorcreator.getMessagesFolders().add(messagefolder);, lo pongo en el save

		return messagefolder;
	}

	public MessageFolder findOne(final int mFid) {
		Assert.isTrue(mFid != 0);

		MessageFolder result;

		result = this.messageFolderRepository.findOne(mFid);
		Assert.notNull(result);
		return result;

	}

	public Collection<MessageFolder> findAll() {

		Collection<MessageFolder> messagefolder;
		messagefolder = this.messageFolderRepository.findAll();
		return messagefolder;
	}
	public MessageFolder save(final MessageFolder messageFolder) {
		final Actor actorPrincipal;
		MessageFolder res;

		actorPrincipal = this.actorService.findPrincipal();
		Assert.notNull(actorPrincipal);
		Assert.notNull(messageFolder);

		res = this.messageFolderRepository.save(messageFolder);
		//Se le añade al actor el messageFolder INSTRUMENTADO
		actorPrincipal.getMessagesFolders().add(res);
		Assert.notNull(res);

		return res;
	}
	public void delete(final MessageFolder messageFolder) {
		final Actor actorPrincipal;
		Assert.notNull(messageFolder);
		Assert.isTrue(messageFolder.isModifiable() == true);
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
		Collection<MessageFolder> res;
		final Collection<Message> messages;
		MessageFolder inbox;
		MessageFolder notificationbox;
		MessageFolder outbox;
		MessageFolder trashbox;
		MessageFolder spambox;

		res = new ArrayList<MessageFolder>();
		messages = new ArrayList<>();
		inbox = new MessageFolder();
		notificationbox = new MessageFolder();
		outbox = new MessageFolder();
		trashbox = new MessageFolder();
		spambox = new MessageFolder();

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

		inbox.setName("in box");
		outbox.setName("out box");
		notificationbox.setName("Notification box");
		trashbox.setName("trash box");
		spambox.setName("spam box");

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

		return res;

	}

	public MessageFolder returnDefaultFolder(final Actor actor, final String name) {
		MessageFolder res;

		res = null;
		Assert.isTrue(name.equals("in box") || name.equals("out box") || name.equals("Notification box") || name.equals("trash box") || name.equals("spam box"));
		for (final MessageFolder folder : actor.getMessagesFolders())
			if (folder.getName().equals(name)) {
				res = folder;
				break;
			}
		Assert.notNull(res);
		return res;
	}

}
