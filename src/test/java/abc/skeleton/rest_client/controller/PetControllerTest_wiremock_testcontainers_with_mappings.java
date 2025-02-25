package abc.skeleton.rest_client.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.wiremock.integrations.testcontainers.WireMockContainer;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@Slf4j
class PetControllerTest_wiremock_testcontainers_with_mappings {

    @Container
    private static final WireMockContainer wireMockContainer = new WireMockContainer("wiremock/wiremock:latest")
            .withFileSystemBind("./src/test/resources/__files", "/home/wiremock/__files", BindMode.READ_WRITE)
            .withFileSystemBind("./src/test/resources/mappings", "/home/wiremock/mappings", BindMode.READ_WRITE)
            .withCliArg("--verbose");
    @LocalServerPort
    private int port;

    @BeforeAll
    static void beforeAll() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        wireMockContainer.followOutput(new Slf4jLogConsumer(log));
    }

    @DynamicPropertySource
    private static void overrideApiUrl(DynamicPropertyRegistry registry) {
        registry.add("remote-apis.petstore.base-path", wireMockContainer::getBaseUrl);
    }


    @Test
    void should_get_pet_3003() {
        given()
            .pathParam("id", 3003)
            .port(port)
        .when()
            .get("/pet/{id}")
        .then()
            .statusCode(200)
            .body("name", equalTo("mat"));
    }

    @Test
    void should_add_pet_3003() {
        given()
            .body(new PetController.CreatePetDto("jerry"))
            .contentType(ContentType.JSON)
            .port(port)
        .when()
            .post("/pet")
        .then()
            .statusCode(200)
            .body("name", equalTo("jerry"));
    }

    @Test
    void should_respond_with_err_500_when_pet_not_found() {
        given()
            .pathParam("id", 3004)
            .port(port)
        .when()
            .get("/pet/{id}")
        .then()
            .statusCode(500);
    }

    /**
     * This test is an example of a whole scenario, where there are 3 steps:
     * 1) before adding pet, when try to get the pet, 404 not found should be returned
     * 2) adding a pet
     * 3) once again we try to get the pet, should respond with 200 and return a pet response
     */
    @Test
    void full_add_pet_scenario() {
        // before add garfield pet
        given()
            .pathParam("id", 3005)
            .port(port)
        .when()
            .get("/pet/{id}")
        .then()
            .statusCode(500);

        // add garfield pet
        given()
            .body(new PetController.CreatePetDto("garfield"))
            .contentType(ContentType.JSON)
            .port(port)
        .when()
            .post("/pet")
        .then()
            .statusCode(200)
            .body("name", equalTo("garfield"));

        // after add garfield pet
        given()
            .pathParam("id", 3005)
            .port(port)
        .when()
            .get("/pet/{id}")
        .then()
            .statusCode(200)
            .body("name", equalTo("garfield"));
    }

}
