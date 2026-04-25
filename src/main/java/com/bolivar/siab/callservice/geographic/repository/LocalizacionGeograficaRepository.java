package com.bolivar.siab.callservice.geographic.repository;

import com.bolivar.siab.callservice.geographic.models.LocalizacionGeograficaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LocalizacionGeograficaRepository extends JpaRepository<LocalizacionGeograficaEntity, LocalizacionGeograficaEntity.LocalizacionId> {

    Page<LocalizacionGeograficaEntity> findByNombreContainingIgnoreCaseAndTlgCodigo(String nombre, Integer tlgCodigo, Pageable pageable);

    List<LocalizacionGeograficaEntity> findByTlgCodigoOrderByNombreAsc(Integer tlgCodigo);

    /**
     * LLAMADA_LOCGE_CODIG_LOV5: Cities with department, filtered by country.
     * Replicates the Oracle Forms record group query exactly.
     */
    @Query(nativeQuery = true, value =
        "SELECT lg1.codigo, Initcap(substr(lg1.nombre,1,30)) as ciudad, " +
        "Initcap(substr(lg2.nombre,1,30)) as departamento, lg1.tlg_codigo " +
        "FROM localizaciones_geograficas lg1, componentes_geograficos cg, localizaciones_geograficas lg2 " +
        "WHERE lg1.tlg_codigo = 3 " +
        "AND lg1.codigo = cg.locg_codigo " +
        "AND cg.locg_tlg_codigo = 3 " +
        "AND cg.padre_locg_codigo = lg2.codigo " +
        "AND lg2.tlg_codigo = 2 " +
        "AND cg.padre_locg_tlg_codigo = 2 " +
        "AND pkg_migracion_pais.f_trae_pais(lg1.codigo, lg1.tlg_codigo) = :pais " +
        "AND pkg_migracion_pais.f_trae_pais(lg2.codigo, lg2.tlg_codigo) = :pais " +
        "AND UPPER(lg1.nombre) LIKE UPPER('%' || :nombre || '%') " +
        "ORDER BY 3, 2")
    List<Object[]> findCitiesWithDepartmentByPais(@Param("pais") Long pais, @Param("nombre") String nombre);

    /**
     * LLAMADA_RIESGO_CODI_LOV8: Risks for a given contract.
     */
    @Query(nativeQuery = true, value =
        "SELECT ra.riesgo_codigo, substr(r.descripcion,1,15) as desc_riesgo, " +
        "ra.codigo_campo, tdr.nombre_campo, ra.valor " +
        "FROM riesgos_asegurados ra, datos_riesgo dr, tipos_dato_riesgo tdr, riesgos r " +
        "WHERE ra.ramo_codigo = :ramo AND ra.producto_codigo = :producto " +
        "AND ra.cont_tipo_contrato = :tipcont " +
        "AND ra.cont_numero_contrato = :contrato " +
        "AND ra.cont_fecha_inicio_vigencia = TO_DATE(:fechaVigencia, 'YYYY-MM-DD') " +
        "AND ra.peco_numero_orden = :pecoOrden " +
        "AND tdr.ramo_codigo = ra.ramo_codigo AND tdr.producto_codigo = ra.producto_codigo " +
        "AND tdr.codigo_campo = ra.codigo_campo " +
        "AND ra.ramo_codigo = dr.ramo_codigo AND ra.producto_codigo = dr.producto_codigo " +
        "AND ra.codigo_campo = dr.codigo_campo AND ra.riesgo_codigo = dr.riesgo_codigo " +
        "AND ra.riesgo_codigo = r.codigo " +
        "AND ra.cont_numero_contrato NOT LIKE '%ASIS%' " +
        "ORDER BY ra.codigo_campo")
    List<Object[]> findRiesgosByContrato(
        @Param("ramo") String ramo, @Param("producto") String producto,
        @Param("tipcont") Integer tipcont, @Param("contrato") String contrato,
        @Param("fechaVigencia") String fechaVigencia, @Param("pecoOrden") Integer pecoOrden);

    /**
     * Simplified risk search by value (placa/cedula) - used when contract details are not yet known.
     */
    @Query(nativeQuery = true, value =
        "SELECT DISTINCT ra.riesgo_codigo, ra.valor, ra.codigo_campo, " +
        "ra.ramo_codigo, ra.producto_codigo, ra.cont_numero_contrato, " +
        "ra.cont_fecha_inicio_vigencia, ra.peco_numero_orden, ra.cont_tipo_contrato " +
        "FROM riesgos_asegurados ra " +
        "WHERE LTRIM(ra.valor, '0') = LTRIM(:valor, '0') " +
        "AND ra.cont_numero_contrato NOT LIKE '%ASIS%' " +
        "AND ROWNUM <= 50 " +
        "ORDER BY ra.cont_fecha_inicio_vigencia DESC")
    List<Object[]> findRiesgosByValor(@Param("valor") String valor);
}
