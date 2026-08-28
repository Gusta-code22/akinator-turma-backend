package gusta.turma.akinator.game;


import gusta.turma.akinator.pergunta.Pergunta;
import gusta.turma.akinator.pergunta.PerguntaRepository;
import gusta.turma.akinator.pergunta.PessoaResposta;
import gusta.turma.akinator.pergunta.PessoaRespostaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Component
public class AkinatorEngine {

    private final PessoaRespostaRepository pessoaRespostaRepository;
    private final PerguntaRepository perguntaRepository;

    public AkinatorEngine(
            PessoaRespostaRepository pessoaRespostaRepository,
            PerguntaRepository perguntaRepository
    ) {
        this.pessoaRespostaRepository = pessoaRespostaRepository;
        this.perguntaRepository = perguntaRepository;
    }

    public void processAnswer(Game game, Pergunta pergunta, Boolean answer) {
        List<PessoaResposta> respostas = pessoaRespostaRepository
                .findByPessoaInAndPergunta(game.getCandidatos(), pergunta);

        Map<Long, Boolean> respostaPorPessoaId = respostas.stream()
                .collect(Collectors.toMap(
                        r -> r.getPessoa().getId(),
                        PessoaResposta::getResposta
                ));

        game.getCandidatos().removeIf(pessoa -> {
            Boolean respostaCadastrada = respostaPorPessoaId.get(pessoa.getId());
            return respostaCadastrada == null || !respostaCadastrada.equals(answer);
        });

        game.getPerguntasUsadas().add(pergunta);
    }

    public Optional<Pergunta> chooseNextQuestion(Game game) {
        List<Pergunta> disponiveis = perguntaRepository.findAll().stream()
                .filter(p -> !game.getPerguntasUsadas().contains(p))
                .toList();

        if (disponiveis.isEmpty()) {
            return Optional.empty();
        }

        int index = ThreadLocalRandom.current().nextInt(disponiveis.size());
        return Optional.of(disponiveis.get(index));
    }
}