package abc.skeleton.rest_client.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PetControllerTest_wiremock_docker_with_mappings {

    @LocalServerPort
    private int port;

    @BeforeAll
    static void beforeAll() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @DynamicPropertySource
    static void overrideApiUrl(DynamicPropertyRegistry registry) {
        registry.add("remote-apis.petstore.base-path", () -> "http://localhost:8089");
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

}
