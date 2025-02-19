package abc.skeleton.rest_client.controller;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.wiremock.spring.EnableWireMock;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableWireMock
public class WireMockServerTest {

    @LocalServerPort
    private int port;

    private WireMockServer wireMockServer;

    private static final String STATUS_200_JSON_FILE_PATH = "./src/test/java/abc/skeleton/json_files/test.json";

    @BeforeEach
    void setup() {
        wireMockServer = new WireMockServer(WireMockConfiguration.options().port(8080));
        wireMockServer.start();
        WireMock.configureFor("localhost", 8080);
    }

    @AfterEach
    void wireMockServerStop() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }

    @Test
    public void status200Test() throws IOException {
        String jsonString = new String(Files.readAllBytes(Paths.get(STATUS_200_JSON_FILE_PATH)));

        givenThat(get(urlPathMatching("/api/v3/pet/2002")).willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(jsonString)));

        given()
                .pathParam("id", 2002)
                .port(port)
                .when()
                .get("/pet/{id}")
                .then()
                .statusCode(200)
                .body("name", equalTo("test"));
    }

    @Test
    public void status404Test() {

        givenThat(get(urlPathMatching("/api/v3/pet/2002")).willReturn(aResponse()
                .withStatus(404)
                .withHeader("Content-Type", "application/json")));

        given()
                .pathParam("id", 2002)
                .port(port)
                .when()
                .get("/pet/{id}")
                .then()
                .statusCode(500);
    }

}
