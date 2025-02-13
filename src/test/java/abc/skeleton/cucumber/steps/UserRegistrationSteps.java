package abc.skeleton.cucumber.steps;

import abc.skeleton.cucumber.user.User;
import io.cucumber.datatable.DataTable;
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
    private ResponseEntity<User> response;

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
        response = restTemplate.postForEntity("http://localhost:" + port + "/api/users/register", request, User.class);
    }

    @Then("the registration should be successful")
    public void the_registration_should_be_successful() {
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status code should be 200 OK");
        User registeredUser = response.getBody();
        assertNotNull(registeredUser, "Registered user should not be null");
        assertNotNull(registeredUser.getId(), "Registered user should have an id");
    }
}
