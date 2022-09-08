package nl.hu.sd.tennis.presentation;

import nl.hu.sd.tennis.application.PlayerService;
import nl.hu.sd.tennis.domain.Player;
import nl.hu.sd.tennis.presentation.dto.request.PlayerRequestDTO;
import nl.hu.sd.tennis.presentation.dto.response.PlayerResponseDTO;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/player")
public class PlayerController {
    private final PlayerService playerService;

    private final String LINK = " http://localhost:8080/player";

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public @ResponseBody ResponseEntity<?> get(@RequestParam(required = false) String id) {

        try {
            // Retrieve player
            List<PlayerResponseDTO> responseDTOS = new ArrayList<>();

            if (id == null) {
                List<Player> players = playerService.getPlayers();

                // Converts to responseDTOS
                for (Player player : players) {
                    PlayerResponseDTO playerResponseDTO = new PlayerResponseDTO(
                            player.getId(),
                            player.getName()
                    );

                    Link create = Link.of(LINK).withRel("POST");
                    Link delete = Link.of(LINK).withRel("DELETE");
                    Link edit = Link.of(LINK).withRel("UPDATE");

                    playerResponseDTO.add(create);
                    playerResponseDTO.add(delete);
                    playerResponseDTO.add(edit);
                    responseDTOS.add(playerResponseDTO);
                }

            } else {
                Player player = playerService.getPlayer(Integer.parseInt(id));
                PlayerResponseDTO playerResponseDTO = new PlayerResponseDTO(
                        player.getId(),
                        player.getName()
                );

                Link create = Link.of(LINK).withRel("POST");
                Link delete = Link.of(LINK).withRel("DELETE");
                Link edit = Link.of(LINK).withRel("UPDATE");

                playerResponseDTO.add(create);
                playerResponseDTO.add(delete);
                playerResponseDTO.add(edit);
                responseDTOS.add(playerResponseDTO);
            }
            return new ResponseEntity<>(responseDTOS, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<PlayerResponseDTO> get(@PathVariable int id) {
        Player player = playerService.getPlayer(id);

        if (player != null) {
            PlayerResponseDTO playerResponseDTO = new PlayerResponseDTO(
                    player.getId(),
                    player.getName()
            );

            Link create = Link.of(LINK).withRel("POST");
            Link delete = Link.of(LINK).withRel("DELETE");
            Link edit = Link.of(LINK).withRel("UPDATE");

            playerResponseDTO.add(create);
            playerResponseDTO.add(delete);
            playerResponseDTO.add(edit);
            return new ResponseEntity<>(playerResponseDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public @ResponseBody ResponseEntity<?> add(@Valid @RequestBody PlayerRequestDTO dto) {
        try {
            Player player = playerService.createPlayer(dto.name);
            return new ResponseEntity<>(player, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{id}")
    public @ResponseBody ResponseEntity<?> edit(@PathVariable int id,
                                                @Valid @RequestBody PlayerRequestDTO dto) {
        try {
            Player player = playerService.editPlayer(id,
                    dto.name);

            return new ResponseEntity<>(player, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public @ResponseBody ResponseEntity<?> delete(@PathVariable int id){
        try {
            playerService.deletePlayer(id);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
        }
    }
}
