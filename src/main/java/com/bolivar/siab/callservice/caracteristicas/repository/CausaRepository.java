package com.bolivar.siab.callservice.caracteristicas.repository;

import com.bolivar.siab.callservice.caracteristicas.models.CausaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CausaRepository extends JpaRepository<CausaEntity, Long> {
    List<CausaEntity> findByRamoCodigoAndProductoCodigoAndEstado(Integer ramo, Integer producto, String estado);
}
