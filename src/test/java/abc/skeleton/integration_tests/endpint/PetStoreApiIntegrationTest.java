package abc.skeleton.integration_tests.endpint;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PetStoreApiIntegrationTest {

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "http://localhost:8080/api/v3";
    }

    @Test
    public void testAddPet() {
        String petJson = "{\n" +
                "  \"id\": 10,\n" +
                "  \"name\": \"doggie\",\n" +
                "  \"status\": \"available\"\n" +
                "}";

        Response response = given()
                .contentType("application/json")
                .body(petJson)
                .when()
                .post("/pet")
                .then()
                .statusCode(200)  // Oczekiwany kod statusu 200
                .extract()
                .response();

        assertEquals("doggie", response.jsonPath().getString("name"));
    }

    @Test
    public void testGetPetById() {
        Response response = given()
                .when()
                .get("/pet/10")
                .then()
                .statusCode(200)  // Oczekiwany kod statusu 200
                .extract()
                .response();

        assertEquals(10, response.jsonPath().getInt("id"));
        assertEquals("doggie", response.jsonPath().getString("name"));
    }

    @Test
    public void testUpdatePet() {
        String updatedPetJson = "{\n" +
                "  \"id\": 10,\n" +
                "  \"name\": \"doggie_updated\",\n" +
                "  \"status\": \"sold\"\n" +
                "}";

        Response response = given()
                .contentType("application/json")
                .body(updatedPetJson)
                .when()
                .put("/pet")
                .then()
                .statusCode(200)  // Oczekiwany kod statusu 200
                .extract()
                .response();

        assertEquals("doggie_updated", response.jsonPath().getString("name"));
        assertEquals("sold", response.jsonPath().getString("status"));
    }

    @Test
    public void testDeletePet() {
        Response response = given()
                .when()
                .delete("/pet/10")
                .then()
                .statusCode(200)  // Oczekiwany kod statusu 200
                .extract()
                .response();

        assertEquals("Pet deleted", response.jsonPath().getString("message"));
    }

    @Test
    public void testFindPetsByStatus() {
        Response response = given()
                .param("status", "available")
                .when()
                .get("/pet/findByStatus")
                .then()
                .statusCode(200)  // Oczekiwany kod statusu 200
                .extract()
                .response();

        assertEquals("available", response.jsonPath().getString("[0].status"));
    }
}
