
package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.MessageFolderService;
import controllers.AbstractController;
import domain.Message;
import domain.MessageFolder;

@Controller
@RequestMapping("/messageFolder/administrator")
public class MessageFolderAdministratorController extends AbstractController {

	//	Services --------------------------------------------------------

	@Autowired
	private MessageFolderService	messageFolderService;


	//	Constructors

	public MessageFolderAdministratorController() {
		super();
	}

	//	Listing ---------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<MessageFolder> messageFolders;

		messageFolders = this.messageFolderService.findAllByActorAutenticate();

		result = new ModelAndView("messageFolder/list");
		result.addObject("messageFolders", messageFolders);
		result.addObject("requestURI", "messageFolder/administrator/list.do");
		result.addObject("modificable", true);

		return result;
	}
	// Creation-------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		MessageFolder messageFolder;

		messageFolder = this.messageFolderService.create();
		result = this.createEditModelAndView(messageFolder);

		return result;

	}

	// Edit ----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int messageFolderId) {
		ModelAndView result;
		MessageFolder messageFolder;

		messageFolder = this.messageFolderService.findOne(messageFolderId);
		Assert.notNull(messageFolder);
		result = this.createEditModelAndView(messageFolder);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid MessageFolder messageFolder, BindingResult bindingResult) {
		ModelAndView result;

		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(messageFolder);
		else
			try {
				this.messageFolderService.save(messageFolder);
				result = new ModelAndView("redirect:/messageFolder/administrator/list.do");
			} catch (final Throwable oops) {

				if (oops.getMessage().equals("This folder is not modifiable"))
					result = this.createEditModelAndView(messageFolder, "messageFolder.commit.error.notModifiable");
				else
					result = this.createEditModelAndView(messageFolder, "messageFolder.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(MessageFolder messageFolder, BindingResult bindingResult) {
		ModelAndView result;

		try {
			this.messageFolderService.delete(messageFolder);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(messageFolder, "messageFolder.commit.error");
		}

		return result;
	}

	// Display ----------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int messageFolderId) {
		ModelAndView result;
		MessageFolder messageFolder;

		messageFolder = this.messageFolderService.findOne(messageFolderId);
		result = new ModelAndView("messageFolder/display");
		result.addObject("messageFolder", messageFolder);

		return result;
	}
	// Ancillary methods ------------------------------------------------------
	protected ModelAndView createEditModelAndView(MessageFolder messageFolder) {
		ModelAndView result;
		result = this.createEditModelAndView(messageFolder, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(MessageFolder messageFolder, String messageCode) {
		ModelAndView result;
		Collection<Message> mess;

		mess = messageFolder.getMessages();

		result = new ModelAndView("messageFolder/edit");
		result.addObject("messageFolder", messageFolder);
		result.addObject("mes", mess);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "messageFolder/administrator/edit.do");

		return result;

	}

}
