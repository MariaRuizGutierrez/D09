
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
import org.springframework.web.context.WebApplicationContext;

import utilities.AbstractTest;
import controllers.user.CommentUserController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@WebAppConfiguration
public class CommentControllerTest extends AbstractTest {

	private MockMvc					mockMvc;

	//	@Autowired
	//	private CommentService			commentService;
	//
	//	@Autowired
	//	private RendezvouseService		rendezvouseService;

	@Autowired
	private WebApplicationContext	ctx;


	@Override
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.ctx).build();
	}

	// Listing ---------------------------------------------------------------

	@Test
	public void getListCommentByUserPositive1() throws Exception {
		this.authenticate("user3");
		int id = this.getEntityId("rendezvouse3");
		this.mockMvc.perform(MockMvcRequestBuilders.get("/comment/user/list" + "?rendezvouseId=" + id)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("comment/list"));
		this.unauthenticate();
	}

	//	@Test
	//	public void getListCommentByAdminPositive2() throws Exception {
	//		this.authenticate("admin");
	//		this.mockMvc.perform(MockMvcRequestBuilders.get("/comment/administrator/list")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("comment/list"));
	//		this.unauthenticate();
	//	}

	@Test(expected = AssertionError.class)
	public void getListCommentByUserNegative1() throws Exception {
		//El user1 va a intentar sacar la lista de comentarios de la cita 3, a la cual el no va a asistir.
		this.authenticate("user1");
		int id = this.getEntityId("rendezvouse3");
		this.mockMvc.perform(MockMvcRequestBuilders.get("/comment/user/list" + "?rendezvouseId=" + id)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("comment/list"));
		this.unauthenticate();
	}

	@Test(expected = AssertionError.class)
	public void getListCommentNegative2() throws Exception {
		//Se va a intentar sacar el listado de los comentarios sin estar logeado.
		int id = this.getEntityId("rendezvouse3");
		this.mockMvc.perform(MockMvcRequestBuilders.get("/comment/user/list" + "?rendezvouseId=" + id)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("comment/list"));

	}

	@Test
	public void getListReplysOfCommentByUserPositive1() throws Exception {
		//El usuario "user1" va a listar las respuestas al comentario 1
		this.authenticate("user1");
		int id = this.getEntityId("comment1");
		this.mockMvc.perform(MockMvcRequestBuilders.get("/comment/user/listReplys" + "?commentId=" + id)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("comment/list"));
		this.unauthenticate();
	}

	//	@Test
	//	public void getListCommentByRendezvousPositive() throws Exception {
	//		//El admin va a sacar los comentarios de una cita
	//		int id = this.getEntityId("rendezvouse3");
	//		this.authenticate("admin");
	//		this.mockMvc.perform(MockMvcRequestBuilders.get("/comment/administrator/list" + "?rendezvouseId=" + id)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("comment/list"));
	//		this.unauthenticate();
	//	}

	// Edition ---------------------------------------------------------------

	@Test
	public void getCreateCommentByUserPositive1() throws Exception {
		//El usuario "user3" va a crear un comentario en la cita 3
		this.authenticate("user3");
		int id = this.getEntityId("rendezvouse3");
		this.mockMvc.perform(MockMvcRequestBuilders.get("/comment/user/create" + "?rendezvouseId=" + id)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("comment/edit"));
		this.unauthenticate();
	}

	@Test(expected = AssertionError.class)
	public void getCreateCommentByUserNegative() throws Exception {
		//El usuario "user1" va a crear un comentario en la cita 3
		this.authenticate("user1");
		int id = this.getEntityId("rendezvouse3");
		this.mockMvc.perform(MockMvcRequestBuilders.get("/comment/user/create" + "?rendezvouseId=" + id)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("comment/edit"));
		this.unauthenticate();
	}

	@Test
	public void getCreateReplyToCommentByUserPositive1() throws Exception {
		//El usuario "user3" va a crear un comentario en la cita 3
		this.authenticate("user3");
		int id = this.getEntityId("comment3");
		this.mockMvc.perform(MockMvcRequestBuilders.get("/comment/user/createReply" + "?commentId=" + id)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("comment/edit"));
		this.unauthenticate();
	}

	@Test(expected = AssertionError.class)
	public void getCreateReplyToCommentByUserNegative1() throws Exception {
		//El usuario "user1 va a crear una respuesta al comment 3
		this.authenticate("user1");
		int id = this.getEntityId("comment3");
		this.mockMvc.perform(MockMvcRequestBuilders.get("/comment/user/createReply" + "?commentId=" + id)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("comment/edit"));
		this.unauthenticate();
	}


	@Configuration
	public static class TestConfiguration {

		@Bean
		public CommentUserController commentUserController() {
			return new CommentUserController();
		}
		//		@Bean
		//		public CommentAdministratorController commentAdministratorController() {
		//			return new CommentAdministratorController();
		//		}
	}

}
