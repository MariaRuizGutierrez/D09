
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import services.ServiceOfferedService;
import controllers.AbstractController;

@Controller
@RequestMapping("/serviceOffered/administrator")
public class ServiceOfferedAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private ServiceOfferedService	serviceOfferedService;
}
