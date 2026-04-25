package com.bolivar.siab.callservice.tarifa.repository;

import com.bolivar.siab.callservice.tarifa.models.RutasIntermediasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RutasIntermediasRepository extends JpaRepository<RutasIntermediasEntity, Long> {
    List<RutasIntermediasEntity> findByLlamadaNumeroAndNumeroAutorizacionOrderBySecuencia(Long llamadaNumero, Long numeroAutorizacion);
}
