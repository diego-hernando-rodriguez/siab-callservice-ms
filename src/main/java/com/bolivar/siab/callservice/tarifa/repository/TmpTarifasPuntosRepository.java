package com.bolivar.siab.callservice.tarifa.repository;

import com.bolivar.siab.callservice.tarifa.models.TmpTarifasPuntosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TmpTarifasPuntosRepository extends JpaRepository<TmpTarifasPuntosEntity, Long> {
    List<TmpTarifasPuntosEntity> findByLlamadaNumeroAndNumeroAutorizacion(Long llamadaNumero, Long numeroAutorizacion);
}
