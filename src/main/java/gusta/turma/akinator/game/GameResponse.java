package gusta.turma.akinator.game;

import gusta.turma.akinator.pergunta.QuestionDTO;
import gusta.turma.akinator.pessoa.PersonDTO;

import java.util.UUID;

public record GameResponse(
    UUID gameId,
    Boolean finished,
    QuestionDTO question,
    PersonDTO person
) {

    public static GameResponse nextQuestion(UUID gameId, QuestionDTO question) {
        return new GameResponse(gameId, false, question, null);
    }

    public static GameResponse discovered(UUID gameId, PersonDTO person) {
        return new GameResponse(gameId, true, null, person);
    }

    public static GameResponse gaveUp(UUID gameId) {
        return new GameResponse(gameId, true, null, null);
    }
}