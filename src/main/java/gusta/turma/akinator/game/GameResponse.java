package gusta.turma.akinator.game;

import gusta.turma.akinator.pergunta.QuestionDTO;
import gusta.turma.akinator.pessoa.PersonDTO;

import java.util.UUID;

public record GameResponse(
        UUID gameId,
        Boolean finished,
        QuestionDTO question,
        PersonDTO person,
        Integer candidatesRemaining
) {

    public static GameResponse nextQuestion(UUID gameId, QuestionDTO question, int candidatesRemaining) {
        return new GameResponse(gameId, false, question, null, candidatesRemaining);
    }

    public static GameResponse discovered(UUID gameId, PersonDTO person) {
        return new GameResponse(gameId, true, null, person, 1);
    }

    public static GameResponse gaveUp(UUID gameId, int candidatesRemaining) {
        return new GameResponse(gameId, true, null, null, candidatesRemaining);
    }
}