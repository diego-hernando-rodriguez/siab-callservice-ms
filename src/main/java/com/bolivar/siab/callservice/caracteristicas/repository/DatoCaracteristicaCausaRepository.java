package com.bolivar.siab.callservice.caracteristicas.repository;

import com.bolivar.siab.callservice.caracteristicas.models.DatoCaracteristicaCausaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DatoCaracteristicaCausaRepository extends JpaRepository<DatoCaracteristicaCausaEntity, DatoCaracteristicaCausaEntity.DatoCaracteristicaCausaId> {
    List<DatoCaracteristicaCausaEntity> findByRamoCodigoAndProductoCodigoAndCausaCodigoAndEstadoOrderByOrdenAparicion(
            Integer ramo, Integer producto, Long causa, String estado);
}
