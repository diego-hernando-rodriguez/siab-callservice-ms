package com.bolivar.siab.callservice.tarifa.repository;

import com.bolivar.siab.callservice.tarifa.models.TarifaLlamadaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TarifaLlamadaRepository extends JpaRepository<TarifaLlamadaEntity, Long> {
    List<TarifaLlamadaEntity> findByLlamadaNumeroAndNumeroAutorizacion(Long llamadaNumero, Long numeroAutorizacion);
}
