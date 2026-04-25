package com.bolivar.siab.callservice.serviciomanagement.repository;

import com.bolivar.siab.callservice.serviciomanagement.models.IngresoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IngresoRepository extends JpaRepository<IngresoEntity, Long> {
    List<IngresoEntity> findByNumeroAutorizacion(Long numeroAutorizacion);
}
