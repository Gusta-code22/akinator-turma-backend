package gusta.turma.akinator.pergunta;

import jakarta.validation.constraints.NotNull;

public record AnswerRequest(
    @NotNull Long questionId,
    @NotNull Boolean answer
) {
}