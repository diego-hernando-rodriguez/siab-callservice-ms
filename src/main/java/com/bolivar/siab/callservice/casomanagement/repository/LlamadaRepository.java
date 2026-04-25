package com.bolivar.siab.callservice.casomanagement.repository;

import com.bolivar.siab.callservice.casomanagement.models.LlamadaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for LLAMADAS table with custom queries for case management.
 */
@Repository
public interface LlamadaRepository extends JpaRepository<LlamadaEntity, Long> {

    List<LlamadaEntity> findByContNumeroContratoAndRiesgoCodigo(String contNumero, String riesgoCodigo);

    /**
     * SQL-02: Find previous case by contract, riesgo, ramo, producto for characteristic copying.
     */
    @Query("SELECT l FROM LlamadaEntity l WHERE l.contNumeroContrato = :contNumero " +
           "AND l.riesgoCodigo = :riesgoCodigo AND l.ramoCodigo = :ramoCodigo " +
           "AND l.productoCodigo = :productoCodigo AND l.numero <> :excludeNumero " +
           "ORDER BY l.fechaLlamada DESC")
    List<LlamadaEntity> findPreviousCaseByContractAndRamo(
            @Param("contNumero") String contNumero,
            @Param("riesgoCodigo") String riesgoCodigo,
            @Param("ramoCodigo") Integer ramoCodigo,
            @Param("productoCodigo") Integer productoCodigo,
            @Param("excludeNumero") Long excludeNumero);

    /**
     * VALIDA_CASO_ATENDIDO: Find duplicate cases by city, contract, and risk.
     */
    @Query("SELECT l FROM LlamadaEntity l WHERE l.locgeCodigo = :locgeCodigo " +
           "AND l.contNumeroContrato = :contNumero AND l.riesgoCodigo = :riesgoCodigo " +
           "AND l.estadoLlamada NOT IN ('AN', 'CE') ORDER BY l.fechaLlamada DESC")
    List<LlamadaEntity> findDuplicateCases(
            @Param("locgeCodigo") Long locgeCodigo,
            @Param("contNumero") String contNumero,
            @Param("riesgoCodigo") String riesgoCodigo);

    Page<LlamadaEntity> findByContNumeroContratoContainingIgnoreCase(String contNumero, Pageable pageable);

    @Query("SELECT l FROM LlamadaEntity l WHERE l.usuNumeroDocumento = :documento ORDER BY l.fechaLlamada DESC")
    Page<LlamadaEntity> findByUsuarioDocumento(@Param("documento") String documento, Pageable pageable);

    Optional<LlamadaEntity> findByNumeroSiniestro(String numeroSiniestro);
}
