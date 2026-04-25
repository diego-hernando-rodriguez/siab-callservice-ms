package com.bolivar.siab.callservice.proveedor.repository;

import com.bolivar.siab.callservice.proveedor.models.ProvCalificacionesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProvCalificacionesRepository extends JpaRepository<ProvCalificacionesEntity, Long> {
    List<ProvCalificacionesEntity> findByNumeroAutorizacion(Long numeroAutorizacion);
}
