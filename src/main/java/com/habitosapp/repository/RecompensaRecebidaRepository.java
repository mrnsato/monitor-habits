package com.habitosapp.repository;

import com.habitosapp.model.RecompensaRecebida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecompensaRecebidaRepository extends JpaRepository<RecompensaRecebida, Long> {
    List<RecompensaRecebida> findByUsuarioIdOrderByDataDesc(Long usuarioId);
}
