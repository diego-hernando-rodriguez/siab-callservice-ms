package com.bolivar.siab.callservice.caracteristicas.repository;

import com.bolivar.siab.callservice.caracteristicas.models.CaracteristicaCausaLlamadaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CaracteristicaCausaLlamadaRepository extends JpaRepository<CaracteristicaCausaLlamadaEntity, CaracteristicaCausaLlamadaEntity.CaracteristicaCausaLlamadaId> {
    List<CaracteristicaCausaLlamadaEntity> findByLlamadaNumero(Long llamadaNumero);
    List<CaracteristicaCausaLlamadaEntity> findByLlamadaNumeroAndCodigoCampoNotIn(Long llamadaNumero, List<Integer> excludeFields);
    void deleteByLlamadaNumero(Long llamadaNumero);
}
