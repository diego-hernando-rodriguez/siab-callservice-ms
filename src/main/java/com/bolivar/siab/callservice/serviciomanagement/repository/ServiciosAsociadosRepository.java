package com.bolivar.siab.callservice.serviciomanagement.repository;

import com.bolivar.siab.callservice.serviciomanagement.models.ServiciosAsociadosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ServiciosAsociadosRepository extends JpaRepository<ServiciosAsociadosEntity, Long> {
    List<ServiciosAsociadosEntity> findByRamoCodigoAndProductoCodigoAndCausaCodigo(Integer ramo, Integer producto, Long causa);
}
