package gusta.turma.akinator.pergunta;

import gusta.turma.akinator.pessoa.Pessoa;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "pessoa_resposta",
    uniqueConstraints = @UniqueConstraint(columnNames = {"pessoa_id", "pergunta_id"})
)
@Data
public class PessoaResposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pergunta_id")
    private Pergunta pergunta;

    @Column(nullable = false)
    private Boolean resposta;
}