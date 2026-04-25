package com.bolivar.siab.callservice.serviciomanagement.repository;

import com.bolivar.siab.callservice.serviciomanagement.models.AdicionalesPrestadosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AdicionalesPrestadosRepository extends JpaRepository<AdicionalesPrestadosEntity, AdicionalesPrestadosEntity.AdicionalesPrestadosId> {
    List<AdicionalesPrestadosEntity> findByLlamadaNumeroAndNumeroAutorizacion(Long llamadaNumero, Long numeroAutorizacion);
    void deleteByLlamadaNumeroAndNumeroAutorizacion(Long llamadaNumero, Long numeroAutorizacion);
}
