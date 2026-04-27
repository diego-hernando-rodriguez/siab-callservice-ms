package com.bolivar.siab.callservice.casomanagement.repository;

import com.bolivar.siab.callservice.casomanagement.models.LlamadaExcepcionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LlamadaExcepcionRepository extends JpaRepository<LlamadaExcepcionEntity, LlamadaExcepcionEntity.LlamadaExcepcionId> {
    List<LlamadaExcepcionEntity> findByNumeroLlamada(Long numeroLlamada);
    long countByNumeroLlamada(Long numeroLlamada);
}