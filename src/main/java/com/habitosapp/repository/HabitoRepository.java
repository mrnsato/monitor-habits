package com.habitosapp.repository;

import com.habitosapp.model.Habito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitoRepository extends JpaRepository<Habito, Long> {
    List<Habito> findByUsuarioIdAndAtivoTrue(Long usuarioId);
    List<Habito> findByUsuarioId(Long usuarioId);
}
