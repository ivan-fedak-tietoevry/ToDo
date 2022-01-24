package com.fedak.application.integration_tests;

import com.fedak.application.entity.ToDo;
import com.fedak.application.entity.User;
import com.fedak.application.integration_tests.rest_client.RestClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTest {

	@LocalServerPort
	private Integer port;

	@Autowired
	private RestClient restClient;

	@BeforeEach
	void setUp(){
		restClient.setPort(port);
	}

	@Order(1)
	@Test
	void cleanDatabase() {
		try {
			System.out.println(restClient.deleteAllUsers());
		} catch (RuntimeException e){
			System.out.println("Database already empty");
		}
		assert (restClient.getAllUsers().size()==0);
	}

	@Order(2)
	@Test
	void addUserToDatabase(){
		User user = restClient.addUserToDatabase();
		assert(user.getName().equals("Ivan"));
		assert restClient.getAllUsers().size()!=0;
	}

	@Order(3)
	@Test
	void updateUserInDatabase(){
		User user = restClient.updateUserInDatabase();
		assert user.getName().equals("Petro");
	}

	@Order(4)
	@Test
	void addToDoToDatabase(){
		ToDo toDo = restClient.addToDoForUser();
		assert(toDo.getTodo().equals(RestClient.TODOFORMARKTWAIN));
	}

	@Order(5)
	@Test
	void getUserById(){
		User user = restClient.getUserById();
		assert(user != null);
	}
}
