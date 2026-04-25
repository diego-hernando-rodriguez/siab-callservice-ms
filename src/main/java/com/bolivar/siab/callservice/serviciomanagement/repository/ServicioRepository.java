package com.bolivar.siab.callservice.serviciomanagement.repository;

import com.bolivar.siab.callservice.serviciomanagement.models.ServicioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ServicioRepository extends JpaRepository<ServicioEntity, Integer> {
    List<ServicioEntity> findByEstado(String estado);
}
