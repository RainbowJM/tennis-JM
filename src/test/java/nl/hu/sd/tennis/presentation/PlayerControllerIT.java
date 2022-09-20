package nl.hu.sd.tennis.presentation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import nl.hu.sd.tennis.data.PlayerRepository;
import nl.hu.sd.tennis.domain.Player;
import nl.hu.sd.tennis.presentation.dto.request.PlayerRequestDTO;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/data-h2_player_before.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/data-h2_player_after.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class PlayerControllerIT {

    private static final String LINK = "/player";

    private PlayerRequestDTO playerRequestDTO;

    @Autowired
    private PlayerRepository playerRepository;

    @LocalServerPort
    private int port;

    private int id;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;

        playerRequestDTO = new PlayerRequestDTO();
        playerRequestDTO.name = "Elvis";
    }

    @Test
    @DisplayName("Should list all player")
    void listAllPlayerSuccess() throws Exception {

        get("/player")
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .body("$", hasSize(3));

        List<Player> playerList = playerRepository.findAll();
        assertFalse(playerList.isEmpty());
        assertEquals(3, playerList.size());
    }

    @Test
    @DisplayName("Should give the player")
    void getPlayerByIdPathVarSuccess() throws Exception {
        id = 10;

        get(LINK + "/" + id)
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .body("name", equalTo(""));

        Player request = playerRepository.findById(id).get();
        assertNotNull(request);
    }

    @Test
    @DisplayName("Should give the player")
    void getPlayerByIdReqParamSuccess() throws Exception {
        id = 11;

        given()
                .params("id", id)
                .when()
                .get(LINK)
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .body("$", hasSize(1));

        Player request = playerRepository.findById(id).get();
        assertNotNull(request);
    }

    @Test
    @DisplayName("Should create a player")
    void addPlayerSuccess() throws Exception {

        given()
                .accept(ContentType.JSON).contentType(ContentType.JSON)
                .body(playerRequestDTO)
                .log().all()
                .when()
                .post(LINK)
                .then().log().all()
                .statusCode(HttpStatus.SC_CREATED);

        List<Player> playerList = playerRepository.findAll();
        assertFalse(playerList.isEmpty());
        assertEquals(4, playerList.size());
    }

    @Test
    @DisplayName("Should update a player")
    void updateBillPaymentSuccess() throws Exception {
        id = 13;

        given()
                .params("id", id)
                .accept(ContentType.JSON).contentType(ContentType.JSON)
                .body(playerRequestDTO).log().all()
                .when()
                .put(LINK)
                .then().log().all()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    @DisplayName("Should delete the player")
    void deletePlayerSuccess() throws Exception {
        id = 10;

        given()
                .params("id", id)
                .accept(ContentType.JSON).contentType(ContentType.JSON)
                .body(playerRequestDTO).log().all()
                .when()
                .delete(LINK)
                .then().log().all()
                .statusCode(HttpStatus.SC_OK);
    }
}
