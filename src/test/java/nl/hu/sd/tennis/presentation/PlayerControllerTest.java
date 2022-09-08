package nl.hu.sd.tennis.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.hu.sd.tennis.data.PlayerRepository;
import nl.hu.sd.tennis.domain.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PlayerControllerTest {

    @MockBean
    private PlayerRepository playerRepository;

    @Autowired
    private MockMvc mockMvc;
    private Player player1;
    private Player player2;
    private Player player3;

    @BeforeEach
    void setUp(){
        player1 = new Player("John");
        player1.setId(1);

        player2 = new Player("Ellen");
        player2.setId(2);

        player3 = new Player("Juan");
        player3.setId(3);
    }

    @Test
    @DisplayName("Will list all players")
    void getPlayersSuccess() throws Exception{
        when(playerRepository.findAll()).thenReturn(Arrays.asList(player1, player2));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/player");

        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("[0].id",is(1)))
                .andExpect(jsonPath("[0].name",is("John")))
                .andExpect(jsonPath("[1].id",is(2)))
                .andExpect(jsonPath("[1].name",is("Ellen")));

        verify(playerRepository).findAll();
    }

    @Test
    @DisplayName("Will give the player that was requested bij request parameter")
    void getPlayerByIdReqParamSuccess() throws Exception{
        when(playerRepository.findById(player2.getId())).thenReturn(Optional.of(player2));

        int id = player2.getId();

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/player")
                .param("id", String.valueOf(id));

        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$[0].name",is("Ellen")));

        verify(playerRepository).findById(id);
    }

    @Test
    @DisplayName("Will give the player that was requested by path variable")
    void getPlayerByIdPathVarSuccess() throws Exception{
        when(playerRepository.findById(player3.getId())).thenReturn(Optional.of(player3));

        int id = player3.getId();

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/player/" + id);

        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(3)))
                .andExpect(jsonPath("name",is("Juan")));

        verify(playerRepository).findById(id);
    }

    @Test
    @DisplayName("Will create a player")
    void createPlayerSuccess() throws Exception {

        String body = new ObjectMapper().writeValueAsString(player1);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/player")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);

        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(201));
    }

    @Test
    @DisplayName("Will delete a player")
    void deletePlayerSuccess() throws Exception {
        when(playerRepository.findById(player3.getId())).thenReturn(Optional.of(player3));

        int id = player3.getId();

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/player/" + id);

        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Will edit a player")
    void editPlayerSuccess() throws Exception {

        String body = new ObjectMapper().writeValueAsString(player1);

        int id = player2.getId();

        when(playerRepository.findById(player2.getId())).thenReturn(Optional.of(player2));
        when(playerRepository.save(player1)).thenReturn(player1);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/player/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);

        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }
}
