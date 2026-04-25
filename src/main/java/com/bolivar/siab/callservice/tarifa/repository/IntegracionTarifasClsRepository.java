package com.bolivar.siab.callservice.tarifa.repository;

import com.bolivar.siab.callservice.tarifa.models.IntegracionTarifasClsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface IntegracionTarifasClsRepository extends JpaRepository<IntegracionTarifasClsEntity, Long> {
    Optional<IntegracionTarifasClsEntity> findByNumeroAutorizacion(Long numeroAutorizacion);
}
