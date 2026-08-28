package gusta.turma.akinator.game;


import gusta.turma.akinator.pergunta.Pergunta;
import gusta.turma.akinator.pessoa.Pessoa;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "game")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID publicId = UUID.randomUUID();

    @Column(nullable = false)
    private Boolean finalizado = false;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime criadoEm;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "game_candidatos",
        joinColumns = @JoinColumn(name = "game_id"),
        inverseJoinColumns = @JoinColumn(name = "pessoa_id")
    )
    private List<Pessoa> candidatos = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "game_perguntas_usadas",
        joinColumns = @JoinColumn(name = "game_id"),
        inverseJoinColumns = @JoinColumn(name = "pergunta_id")
    )
    private List<Pergunta> perguntasUsadas = new ArrayList<>();
}