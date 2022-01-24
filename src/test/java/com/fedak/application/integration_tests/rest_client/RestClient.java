package com.fedak.application.integration_tests.rest_client;

import com.fedak.application.entity.ToDo;
import com.fedak.application.entity.User;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class RestClient {

    private int port = 0;
    private RestTemplate restTemplate = new RestTemplate();
    private TestRestTemplate testRestTemplate = new TestRestTemplate();

    private final String URL = "http://localhost:";

    public final static String TODOFORMARKTWAIN = "Write book about Tom Sawyer";

    public void setPort(int port) {
        this.port = port;
    }

    public List<User> getAllUsers(){
        ResponseEntity<List<User>> responseEntity =
                restTemplate.exchange(URL + port + "/users/", HttpMethod.GET, null
                        , new ParameterizedTypeReference<List<User>>(){});
        return responseEntity.getBody();
    }

    public String deleteAllUsers(){
        return restTemplate.exchange(URL + port + "/users/",HttpMethod.DELETE,null,String.class).getBody();
    }

    public User addUserToDatabase(){
        User userToAdd = new User("Ivan", "Fedak", "fedak@mail.com");
        return restTemplate.postForObject(URL + port + "/users/",userToAdd,User.class);
    }

    public User updateUserInDatabase(){
        User userToAdd = new User("Ivan", "Fedak", "fedak@mail.com");
        int id = restTemplate.postForObject(URL + port + "/users/",userToAdd,User.class).getId();
        userToAdd.setName("Petro");
        userToAdd.setId(id);
        RequestEntity<User> requestEntity = RequestEntity.put(URL + port + "/users/")
                .accept(MediaType.APPLICATION_JSON)
                .body(userToAdd);
        return restTemplate.exchange(URL + port + "/users/", HttpMethod.PUT, requestEntity
                , new ParameterizedTypeReference<User>(){}).getBody();
    }

    public ToDo addToDoForUser(){
        User userToAdd = new User("Mark", "Twain", "twain@mail.com");
        User addedUser = restTemplate.postForObject(URL + port + "/users/",userToAdd,User.class);
        System.out.println(addedUser);
        System.out.println(addedUser.getId());
        ToDo toDo = new ToDo("Write book about Tom Sawyer");
        toDo.setUser(addedUser);
        System.out.println(toDo.toString());
        System.out.println(URL + port + "/users/"+addedUser.getId()+"/todos/");
        return restTemplate.postForObject(URL + port + "/users/"+addedUser.getId()+"/todos/",toDo,ToDo.class);
    }

    public User getUserById() {
        User user = addUserToDatabase();
        System.out.println(user.getId());


        String url = this.URL + port + "/users/"+ user.getId() + "/";
        System.out.println(url);


        User user1 = testRestTemplate.exchange(url,HttpMethod.GET, null
                        , new ParameterizedTypeReference<User>(){}).getBody();


        //User user1 = testRestTemplate.getForObject(url,User.class);
        //User user1 = testRestTemplate.getForObject(url,User.class);
        System.out.println(user1);

//        ResponseEntity<User> responseEntity =
//                restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null
//                        , new ParameterizedTypeReference<User>(){});
//        System.out.println(responseEntity.getBody());

        return user1;

    }
}
