package nl.hu.sd.tennis.presentation;

import nl.hu.sd.tennis.domain.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PlayerControllerTest {

    @MockBean
    private PlayerRepository playerRepository;

    @Autowired
    private MockMvc mockMvc;

    private Player player1;
    private Player player2;

    @BeforeEach
    void setUp(){
        player1 = new Player("John");
        player1.setId(1);

        player2 = new Player("Ellen");
        player2.setId(2);
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
}
