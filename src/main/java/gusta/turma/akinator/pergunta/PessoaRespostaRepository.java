package gusta.turma.akinator.pergunta;

import gusta.turma.akinator.pessoa.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaRespostaRepository extends JpaRepository<PessoaResposta, Long> {
    List<PessoaResposta> findByPessoaInAndPergunta(List<Pessoa> pessoas, Pergunta pergunta);
}
