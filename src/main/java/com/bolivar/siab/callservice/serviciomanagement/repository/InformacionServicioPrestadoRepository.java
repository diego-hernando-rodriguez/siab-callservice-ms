package com.bolivar.siab.callservice.serviciomanagement.repository;

import com.bolivar.siab.callservice.serviciomanagement.models.InformacionServicioPrestadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InformacionServicioPrestadoRepository extends JpaRepository<InformacionServicioPrestadoEntity, InformacionServicioPrestadoEntity.InfServicioId> {
    List<InformacionServicioPrestadoEntity> findByNumeroAutorizacion(Long numeroAutorizacion);
}
