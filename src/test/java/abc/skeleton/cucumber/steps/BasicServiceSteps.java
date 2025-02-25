package abc.skeleton.cucumber.steps;



import abc.skeleton.cucumber.basic.BasicService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasicServiceSteps {

    private BasicService basicService;
    private int result;

    @Given("a basic service is available")
    public void a_basic_service_is_available() {
        basicService = new BasicService();
    }

    @When("I add {int} and {int}")
    public void i_add_and(Integer a, Integer b) {
        result = basicService.add(a, b);
    }

    @Then("the result should be {int}")
    public void the_result_should_be(Integer expectedResult) {
        assertEquals(expectedResult.intValue(), result);
    }
}