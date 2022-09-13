package nl.hu.sd.tennis.data;

import nl.hu.sd.tennis.domain.Round;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoundRepository extends JpaRepository<Integer, Round> {
}
