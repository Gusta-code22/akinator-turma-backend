package gusta.turma.akinator.pergunta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRespostaRepository extends JpaRepository<PessoaResposta, Long> {
}
