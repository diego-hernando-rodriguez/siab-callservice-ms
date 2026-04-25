package com.bolivar.siab.callservice.serviciomanagement.repository;

import com.bolivar.siab.callservice.serviciomanagement.models.CartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CartaRepository extends JpaRepository<CartaEntity, Long> {
    List<CartaEntity> findByNumeroAutorizacion(Long numeroAutorizacion);
}
