
package controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import utilities.AbstractTest;
import controllers.user.RendezvousesUserController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@WebAppConfiguration
@Transactional
public class RendezvousControllerTest extends AbstractTest {

	private MockMvc					mockMvc;

	@Autowired
	private WebApplicationContext	ctx;


	@Override
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.ctx).build();
	}

	// Listing ---------------------------------------------------------------

	@Test
	public void getListDeletedPositive() throws Exception {
		//Usuario va a listar las citas que ha eliminado
		this.authenticate("user1");
		this.mockMvc.perform(MockMvcRequestBuilders.get("/rendezvous/user/list-deleted")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("rendezvous/listasis"));
		this.unauthenticate();

	}
	@Test
	public void getListRendezvousPositive1() throws Exception {
		this.authenticate("user3");
		this.mockMvc.perform(MockMvcRequestBuilders.get("/rendezvous/user/list")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("rendezvous/list"));
		this.unauthenticate();
	}

	@Test(expected = AssertionError.class)
	public void getListRendezvousNegative1() throws Exception {
		//Se va a intentar acceder al listado de citas creadas por un usuario sin estar logeado
		this.mockMvc.perform(MockMvcRequestBuilders.get("/rendezvous/user/list")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("rendezvous/list"));

	}

	@Test
	public void getListNotAssistRendezvousPositive1() throws Exception {
		//Se va a acceder al listado de citas que aun no ha confirmado asistencia el usuario
		this.authenticate("user3");
		this.mockMvc.perform(MockMvcRequestBuilders.get("/rendezvous/user/listnotasis")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("rendezvous/listasis"));
		this.unauthenticate();
	}

	@Test(expected = AssertionError.class)
	public void getListNotAssistRendezvousNegative1() throws Exception {
		//Se va a acceder al listado de las citas que un usuario aun no va a asistir sin estar logeado
		this.mockMvc.perform(MockMvcRequestBuilders.get("/rendezvous/user/listnotasis")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("rendezvous/listasis"));
	}

	@Test
	public void getListAssistRendezvousPositive1() throws Exception {
		//Se va a acceder al listado de citas que el usuario ha confirmado su asistencia
		this.authenticate("user2");
		this.mockMvc.perform(MockMvcRequestBuilders.get("/rendezvous/user/listasis")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("rendezvous/listasis"));
		this.unauthenticate();
	}

	@Test(expected = AssertionError.class)
	public void getListAssistRendezvousNegative1() throws Exception {
		//El administrador va a intentar acceder al listado de las citas que va a asistir cuando el admin no puede asistir a ninguna
		this.authenticate("admin");
		this.mockMvc.perform(MockMvcRequestBuilders.get("/rendezvous/user/listasis")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("rendezvous/listasis"));
		this.unauthenticate();
	}

	@Test
	public void UnAssistRendezvousPositive1() throws Exception {
		//El usuario va a cancelar su asistencia a la una cita.
		this.authenticate("user3");
		int id = this.getEntityId("rendezvouse3");
		this.mockMvc.perform(MockMvcRequestBuilders.get("/rendezvous/user/notassist" + "?rendezvousId=" + id)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("rendezvous/listasis"));
		this.unauthenticate();
	}

	@Test
	public void AssistRendezvousPositive1() throws Exception {
		//El usuario va a confirmar asistencia  una cita
		this.authenticate("user1");
		int id = this.getEntityId("rendezvouse3");
		this.mockMvc.perform(MockMvcRequestBuilders.get("/rendezvous/user/assist" + "?rendezvousId=" + id)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("rendezvous/listasis"));
		this.unauthenticate();
	}

	@Test(expected = AssertionError.class)
	public void AssistRendezvousNegative1() throws Exception {
		//El admin va a intentar asistir a una rendezvous
		this.authenticate("admin");
		int id = this.getEntityId("rendezvouse3");
		this.mockMvc.perform(MockMvcRequestBuilders.get("/rendezvous/user/assist" + "?rendezvousId=" + id)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("rendezvous/listasis"));
		this.unauthenticate();
	}

	// Display ---------------------------------------------------------------
	//Solo pueden verlas usuarios que tengan mas de 18 años de edad.

	@Test
	public void DisplayRendezvousByUserPositive() throws Exception {
		this.authenticate("user1");
		int id = this.getEntityId("rendezvouse3");
		this.mockMvc.perform(MockMvcRequestBuilders.get("/rendezvous/user/display" + "?rendezvousId=" + id)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("rendezvous/display"));
		this.unauthenticate();
	}

	@Test(expected = AssertionError.class)
	public void DisplayRendezvousByUserNegative() throws Exception {
		//Un usuario menor de edad va a intentar mostrar una cita para adultos.
		this.authenticate("user5");
		int id = this.getEntityId("rendezvouse4");
		this.mockMvc.perform(MockMvcRequestBuilders.get("/rendezvous/user/display" + "?rendezvousId=" + id)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("rendezvous/display"));
		this.unauthenticate();
	}

	// Edition  ---------------------------------------------------------------
	@Test
	public void createRendezvousByUserPositive() throws Exception {
		this.authenticate("user1");
		this.mockMvc.perform(MockMvcRequestBuilders.get("/rendezvous/user/create")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("rendezvous/edit"));
		this.unauthenticate();
	}

	//Solo puede editarla si no esta en modo final
	@Test
	public void editRendezvousByUserPositive() throws Exception {
		this.authenticate("user1");
		int id = this.getEntityId("rendezvouse4");
		this.mockMvc.perform(MockMvcRequestBuilders.get("/rendezvous/user/edit" + "?rendezvouseId=" + id)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("rendezvous/edit"));
		this.unauthenticate();
	}

	@Test(expected = AssertionError.class)
	public void editRendezvousByUserNegative1() throws Exception {
		//Un usuario va a intentar editar una cita que esta en modo final.
		this.authenticate("user1");
		int id = this.getEntityId("rendezvouse1");
		this.mockMvc.perform(MockMvcRequestBuilders.get("/rendezvous/user/edit" + "?rendezvousId=" + id)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("rendezvous/edit"));
		this.unauthenticate();
	}

	@Test(expected = AssertionError.class)
	public void editRendezvousByUserNegative2() throws Exception {
		//Un usuario va a intentar editar una cita que esta borrada.
		this.authenticate("user5");
		int id = this.getEntityId("rendezvouse5");
		this.mockMvc.perform(MockMvcRequestBuilders.get("/rendezvous/user/edit" + "?rendezvousId=" + id)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("rendezvous/edit"));
		this.unauthenticate();
	}

	@Test(expected = AssertionError.class)
	public void editRendezvousByUserNegative3() throws Exception {
		//Un usuario va a intentar editar una que el no ha creado
		this.authenticate("user1");
		int id = this.getEntityId("rendezvouse5");
		this.mockMvc.perform(MockMvcRequestBuilders.get("/rendezvous/user/edit" + "?rendezvousId=" + id)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("rendezvous/edit"));
		this.unauthenticate();
	}

	@Test
	public void editSimilarRendezvousByUserPositive() throws Exception {
		this.authenticate("user1");
		int id = this.getEntityId("rendezvouse1");
		this.mockMvc.perform(MockMvcRequestBuilders.get("/rendezvous/user/editSimilar" + "?rendezvouseId=" + id)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("rendezvous/editNotSimilar"));
		this.unauthenticate();
	}

	@Test(expected = AssertionError.class)
	public void editSimilarRendezvousByUserNegative() throws Exception {
		this.authenticate("user1");
		int id = this.getEntityId("rendezvouse3");
		this.mockMvc.perform(MockMvcRequestBuilders.get("/rendezvous/user/editSimilar" + "?rendezvouseId=" + id)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("rendezvous/editNotSimilar"));
		this.unauthenticate();
	}

	@Test
	public void editNotSimilarRendezvousByUserPositive() throws Exception {
		this.authenticate("user1");
		int id = this.getEntityId("rendezvouse1");
		this.mockMvc.perform(MockMvcRequestBuilders.get("/rendezvous/user/editNotSimilar" + "?rendezvouseId=" + id)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("rendezvous/editSimilar"));
		this.unauthenticate();
	}

	@Test(expected = AssertionError.class)
	public void editNotSimilarRendezvousByUserNegative() throws Exception {
		this.authenticate("user1");
		int id = this.getEntityId("rendezvouse3");
		this.mockMvc.perform(MockMvcRequestBuilders.get("/rendezvous/user/editNotSimilar" + "?rendezvouseId=" + id)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("rendezvous/editSimilar"));
		this.unauthenticate();
	}


	@Configuration
	public static class TestConfiguration {

		@Bean
		public RendezvousesUserController rendezvousesUserController() {
			return new RendezvousesUserController();
		}

	}
}
