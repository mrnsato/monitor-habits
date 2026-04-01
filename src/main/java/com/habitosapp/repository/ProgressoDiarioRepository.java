package com.habitosapp.repository;

import com.habitosapp.model.ProgressoDiario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProgressoDiarioRepository extends JpaRepository<ProgressoDiario, Long> {
    Optional<ProgressoDiario> findByUsuarioIdAndData(Long usuarioId, LocalDate data);
    List<ProgressoDiario> findByUsuarioIdOrderByDataDesc(Long usuarioId);
}
