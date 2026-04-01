package com.habitosapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "progresso_diario", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"usuario_id", "data"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgressoDiario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private LocalDate data;

    @Builder.Default
    @Column(nullable = false)
    private Integer habitosConcluidos = 0;

    @Builder.Default
    @Column(nullable = false)
    private Integer totalHabitos = 0;

    @Builder.Default
    @Column(nullable = false)
    private Long pontosGanhos = 0L;

    @Transient
    public Double getPercentualConclusao() {
        if (this.totalHabitos == 0) {
            return 0.0;
        }
        return (this.habitosConcluidos.doubleValue() / this.totalHabitos) * 100;
    }

    @Transient
    public Boolean elixirRecompensa() {
        return this.getPercentualConclusao() >= 80.0;
    }
}
