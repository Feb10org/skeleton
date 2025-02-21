package abc.skeleton.rest_client.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PetControllerTest {

    @LocalServerPort
    private int port;

    @BeforeAll
    static void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    @Disabled(value = "to be enabled wen wiremock change will be merged")
    void shouldAddPetSuccessfully() {
        given()
            .contentType(ContentType.JSON)
            .body(new PetController.CreatePetDto("teddy"))
            .port(port)
        .when()
            .post("/pet")
        .then()
            .statusCode(200);
    }

    @Test
    @Disabled(value = "to be enabled wen wiremock change will be merged")
    void shouldRetrievePetSuccessfully() {
        given()
            .pathParam("id", 3003)
            .port(port)
        .when()
            .get("/pet/{id}")
        .then()
            .statusCode(200)
            .body("name", equalTo("teddy"));
    }

}
