package com.bolivar.siab.callservice.commons.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Map;

/**
 * Repository for invoking PL/SQL packages and standalone functions via JdbcTemplate.
 * Wraps all 16+ PL/SQL packages referenced by the Oracle Forms LLAMADA system.
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class StoredProcedureRepository {

    private final JdbcTemplate jdbcTemplate;

    // ========== PKG_VARIABLES_GLOBALES ==========

    public String getIndicadorSi() {
        return callStringFunction("PKG_VARIABLES_GLOBALES", "F_INDICADORSI");
    }

    public String getIndicadorNo() {
        return callStringFunction("PKG_VARIABLES_GLOBALES", "F_INDICADORNO");
    }

    public String getRetroNoEfectivaRetroLlamada() {
        return callStringFunction("PKG_VARIABLES_GLOBALES", "F_RETRONOEFECTIVA_RETROLLAMADA");
    }

    // ========== PKG_DESCRIPTORES ==========

    public String getDescriptorRamo(Integer ramoCodigo) {
        return callStringFunctionWithParam("PKG_DESCRIPTORES", "F_RAMO", "P_RAMO_CODIGO", ramoCodigo, Types.NUMERIC);
    }

    public String getDescriptorProducto(Integer ramoCodigo, Integer productoCodigo) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("NASIST")
                .withCatalogName("PKG_DESCRIPTORES")
                .withFunctionName("F_PRODUCTO")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("RETURN", Types.VARCHAR),
                        new SqlParameter("P_RAMO_CODIGO", Types.NUMERIC),
                        new SqlParameter("P_PRODUCTO_CODIGO", Types.NUMERIC));
        Map<String, Object> result = jdbcCall.execute(ramoCodigo, productoCodigo);
        return (String) result.get("RETURN");
    }

    public String getDescriptorCausa(Integer ramoCodigo, Integer productoCodigo, Long causaCodigo) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("NASIST")
                .withCatalogName("PKG_DESCRIPTORES")
                .withFunctionName("F_CAUSA")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("RETURN", Types.VARCHAR),
                        new SqlParameter("P_RAMO_CODIGO", Types.NUMERIC),
                        new SqlParameter("P_PRODUCTO_CODIGO", Types.NUMERIC),
                        new SqlParameter("P_CAUSA_CODIGO", Types.NUMERIC));
        Map<String, Object> result = jdbcCall.execute(ramoCodigo, productoCodigo, causaCodigo);
        return (String) result.get("RETURN");
    }

    public String getDescriptorCaracteristicas(Integer codigoCampo) {
        return callStringFunctionWithParam("PKG_DESCRIPTORES", "F_CARACTERISTICAS", "P_CODIGO_CAMPO", codigoCampo, Types.NUMERIC);
    }

    public String getDescriptorEntidad(Long locgeCodigo) {
        return callStringFunctionWithParam("PKG_DESCRIPTORES", "F_DESC_ENTIDAD", "P_LOCGE_CODIGO", locgeCodigo, Types.NUMERIC);
    }

    // ========== PKG_MIGRACION_PAIS ==========

    public String getPais(Long locgeCodigo, Integer tlgCodigo) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("NASIST")
                .withCatalogName("PKG_MIGRACION_PAIS")
                .withFunctionName("F_TRAE_PAIS")
                .declareParameters(
                        new SqlOutParameter("RETURN", Types.VARCHAR),
                        new SqlParameter("P_LOCGE_CODIGO", Types.NUMERIC),
                        new SqlParameter("P_TLG_CODIGO", Types.NUMERIC));
        Map<String, Object> result = jdbcCall.execute(locgeCodigo, tlgCodigo);
        return (String) result.get("RETURN");
    }

    // ========== PKG_UTILITARIOS ==========

    public String getValDominio(String dominio, String referencia) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("NASIST")
                .withCatalogName("PKG_UTILITARIOS")
                .withFunctionName("FN_VAL_DOMINIO")
                .declareParameters(
                        new SqlOutParameter("RETURN", Types.VARCHAR),
                        new SqlParameter("P_DOMINIO", Types.VARCHAR),
                        new SqlParameter("P_REFERENCIA", Types.VARCHAR));
        Map<String, Object> result = jdbcCall.execute(dominio, referencia);
        return (String) result.get("RETURN");
    }

    // ========== PKG_LLAMADA_GENERAL ==========

    public String getMechoqueHaceDiasDesc(Long llamadaNumero) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("NASIST")
                .withCatalogName("PKG_LLAMADA_GENERAL")
                .withProcedureName("PR_ME_CHOQUE_HACE_DIAS_DESC")
                .declareParameters(
                        new SqlParameter("P_LLAMADA_NUMERO", Types.NUMERIC),
                        new SqlOutParameter("P_RESULTADO", Types.VARCHAR));
        Map<String, Object> result = jdbcCall.execute(llamadaNumero);
        return (String) result.get("P_RESULTADO");
    }

    public String getCodigoCaractRefcod(Integer codigoCampo, Integer ramoCodigo, Integer productoCodigo) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("NASIST")
                .withCatalogName("PKG_LLAMADA_GENERAL")
                .withFunctionName("FUN_CODIGO_CARACT_REFCOD")
                .declareParameters(
                        new SqlOutParameter("RETURN", Types.VARCHAR),
                        new SqlParameter("P_CODIGO_CAMPO", Types.NUMERIC),
                        new SqlParameter("P_RAMO_CODIGO", Types.NUMERIC),
                        new SqlParameter("P_PRODUCTO_CODIGO", Types.NUMERIC));
        Map<String, Object> result = jdbcCall.execute(codigoCampo, ramoCodigo, productoCodigo);
        return (String) result.get("RETURN");
    }

    public void creaCaracteristicaTmp(Long llamadaNumero, Integer codigoCampo, String valor) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("NASIST")
                .withCatalogName("PKG_LLAMADA_GENERAL")
                .withProcedureName("PR_CREA_CARACTERISTICA_TMP")
                .declareParameters(
                        new SqlParameter("P_LLAMADA_NUMERO", Types.NUMERIC),
                        new SqlParameter("P_CODIGO_CAMPO", Types.NUMERIC),
                        new SqlParameter("P_VALOR", Types.VARCHAR));
        jdbcCall.execute(llamadaNumero, codigoCampo, valor);
    }

    // ========== PKG_GENERICOS ==========

    public String convierteNumeroMinutos(Integer minutos) {
        return callStringFunctionWithParam("PKG_GENERICOS", "CONVIERTE_NUMERO_MINUTOS", "P_MINUTOS", minutos, Types.NUMERIC);
    }

    public String getValorRiesgo(String contNumero, String riesgoCodigo, Integer posicion) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("NASIST")
                .withCatalogName("PKG_GENERICOS")
                .withFunctionName("F_VALOR_RIESGO")
                .declareParameters(
                        new SqlOutParameter("RETURN", Types.VARCHAR),
                        new SqlParameter("P_CONT_NUMERO", Types.VARCHAR),
                        new SqlParameter("P_RIESGO_CODIGO", Types.VARCHAR),
                        new SqlParameter("P_POSICION", Types.NUMERIC));
        Map<String, Object> result = jdbcCall.execute(contNumero, riesgoCodigo, posicion);
        return (String) result.get("RETURN");
    }

    public String consultaOrdenamiento(Integer ramoCodigo, Integer productoCodigo, Long causaCodigo, Integer codigoCampo) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("NASIST")
                .withCatalogName("PKG_GENERICOS")
                .withFunctionName("CONSULTA_ORDENAMIENTO")
                .declareParameters(
                        new SqlOutParameter("RETURN", Types.VARCHAR),
                        new SqlParameter("P_RAMO", Types.NUMERIC),
                        new SqlParameter("P_PRODUCTO", Types.NUMERIC),
                        new SqlParameter("P_CAUSA", Types.NUMERIC),
                        new SqlParameter("P_CAMPO", Types.NUMERIC));
        Map<String, Object> result = jdbcCall.execute(ramoCodigo, productoCodigo, causaCodigo, codigoCampo);
        return (String) result.get("RETURN");
    }

    public String getOrdenamientoCarServicio(Integer clservCodigo, Integer servCodigo, Integer codigoCampo) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("NASIST")
                .withCatalogName("PKG_GENERICOS")
                .withFunctionName("F_ORDENAMIENTO_CAR_SERVICIO")
                .declareParameters(
                        new SqlOutParameter("RETURN", Types.VARCHAR),
                        new SqlParameter("P_CLSERV", Types.NUMERIC),
                        new SqlParameter("P_SERV", Types.NUMERIC),
                        new SqlParameter("P_CAMPO", Types.NUMERIC));
        Map<String, Object> result = jdbcCall.execute(clservCodigo, servCodigo, codigoCampo);
        return (String) result.get("RETURN");
    }

    // ========== PKG_INTEGRACION_CLICKSOFTWARE ==========

    public String isHabilitadoClick(Integer ramoCodigo, Long locgeCodigo) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("NASIST")
                .withCatalogName("PKG_INTEGRACION_CLICKSOFTWARE")
                .withFunctionName("F_HABILITADO_CLICK")
                .declareParameters(
                        new SqlOutParameter("RETURN", Types.VARCHAR),
                        new SqlParameter("P_RAMO", Types.NUMERIC),
                        new SqlParameter("P_LOCGE", Types.NUMERIC));
        Map<String, Object> result = jdbcCall.execute(ramoCodigo, locgeCodigo);
        return (String) result.get("RETURN");
    }

    public String enviaClicksoftware(Long llamadaNumero) {
        return callStringFunctionWithParam("PKG_INTEGRACION_CLICKSOFTWARE", "F_ENVIA_CLICKSOFTWARE",
                "P_LLAMADA_NUMERO", llamadaNumero, Types.NUMERIC);
    }

    public void creacionLlamadas(Long llamadaNumero) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("NASIST")
                .withCatalogName("PKG_INTEGRACION_CLICKSOFTWARE")
                .withProcedureName("P_CREACION_LLAMADAS")
                .declareParameters(new SqlParameter("P_LLAMADA_NUMERO", Types.NUMERIC));
        jdbcCall.execute(llamadaNumero);
    }

    // ========== PKG_INSERTAR ==========

    public String insertarObservacionesCaso(Long llamadaNumero, Long numeroSiniestro, String observaciones) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("NASIST")
                .withCatalogName("PKG_INSERTAR")
                .withProcedureName("PR_INSERTAR_OBSERVACIONES_CASO")
                .declareParameters(
                        new SqlParameter("P_LLAMADA_NUMERO", Types.NUMERIC),
                        new SqlParameter("P_NUMERO_SINIESTRO", Types.NUMERIC),
                        new SqlParameter("P_OBSERVACIONES", Types.LONGVARCHAR),
                        new SqlOutParameter("P_ERROR", Types.VARCHAR));
        Map<String, Object> result = jdbcCall.execute(llamadaNumero, numeroSiniestro, observaciones);
        return (String) result.get("P_ERROR");
    }

    // ========== P_PRODUCTOS_CONSULTA (standalone procedure) ==========

    /**
     * Calls P_PRODUCTOS_CONSULTA which populates T_PRODUCTOS_CONSULTA temp table.
     * Then reads the results from that table.
     */
    public void ejecutarProductosConsulta(String ramoCodigo, String productoCodigo, Long pais, String riesgoValor, Integer codigoCampo) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("NASIST")
                .withProcedureName("P_PRODUCTOS_CONSULTA")
                .declareParameters(
                        new SqlParameter("P_LLAMADA_RAMO_CODIGO", Types.VARCHAR),
                        new SqlParameter("P_LLAMADA_PRODUCTO_CODIGO", Types.VARCHAR),
                        new SqlParameter("P_LLAMADA_PAIS", Types.NUMERIC),
                        new SqlParameter("P_RIESGO_VALOR", Types.VARCHAR),
                        new SqlParameter("P_RIESGO_CODIGO_CAMPO", Types.NUMERIC));
        jdbcCall.execute(ramoCodigo, productoCodigo, pais, riesgoValor, codigoCampo);
    }

    /**
     * Calls P_RIESGOS_CEDULA which populates T_RIESGOS_CEDULA temp table.
     */
    public void ejecutarRiesgosCedula(String ramoCodigo, String productoCodigo,
                                       String ramoCodigo2, String productoCodigo2,
                                       String existente, String riesgoValor, Long pais) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("NASIST")
                .withProcedureName("P_RIESGOS_CEDULA")
                .declareParameters(
                        new SqlParameter("P_RAMO_CODIGO", Types.VARCHAR),
                        new SqlParameter("P_PRODUCTO_CODIGO", Types.VARCHAR),
                        new SqlParameter("P_RAMO_CODIGO_2", Types.VARCHAR),
                        new SqlParameter("P_PRODUCTO_CODIGO_2", Types.VARCHAR),
                        new SqlParameter("P_LLAMADA_EXISTENTE", Types.VARCHAR),
                        new SqlParameter("P_RIESGO_VALOR", Types.VARCHAR),
                        new SqlParameter("P_PAIS", Types.NUMERIC));
        jdbcCall.execute(ramoCodigo, productoCodigo, ramoCodigo2, productoCodigo2, existente, riesgoValor, pais);
    }

    // ========== PKG_GEO_DIRECCION_INTEGRA ==========

    public String getDireccionLimpia(String direccion) {
        return callStringFunctionWithParam("PKG_GEO_DIRECCION_INTEGRA", "FU_DIRECCION_LIMPIA",
                "P_DIRECCION", direccion, Types.VARCHAR);
    }

    public String getDireccionUnica(Integer indice) {
        return callStringFunctionWithParam("PKG_GEO_DIRECCION_INTEGRA", "FU_DIRECCION_UNICA",
                "P_INDICE", indice, Types.NUMERIC);
    }

    public String getRegistrosCoordenadas(Long locgeCodigo, String direccion) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("NASIST")
                .withCatalogName("PKG_GEO_DIRECCION_INTEGRA")
                .withFunctionName("FU_REGISTROS_COORDENADAS")
                .declareParameters(
                        new SqlOutParameter("RETURN", Types.VARCHAR),
                        new SqlParameter("P_LOCGE_CODIGO", Types.NUMERIC),
                        new SqlParameter("P_DIRECCION", Types.VARCHAR));
        Map<String, Object> result = jdbcCall.execute(locgeCodigo, direccion);
        return (String) result.get("RETURN");
    }

    public String getNombreCiudad(Long locgeCodigo) {
        return callStringFunctionWithParam("PKG_GEO_DIRECCION_INTEGRA", "FU_NOMBRE_CIUDAD",
                "P_LOCGE_CODIGO", locgeCodigo, Types.NUMERIC);
    }

    public void busquedaDireccionIntegra(Long locgeCodigo, String direccion, String usuario) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("NASIST")
                .withCatalogName("PKG_GEO_DIRECCION_INTEGRA")
                .withProcedureName("PR_BUSQUEDA_DIRECCION_INTEGRA")
                .declareParameters(
                        new SqlParameter("P_LOCGE_CODIGO", Types.NUMERIC),
                        new SqlParameter("P_DIRECCION", Types.VARCHAR),
                        new SqlParameter("P_USUARIO", Types.VARCHAR));
        jdbcCall.execute(locgeCodigo, direccion, usuario);
    }

    // ========== PKG_PICO_PLACA ==========

    public String getTipoRestriccion(Long locgeCodigo) {
        return callStringFunctionWithParam("PKG_PICO_PLACA", "F_TRAE_TIPO_RESTRICCION",
                "P_LOCGE_CODIGO", locgeCodigo, Types.NUMERIC);
    }

    public String getPicoPlaca(String placa, Long locgeCodigo) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("NASIST")
                .withCatalogName("PKG_PICO_PLACA")
                .withFunctionName("F_TRAE_PICO_PLACA")
                .declareParameters(
                        new SqlOutParameter("RETURN", Types.VARCHAR),
                        new SqlParameter("P_PLACA", Types.VARCHAR),
                        new SqlParameter("P_LOCGE_CODIGO", Types.NUMERIC));
        Map<String, Object> result = jdbcCall.execute(placa, locgeCodigo);
        return (String) result.get("RETURN");
    }

    public String getRestriccion(String placa, Long locgeCodigo) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("NASIST")
                .withCatalogName("PKG_PICO_PLACA")
                .withFunctionName("F_TRAE_RESTRICCION")
                .declareParameters(
                        new SqlOutParameter("RETURN", Types.VARCHAR),
                        new SqlParameter("P_PLACA", Types.VARCHAR),
                        new SqlParameter("P_LOCGE_CODIGO", Types.NUMERIC));
        Map<String, Object> result = jdbcCall.execute(placa, locgeCodigo);
        return (String) result.get("RETURN");
    }

    // ========== PKG_GLOBALES ==========

    public Long getSegundos() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("NASIST")
                .withCatalogName("PKG_GLOBALES")
                .withFunctionName("F_SEGUNDOS")
                .declareParameters(new SqlOutParameter("RETURN", Types.NUMERIC));
        Map<String, Object> result = jdbcCall.execute();
        Number val = (Number) result.get("RETURN");
        return val != null ? val.longValue() : null;
    }

    // ========== PKG_CAMBIA_PROPIEDADES ==========

    public void cambiaPropiedades(Long llamadaNumero, String estadoLlamada, String estadoServicio) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("NASIST")
                .withCatalogName("PKG_CAMBIA_PROPIEDADES")
                .withProcedureName("PR_CAMBIA_PROPIEDADES")
                .declareParameters(
                        new SqlParameter("P_LLAMADA_NUMERO", Types.NUMERIC),
                        new SqlParameter("P_ESTADO_LLAMADA", Types.VARCHAR),
                        new SqlParameter("P_ESTADO_SERVICIO", Types.VARCHAR));
        jdbcCall.execute(llamadaNumero, estadoLlamada, estadoServicio);
    }

    // ========== PKG_CORREOS ==========

    public void enviarCorreo(String destinatario, String asunto, String cuerpo, String tipo) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("NASIST")
                .withCatalogName("PKG_CORREOS")
                .withProcedureName("PR_ENVIA_CORREO")
                .declareParameters(
                        new SqlParameter("P_DESTINATARIO", Types.VARCHAR),
                        new SqlParameter("P_ASUNTO", Types.VARCHAR),
                        new SqlParameter("P_CUERPO", Types.VARCHAR),
                        new SqlParameter("P_TIPO", Types.VARCHAR));
        jdbcCall.execute(destinatario, asunto, cuerpo, tipo);
    }

    // ========== PKG_CONDUCTOR_ELEGIDO ==========

    public String getPolizaNumeroOrden(String contNumero, Long pecoNumeroOrden) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("NASIST")
                .withCatalogName("PKG_CONDUCTOR_ELEGIDO")
                .withFunctionName("FU_POLIZA_NUMERO_ORDEN")
                .declareParameters(
                        new SqlOutParameter("RETURN", Types.VARCHAR),
                        new SqlParameter("P_CONT_NUMERO", Types.VARCHAR),
                        new SqlParameter("P_PECO_NUMERO_ORDEN", Types.NUMERIC));
        Map<String, Object> result = jdbcCall.execute(contNumero, pecoNumeroOrden);
        return (String) result.get("RETURN");
    }

    // ========== STANDALONE FUNCTIONS ==========

    public String validaFuncionario(String numeroDocumento, String tipoDocumento) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("NASIST")
                .withFunctionName("F_VALIDA_FUNCIONARIO")
                .declareParameters(
                        new SqlOutParameter("RETURN", Types.VARCHAR),
                        new SqlParameter("P_NUMERO_DOCUMENTO", Types.VARCHAR),
                        new SqlParameter("P_TIPO_DOCUMENTO", Types.VARCHAR));
        Map<String, Object> result = jdbcCall.execute(numeroDocumento, tipoDocumento);
        return (String) result.get("RETURN");
    }

    public Long getConsecutivoSiab(String tableName) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("NASIST")
                .withFunctionName("F_CONSECUTIVO_SIAB")
                .declareParameters(
                        new SqlOutParameter("RETURN", Types.NUMERIC),
                        new SqlParameter("P_TABLE_NAME", Types.VARCHAR));
        Map<String, Object> result = jdbcCall.execute(tableName);
        Number val = (Number) result.get("RETURN");
        return val != null ? val.longValue() : null;
    }

    public String getCiudadRamoClick(Long locgeCodigo, Integer ramoCodigo) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("NASIST")
                .withFunctionName("FU_CIUDAD_RAMO_CLICK")
                .declareParameters(
                        new SqlOutParameter("RETURN", Types.VARCHAR),
                        new SqlParameter("P_LOCGE_CODIGO", Types.NUMERIC),
                        new SqlParameter("P_RAMO_CODIGO", Types.NUMERIC));
        Map<String, Object> result = jdbcCall.execute(locgeCodigo, ramoCodigo);
        return (String) result.get("RETURN");
    }

    public String evaluaPideIdTitular(String contNumero, Long pecoNumeroOrden) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("NASIST")
                .withFunctionName("FU_EVALUA_PIDE_ID_TITULAR")
                .declareParameters(
                        new SqlOutParameter("RETURN", Types.VARCHAR),
                        new SqlParameter("P_CONT_NUMERO", Types.VARCHAR),
                        new SqlParameter("P_PECO_NUMERO_ORDEN", Types.NUMERIC));
        Map<String, Object> result = jdbcCall.execute(contNumero, pecoNumeroOrden);
        return (String) result.get("RETURN");
    }

    public String getRiesgosCargue(String contNumero, String riesgoCodigo, Integer posicion) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("NASIST")
                .withFunctionName("F_RIESGOS_CARGUE")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("RETURN", Types.VARCHAR),
                        new SqlParameter("P_CONT_NUMERO", Types.VARCHAR),
                        new SqlParameter("P_RIESGO_CODIGO", Types.VARCHAR),
                        new SqlParameter("P_POSICION", Types.NUMERIC));
        Map<String, Object> result = jdbcCall.execute(contNumero, riesgoCodigo, posicion);
        return (String) result.get("RETURN");
    }

    /**
     * Full F_RIESGOS_CARGUE with all 8 parameters as defined in the Oracle function.
     */
    public String getRiesgosCargueCompleto(String ramoCodigo, String productoCodigo, String riesgoCodigo,
                                            Integer codigoCampo, Integer tipContrato, String contNumero,
                                            java.util.Date fechaInicioVigencia, Long pecoNumeroOrden) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("NASIST")
                .withFunctionName("F_RIESGOS_CARGUE")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("RETURN", Types.VARCHAR),
                        new SqlParameter("P_RAMO_CODIGO", Types.VARCHAR),
                        new SqlParameter("P_PRODUCTO_CODIGO", Types.VARCHAR),
                        new SqlParameter("P_RIESGO_CODIGO", Types.VARCHAR),
                        new SqlParameter("P_CODIGO_CAMPO", Types.NUMERIC),
                        new SqlParameter("P_CONT_TIPO_CONTRATO", Types.NUMERIC),
                        new SqlParameter("P_CONT_NUMERO_CONTRATO", Types.VARCHAR),
                        new SqlParameter("P_CONT_FECHA_INICIO_VIGENCIA", Types.DATE),
                        new SqlParameter("P_PECO_NUMERO_ORDEN", Types.NUMERIC));
        Map<String, Object> result = jdbcCall.execute(ramoCodigo, productoCodigo, riesgoCodigo,
                codigoCampo, tipContrato, contNumero, fechaInicioVigencia, pecoNumeroOrden);
        return (String) result.get("RETURN");
    }

    // ========== PK_GENERAR_INFORMACION ==========

    public String consultaCarNombre(Integer tipo, Integer campo) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("NASIST")
                .withCatalogName("PK_GENERAR_INFORMACION")
                .withFunctionName("CONSULTA_CAR_NOMBRE")
                .declareParameters(
                        new SqlOutParameter("RETURN", Types.VARCHAR),
                        new SqlParameter("P_TIPO", Types.NUMERIC),
                        new SqlParameter("P_CAMPO", Types.NUMERIC));
        Map<String, Object> result = jdbcCall.execute(tipo, campo);
        return (String) result.get("RETURN");
    }

    // ========== GENERIC DESCRIPTOR ==========

    /**
     * Generic descriptor resolver that routes to the appropriate PKG_DESCRIPTORES function
     * based on the tipo parameter.
     *
     * @param tipo   one of RAMO, PRODUCTO, CAUSA, ENTIDAD, CARACTERISTICAS
     * @param codigo the numeric code to resolve
     * @return the descriptor string, or null if not found
     */
    public String getDescriptor(String tipo, String codigo) {
        if (tipo == null || codigo == null) {
            return null;
        }
        return switch (tipo.toUpperCase()) {
            case "RAMO" -> getDescriptorRamo(Integer.valueOf(codigo));
            case "PRODUCTO" -> getDescriptorProducto(0, Integer.valueOf(codigo));
            case "CAUSA" -> getDescriptorCausa(0, 0, Long.valueOf(codigo));
            case "ENTIDAD" -> getDescriptorEntidad(Long.valueOf(codigo));
            case "CARACTERISTICAS" -> getDescriptorCaracteristicas(Integer.valueOf(codigo));
            default -> {
                log.warn("Tipo de descriptor no soportado: {}", tipo);
                yield null;
            }
        };
    }

    // ========== HELPER METHODS ==========

    private String callStringFunction(String packageName, String functionName) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("NASIST")
                .withCatalogName(packageName)
                .withFunctionName(functionName)
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(new SqlOutParameter("RETURN", Types.VARCHAR));
        Map<String, Object> result = jdbcCall.execute();
        return (String) result.get("RETURN");
    }

    private <T> String callStringFunctionWithParam(String packageName, String functionName,
                                                     String paramName, T paramValue, int sqlType) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("NASIST")
                .withCatalogName(packageName)
                .withFunctionName(functionName)
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("RETURN", Types.VARCHAR),
                        new SqlParameter(paramName, sqlType));
        Map<String, Object> result = jdbcCall.execute(paramValue);
        return (String) result.get("RETURN");
    }
}
