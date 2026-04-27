package com.bolivar.siab.callservice.poliza.repository;

import com.bolivar.siab.callservice.poliza.models.RiesgoAseguradoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RiesgoAseguradoRepository extends JpaRepository<RiesgoAseguradoEntity, RiesgoAseguradoEntity.RiesgoAseguradoId> {

    /**
     * Search by VALOR using LTRIM logic (removes leading zeros), excluding non-identifying fields.
     * Returns raw rows to avoid Hibernate entity mapping issues with composite PK.
     */
    @Query(value =
        "SELECT DISTINCT ra.riesgo_codigo, ra.valor, ra.codigo_campo, " +
        "ra.ramo_codigo, ra.producto_codigo, ra.cont_numero_contrato, " +
        "ra.cont_fecha_inicio_vigencia, ra.peco_numero_orden, ra.cont_tipo_contrato, ra.valor_sin_ceros " +
        "FROM NASIST.RIESGOS_ASEGURADOS ra " +
        "WHERE LTRIM(ra.VALOR, '0') = LTRIM(:valor, '0') " +
        "AND ra.CODIGO_CAMPO NOT IN (48, 4, 81) " +
        "AND ra.CONT_NUMERO_CONTRATO NOT LIKE '%ASIS%' " +
        "AND ROWNUM <= 50 " +
        "ORDER BY ra.CONT_FECHA_INICIO_VIGENCIA DESC",
        nativeQuery = true)
    List<Object[]> findByValorSinCeros(@Param("valor") String valor);

    List<RiesgoAseguradoEntity> findByContNumeroContrato(String contNumeroContrato);

    List<RiesgoAseguradoEntity> findByContNumeroContratoAndRiesgoCodigo(String contNumeroContrato, String riesgoCodigo);

    /**
     * RIESGO_CODIGO_CAMP_LOV12: Searchable fields for a ramo/producto.
     * Returns codigo_campo, nombre_campo, riesgo_codigo where argumento_busqueda = 'S'.
     */
    @Query(nativeQuery = true, value =
        "SELECT DISTINCT td.codigo_campo, td.nombre_campo, dr.riesgo_codigo " +
        "FROM tipos_dato_riesgo td, datos_riesgo dr " +
        "WHERE td.ramo_codigo = :ramo AND td.producto_codigo = :producto " +
        "AND dr.ramo_codigo = td.ramo_codigo AND dr.producto_codigo = td.producto_codigo " +
        "AND td.codigo_campo = dr.codigo_campo AND td.argumento_busqueda = 'S'")
    List<Object[]> findCamposBusqueda(@Param("ramo") String ramo, @Param("producto") String producto);

    /**
     * RG_RIESGO / LOV_RIESGOS: All searchable field types from TIPOS_DATO_ASEGURADO.
     * This is the global list shown when ramo/producto are not yet known.
     */
    @Query(nativeQuery = true, value =
        "SELECT DISTINCT td.codigo_campo, td.nombre_campo " +
        "FROM tipos_dato_asegurado td " +
        "WHERE EXISTS (SELECT 1 FROM tipos_dato_riesgo t1 " +
        "              WHERE t1.argumento_busqueda = 'S' " +
        "                AND t1.codigo_campo = td.codigo_campo) " +
        "ORDER BY td.codigo_campo")
    List<Object[]> findAllCamposBusqueda();

    /**
     * CGFK$CHK_LLAMADA_LLAMADA_PR2: Get user data for a contract.
     * Joins PERSONAS_CONTRATO with USUARIOS to get document, name, preferencial.
     */
    @Query(nativeQuery = true, value =
        "SELECT PECO.USU_TIPO_DOCUMENTO, PECO.USU_NUMERO_DOCUMENTO, " +
        "substr(USU.NOMBRES_APELLIDOS,1,40) as nombre_usuario, USU.PREFERENCIAL, " +
        "substr(USU.NOMBRES_APELLIDOS,1,40) as nombre_tomador " +
        "FROM PERSONAS_CONTRATO PECO, USUARIOS USU " +
        "WHERE PECO.CONT_NUMERO_CONTRATO = :contrato " +
        "AND PECO.USU_TIPO_DOCUMENTO = USU.TIPO_DOCUMENTO " +
        "AND PECO.USU_NUMERO_DOCUMENTO = USU.NUMERO_DOCUMENTO " +
        "AND ROWNUM <= 1")
    List<Object[]> findDatosUsuarioContrato(@Param("contrato") String contrato);
}
