package abc.skeleton.cucumber.steps;

import abc.skeleton.cucumber.user.User;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class UserRegistrationSteps {


    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate;
    private ResponseEntity<User> registerUserResponse;
    private ResponseEntity<User> getUserResponse;
    private ResponseEntity<String> errorResponse;
    private String baseUrl;

    @Autowired
    public UserRegistrationSteps(TestRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Given("the following user details:")
    public void the_following_user_details(DataTable table) {
        List<Map<String, String>> userDetails = table.asMaps(String.class, String.class);
        assertFalse(userDetails.isEmpty(), "Data table should not be empty");
    }

    @When("I register the user with username {string} and password {string}")
    public void i_register_the_user_with_username_and_password(String username, String password) {
        User user = new User(username, password);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        baseUrl = "http://localhost:" + port + "/api/users/register";
        registerUserResponse = restTemplate.postForEntity(baseUrl, request, User.class);

    }

    @Then("the registration is successful")
    public void the_registration_should_be_successful() {
        assertEquals(HttpStatus.OK, registerUserResponse.getStatusCode(), "Status code should be 200 OK");
        User registeredUser = registerUserResponse.getBody();
        assertNotNull(registeredUser, "Registered user should not be null");
        assertNotNull(registeredUser.getId(), "Registered user should have an id");
    }

    @When("I fetch the user with username {string}")
    public void i_fetch_the_user_with_username(String username) {
        String fetchUrl = "http://localhost:" + port + "/api/users/" + username;
        getUserResponse = restTemplate.getForEntity(fetchUrl, User.class);
    }

    @When("I fetch the user with username {string} that has not been registered before")
    public void i_fetch_the_user_that_does_not_exist(String username) {
        String fetchUrl = "http://localhost:" + port + "/api/users/" + username;
        errorResponse = restTemplate.getForEntity(fetchUrl, String.class);
    }

    @Then("the fetched user should have password {string}")
    public void the_fetched_user_should_have_username(String expectedPassword) {
        assertNotNull(getUserResponse, "Fetched user response should not be null");
        assertEquals(HttpStatus.OK, getUserResponse.getStatusCode(), "Status code should be 200 OK");
        User fetchedUser = getUserResponse.getBody();
        assertNotNull(fetchedUser, "Fetched user should not be null");
        assertEquals(expectedPassword, fetchedUser.getPassword(), "Passwords should match");
    }


    @And("I register the user with username {string} and password {string} for the second time")
    public void i_register_the_user_again(String username, String password) {
        User user = new User(username, password);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<String> duplicateResponse = restTemplate.postForEntity(baseUrl, request, String.class);
        errorResponse = new ResponseEntity<>(duplicateResponse.getBody(), duplicateResponse.getStatusCode());
    }

    @Then("the registration should fail with message {string}")
    public void the_registration_should_fail_with_message(String expectedMessage) {
        assertNotNull(errorResponse, "An error response is expected");
        assertTrue(errorResponse.getBody().contains(expectedMessage),
                "Expected error message to contain: " + expectedMessage);
        assertEquals(HttpStatus.CONFLICT, errorResponse.getStatusCode());
    }

    @Then("I got the error response with message {string}")
    public void error_response_with_message(String expectedMessage) {
        assertNotNull(errorResponse, "An error response is expected");
        assertTrue(errorResponse.getBody().contains(expectedMessage),
                "Expected error message to contain: " + expectedMessage);
        assertEquals(HttpStatus.NOT_FOUND, errorResponse.getStatusCode());
    }
}
