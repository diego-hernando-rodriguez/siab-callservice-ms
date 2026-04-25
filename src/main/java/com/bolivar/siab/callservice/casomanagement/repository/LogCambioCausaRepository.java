package com.bolivar.siab.callservice.casomanagement.repository;

import com.bolivar.siab.callservice.casomanagement.models.LogCambioCausaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LogCambioCausaRepository extends JpaRepository<LogCambioCausaEntity, Long> {
    List<LogCambioCausaEntity> findByNumeroLlamadaOrderByFechaCambioDesc(Long numeroLlamada);
}
