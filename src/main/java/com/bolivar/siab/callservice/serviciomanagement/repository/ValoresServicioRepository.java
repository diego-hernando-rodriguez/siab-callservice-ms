package com.bolivar.siab.callservice.serviciomanagement.repository;

import com.bolivar.siab.callservice.serviciomanagement.models.ValoresServicioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ValoresServicioRepository extends JpaRepository<ValoresServicioEntity, Long> {
    Optional<ValoresServicioEntity> findByServCodigoAndClservCodigoAndRamoCodigoAndProductoCodigoAndCausaCodigoAndLocgeCodigo(
            Integer servCodigo, Integer clservCodigo, Integer ramoCodigo, Integer productoCodigo, Long causaCodigo, Long locgeCodigo);
    List<ValoresServicioEntity> findByServCodigoAndClservCodigo(Integer servCodigo, Integer clservCodigo);
}
