package gusta.turma.akinator.pergunta;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pergunta")
@Data
public class Pergunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String texto;
}