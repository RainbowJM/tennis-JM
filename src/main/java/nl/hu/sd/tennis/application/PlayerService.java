package nl.hu.sd.tennis.application;

import nl.hu.sd.tennis.application.exception.PlayerNotFound;
import nl.hu.sd.tennis.data.PlayerRepository;
import nl.hu.sd.tennis.domain.Player;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }


    public List<Player> getPlayers() {
        return playerRepository.findAll();
    }

    public Player getPlayer(int id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFound(id));
    }

    public Player createPlayer(String name) {
        Player player = new Player(name);

        return this.playerRepository.save(player);
    }

    public Player editPlayer(int id, String name) {
        Player player = playerRepository.findById(id).orElseThrow(() -> new PlayerNotFound(id));
        player.setName(name);

        return this.playerRepository.save(player);
    }

    public void deletePlayer(int id) {
        this.playerRepository.deleteById(id);
    }
}
