package gusta.turma.akinator.game;

import gusta.turma.akinator.pergunta.AnswerRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<GameResponse> startGame() {
        GameResponse response = gameService.startGame();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{gameId}/answers")
    public ResponseEntity<GameResponse> answer(
            @PathVariable UUID gameId,
            @Valid @RequestBody AnswerRequest request
    ) {
        GameResponse response = gameService.answer(gameId, request);
        return ResponseEntity.ok(response);
    }
}