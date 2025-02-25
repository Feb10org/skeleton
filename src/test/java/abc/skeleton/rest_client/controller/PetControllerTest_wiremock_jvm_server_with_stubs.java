package abc.skeleton.rest_client.controller;

import com.github.tomakehurst.wiremock.stubbing.Scenario;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

import static abc.skeleton.rest_client.controller.WireMockResponses.ADD_PET_3003_RESPONSE;
import static abc.skeleton.rest_client.controller.WireMockResponses.ADD_PET_3005_RESPONSE;
import static abc.skeleton.rest_client.controller.WireMockResponses.GET_PET_3003_RESPONSE;
import static abc.skeleton.rest_client.controller.WireMockResponses.GET_PET_3005_RESPONSE;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.givenThat;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock
class PetControllerTest_wiremock_jvm_server_with_stubs {
    private static final String FULL_ADD_PET_SCENARIO = "FULL_ADD_PET_SCENARIO";
    private static final String PET_ADDED = "PET_ADDED";
    @LocalServerPort
    private int port;


    @Test
    void should_get_pet_3003() {
        givenThat(
                get(urlPathMatching("/api/v3/pet/3003"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(GET_PET_3003_RESPONSE))
        );

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
        givenThat(
                post(urlPathMatching("/api/v3/pet"))
                .willReturn(aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(ADD_PET_3003_RESPONSE))
        );

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
        givenThat(
                get(urlPathMatching("/api/v3/pet/3004"))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", "application/json"))
        );

        given()
            .pathParam("id", 3004)
            .port(port)
        .when()
            .get("/pet/{id}")
        .then()
            .statusCode(500);
    }

    @Test
    void full_add_pet_scenario() {
        givenThat(get(urlPathMatching("/api/v3/pet/3005"))
                .inScenario(FULL_ADD_PET_SCENARIO)
                .whenScenarioStateIs(Scenario.STARTED)
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", "application/json"))
        );

        givenThat(post(urlPathMatching("/api/v3/pet"))
                .inScenario(FULL_ADD_PET_SCENARIO)
                .whenScenarioStateIs(Scenario.STARTED)
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(ADD_PET_3005_RESPONSE))
                .willSetStateTo(PET_ADDED)
        );

        givenThat(get(urlPathMatching("/api/v3/pet/3005"))
                .inScenario(FULL_ADD_PET_SCENARIO)
                .whenScenarioStateIs(PET_ADDED)
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(GET_PET_3005_RESPONSE))
        );

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
