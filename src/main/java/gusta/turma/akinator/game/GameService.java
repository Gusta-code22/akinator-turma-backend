package gusta.turma.akinator.game;



import gusta.turma.akinator.pergunta.AnswerRequest;
import gusta.turma.akinator.pergunta.Pergunta;
import gusta.turma.akinator.pergunta.PerguntaRepository;
import gusta.turma.akinator.pergunta.QuestionDTO;
import gusta.turma.akinator.pessoa.PersonDTO;
import gusta.turma.akinator.pessoa.Pessoa;
import gusta.turma.akinator.pessoa.PessoaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final PessoaRepository pessoaRepository;
    private final PerguntaRepository perguntaRepository;
    private final AkinatorEngine engine;

    public GameService(
            GameRepository gameRepository,
            PessoaRepository pessoaRepository,
            PerguntaRepository perguntaRepository,
            AkinatorEngine engine
    ) {
        this.gameRepository = gameRepository;
        this.pessoaRepository = pessoaRepository;
        this.perguntaRepository = perguntaRepository;
        this.engine = engine;
    }

    @Transactional
    public GameResponse startGame() {
        Game game = new Game();

        List<Pessoa> todasAsPessoas = pessoaRepository.findAll();
        game.getCandidatos().addAll(todasAsPessoas);

        gameRepository.save(game);

        Optional<Pergunta> primeiraPergunta = engine.chooseNextQuestion(game);

        if (primeiraPergunta.isEmpty()) {
            game.setFinalizado(true);
            return GameResponse.gaveUp(game.getPublicId(), game.getCandidatos().size());
        }

        return GameResponse.nextQuestion(
                game.getPublicId(),
                toQuestionDTO(primeiraPergunta.get()),
                game.getCandidatos().size()
        );
    }

    @Transactional
    public GameResponse answer(UUID gameId, AnswerRequest request) {
        Game game = gameRepository.findByPublicId(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Jogo não encontrado: " + gameId));

        Pergunta pergunta = perguntaRepository.findById(request.questionId())
                .orElseThrow(() -> new IllegalArgumentException("Pergunta não encontrada: " + request.questionId()));

        engine.processAnswer(game, pergunta, request.answer());

        if (game.getCandidatos().size() == 1) {
            game.setFinalizado(true);
            Pessoa descoberta = game.getCandidatos().get(0);
            return GameResponse.discovered(game.getPublicId(), toPersonDTO(descoberta));
        }

        if (game.getCandidatos().isEmpty()) {
            game.setFinalizado(true);
            return GameResponse.gaveUp(game.getPublicId(), 0);
        }

        Optional<Pergunta> proximaPergunta = engine.chooseNextQuestion(game);

        if (proximaPergunta.isEmpty()) {
            game.setFinalizado(true);
            return GameResponse.gaveUp(game.getPublicId(), game.getCandidatos().size());
        }

        return GameResponse.nextQuestion(
                game.getPublicId(),
                toQuestionDTO(proximaPergunta.get()),
                game.getCandidatos().size()
        );
    }

    private QuestionDTO toQuestionDTO(Pergunta pergunta) {
        return new QuestionDTO(pergunta.getId(), pergunta.getTexto());
    }

    private PersonDTO toPersonDTO(Pessoa pessoa) {
        return new PersonDTO(pessoa.getId(), pessoa.getNome(), pessoa.getFoto());
    }
}