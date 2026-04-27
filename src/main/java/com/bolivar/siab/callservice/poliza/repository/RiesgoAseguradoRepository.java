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

    /**
     * LLAMADA_DSP_USU_NUM_LOV1: Search users by document number or name.
     */
    @Query(nativeQuery = true, value =
        "SELECT numero_documento, nombres_apellidos, tipo_documento, preferencial " +
        "FROM NASIST.usuarios " +
        "WHERE numero_documento LIKE :query || '%' OR UPPER(nombres_apellidos) LIKE UPPER(:query) || '%' " +
        "AND ROWNUM <= 50")
    List<Object[]> findUsuariosByDocOrName(@Param("query") String query);

    /**
     * CONSULTA_EXISTENTES: Check if risk value exists in non-ASIS contracts.
     * Returns 'S' if exists, empty if not (inexistente).
     */
    @Query(nativeQuery = true, value =
        "SELECT 'S' FROM NASIST.riesgos_asegurados r, NASIST.tipos_dato_riesgo da " +
        "WHERE da.argumento_busqueda = 'S' " +
        "AND r.ramo_codigo = da.ramo_codigo AND r.producto_codigo = da.producto_codigo " +
        "AND r.codigo_campo = da.codigo_campo " +
        "AND da.ramo_codigo = NVL(:ramo, da.ramo_codigo) " +
        "AND da.producto_codigo = NVL(:producto, da.producto_codigo) " +
        "AND da.codigo_campo = NVL(:codigoCampo, da.codigo_campo) " +
        "AND r.valor_sin_ceros = LTRIM(:valor, '0') " +
        "AND r.cont_numero_contrato NOT LIKE '%ASIS%' " +
        "AND ROWNUM <= 1")
    List<Object[]> consultaExistentes(@Param("valor") String valor,
                                       @Param("ramo") String ramo,
                                       @Param("producto") String producto,
                                       @Param("codigoCampo") Integer codigoCampo);

    /**
     * RIESGOS_AUTOS: Search contracts for a plate (codigo_campo=1).
     * Used when the risk is a plate and there's a valid auto contract.
     */
    @Query(nativeQuery = true, value =
        "SELECT pr.DESCRIPCION as producto, pc.cont_numero_contrato as poliza, " +
        "pc.cont_fecha_inicio_vigencia as inicio, c.fecha_fin_vigencia as fin, " +
        "DECODE(c.estado,'CA','Cancelado','VG','Vigente',c.estado) as estado, " +
        "LTRIM(r.valor,'0') as valor_riesgo, " +
        "pc.numero_orden as num_orden, pc.tipo_contrato as tip_contrato, " +
        "r.riesgo_codigo as riesgo, r.ramo_codigo, r.producto_codigo, " +
        "ri.descripcion as desc_riesgo, pc.usu_numero_documento as numero_documento, " +
        "u.nombres_apellidos, u.tipo_documento, u.preferencial, " +
        "r.codigo_campo as cod_campo, r.valor as valor_riesgo_ori " +
        "FROM NASIST.riesgos ri, NASIST.productos pr, NASIST.usuarios u, NASIST.contratos c, " +
        "NASIST.personas_contrato pc, NASIST.riesgos_asegurados r " +
        "WHERE ri.codigo = r.riesgo_codigo AND pr.ramo_codigo = r.ramo_codigo AND pr.codigo = r.producto_codigo " +
        "AND pc.usu_tipo_documento = u.tipo_documento AND pc.usu_numero_documento = u.numero_documento " +
        "AND c.tipo_contrato = pc.tipo_contrato AND c.numero_contrato = pc.cont_numero_contrato " +
        "AND c.fecha_inicio_vigencia = pc.cont_fecha_inicio_vigencia " +
        "AND SYSDATE BETWEEN c.fecha_inicio_vigencia AND c.fecha_fin_vigencia " +
        "AND pc.TIPO_CONTRATO = r.CONT_TIPO_CONTRATO AND pc.CONT_NUMERO_CONTRATO = r.CONT_NUMERO_CONTRATO " +
        "AND pc.CONT_FECHA_INICIO_VIGENCIA = r.CONT_FECHA_INICIO_VIGENCIA " +
        "AND r.ramo_codigo = NVL(:ramo, r.ramo_codigo) " +
        "AND r.producto_codigo = NVL(:producto, r.producto_codigo) " +
        "AND r.valor_sin_ceros = LTRIM(:valor, '0') " +
        "AND r.cont_tipo_contrato = pc.tipo_contrato AND r.cont_numero_contrato = pc.cont_numero_contrato " +
        "AND r.cont_fecha_inicio_vigencia = pc.cont_fecha_inicio_vigencia " +
        "AND r.peco_numero_orden = pc.numero_orden AND r.peco_tipo_usuario = pc.tipo_usuario " +
        "AND pr.locge_codigo = :pais " +
        "AND ROWNUM <= 50 " +
        "ORDER BY pc.cont_fecha_inicio_vigencia DESC")
    List<Object[]> findRiesgosAutos(@Param("valor") String valor, @Param("ramo") String ramo,
                                     @Param("producto") String producto, @Param("pais") Long pais);

    /**
     * LLAMADA_DSP_USUARIO_LOV2: Contracts for a user with risk details.
     */
    @Query(nativeQuery = true, value =
        "SELECT pc.cont_numero_contrato, pc.cont_fecha_inicio_vigencia, c.fecha_fin_vigencia, " +
        "pc.numero_orden, pc.tipo_contrato, r.riesgo_codigo, r.valor, r.ramo_codigo, r.producto_codigo " +
        "FROM NASIST.personas_contrato pc, NASIST.contratos c, NASIST.riesgos_asegurados r " +
        "WHERE pc.usu_numero_documento = :numDoc AND pc.usu_tipo_documento = :tipoDoc " +
        "AND pc.tipo_contrato = c.tipo_contrato AND pc.cont_numero_contrato = c.numero_contrato " +
        "AND pc.cont_fecha_inicio_vigencia = c.fecha_inicio_vigencia " +
        "AND r.cont_tipo_contrato = pc.tipo_contrato AND r.cont_numero_contrato = pc.cont_numero_contrato " +
        "AND r.cont_fecha_inicio_vigencia = pc.cont_fecha_inicio_vigencia " +
        "AND r.peco_numero_orden = pc.numero_orden AND r.peco_tipo_usuario = pc.tipo_usuario " +
        "AND r.codigo_campo = :codigoCampo AND ROWNUM <= 50 " +
        "ORDER BY c.fecha_fin_vigencia DESC")
    List<Object[]> findContratosByUsuario(@Param("numDoc") String numDoc, @Param("tipoDoc") String tipoDoc,
                                           @Param("codigoCampo") Integer codigoCampo);
}
