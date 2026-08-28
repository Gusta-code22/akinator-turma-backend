package gusta.turma.akinator.game;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findByPublicId(UUID publicId);
}
