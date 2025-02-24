package abc.skeleton.rest_client.controller;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.BindMode;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.wiremock.integrations.testcontainers.WireMockContainer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class PetControllerTest_wiremock_testcontainers_with_mappings {

    private static final int WIREMOCK_PORT = 8080;
    @Container
    private static final WireMockContainer wireMockContainer = new WireMockContainer("wiremock/wiremock:latest")
            .withFileSystemBind("./src/test/resources/__files", "/home/wiremock/__files", BindMode.READ_WRITE)
            .withFileSystemBind("./src/test/resources/mappings", "/home/wiremock/mappings", BindMode.READ_WRITE)
            .withExposedPorts(WIREMOCK_PORT);
    @LocalServerPort
    private int port;

    @BeforeAll
    static void beforeAll() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
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

}
