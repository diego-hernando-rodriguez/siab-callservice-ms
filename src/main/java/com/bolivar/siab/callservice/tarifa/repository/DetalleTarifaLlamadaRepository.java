package com.bolivar.siab.callservice.tarifa.repository;

import com.bolivar.siab.callservice.tarifa.models.DetalleTarifaLlamadaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DetalleTarifaLlamadaRepository extends JpaRepository<DetalleTarifaLlamadaEntity, Long> {
    List<DetalleTarifaLlamadaEntity> findByTarifaCodigo(Long tarifaCodigo);
}
