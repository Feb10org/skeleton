package abc.skeleton.rest_client.controller;

import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.wiremock.integrations.testcontainers.WireMockContainer;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class PetControllerWiremockStubInTest {

    private static final int WIREMOCK_PORT = 8080;
    @Container
    private static final WireMockContainer wireMockContainer = new WireMockContainer("wiremock/wiremock:latest")
            .withExposedPorts(WIREMOCK_PORT);
    @LocalServerPort
    private int port;

    @BeforeAll
    static void beforeAll() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @DynamicPropertySource
    static void overrideApiUrl(DynamicPropertyRegistry registry) {
        registry.add("remote-apis.petstore.base-path", wireMockContainer::getBaseUrl);
    }

    @BeforeEach
    void beforeEach() {
        WireMock.configureFor(wireMockContainer.getHost(), wireMockContainer.getMappedPort(WIREMOCK_PORT));
        WireMock.givenThat(WireMock.get(WireMock.urlEqualTo("/api/v3/pet/3003"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                    {
                                        "id": 3003,
                                        "category": {
                                            "id": 3003,
                                            "name": "cats"
                                        },
                                        "name": "teddyABC",
                                        "photoUrls": [
                                            "http://photo.com/1",
                                            "http://photo.com/2"
                                        ],
                                        "tags": [],
                                        "status": "available"
                                    }
                                """)));
    }

    @Test
    void shouldAddPetSuccessfully() {
        given()
            .contentType(ContentType.JSON)
            .body(new PetController.CreatePetDto("teddyXXX"))
            .port(port)
        .when()
            .post("/pet")
        .then()
            .statusCode(200);
    }

    @Test
    void shouldRetrievePetSuccessfully() {
        given()
            .pathParam("id", 3003)
            .port(port)
        .when()
            .get("/pet/{id}")
        .then()
            .statusCode(200)
            .body("name", equalTo("teddyABC"));
    }

}
