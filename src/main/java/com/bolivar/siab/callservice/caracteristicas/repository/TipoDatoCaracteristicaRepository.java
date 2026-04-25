package com.bolivar.siab.callservice.caracteristicas.repository;

import com.bolivar.siab.callservice.caracteristicas.models.TipoDatoCaracteristicaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TipoDatoCaracteristicaRepository extends JpaRepository<TipoDatoCaracteristicaEntity, Integer> {

    /**
     * SQL-03: Find active characteristics for a cause, joining TIPOS_DATO_CARACTERISTICA with DATOS_CARACTERISTICA_CAUSA.
     * Ordered by ORDEN_APARICION, excluding fields 141, 142, 143.
     */
    @Query("SELECT t FROM TipoDatoCaracteristicaEntity t WHERE t.codigoCampo IN " +
           "(SELECT d.codigoCampo FROM DatoCaracteristicaCausaEntity d WHERE d.ramoCodigo = :ramo " +
           "AND d.productoCodigo = :producto AND d.causaCodigo = :causa AND d.estado = 'A') " +
           "AND t.codigoCampo NOT IN (141, 142, 143) AND t.estado = 'A' " +
           "ORDER BY t.codigoCampo")
    List<TipoDatoCaracteristicaEntity> findActiveCharacteristicsByCause(
            @Param("ramo") Integer ramo,
            @Param("producto") Integer producto,
            @Param("causa") Long causa);
}
