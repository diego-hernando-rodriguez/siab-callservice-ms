package com.bolivar.siab.callservice.poliza.services.impl;

import com.bolivar.siab.callservice.commons.repository.StoredProcedureRepository;
import com.bolivar.siab.callservice.poliza.dto.*;
import com.bolivar.siab.callservice.poliza.models.*;
import com.bolivar.siab.callservice.poliza.repository.*;
import com.bolivar.siab.callservice.poliza.services.PolizaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PolizaServiceImpl implements PolizaService {

    private final RiesgoAseguradoRepository riesgoRepository;
    private final PersonaContratoRepository personaContratoRepository;
    private final StoredProcedureRepository storedProcedureRepository;
    private final org.springframework.jdbc.core.JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(readOnly = true)
    public RiesgoBusquedaResponseDTO searchRisks(String valor) {
        log.info("Searching risks by valor: {}", valor);

        // Native query returns Object[] rows to avoid Hibernate composite PK mapping issues
        List<Object[]> rows = riesgoRepository.findByValorSinCeros(valor);
        boolean encontrado = !rows.isEmpty();

        List<RiesgoAseguradoDTO> riesgos = new ArrayList<>();
        for (Object[] row : rows) {
            riesgos.add(RiesgoAseguradoDTO.builder()
                .riesgoCodigo(row[0] != null ? row[0].toString() : null)
                .valor(row[1] != null ? row[1].toString() : null)
                .codigoCampo(row[2] != null ? Integer.parseInt(row[2].toString()) : null)
                .ramoCodigo(row[3] != null ? Integer.parseInt(row[3].toString()) : null)
                .productoCodigo(row[4] != null ? Integer.parseInt(row[4].toString()) : null)
                .contNumero(row[5] != null ? row[5].toString() : null)
                .pecoNumeroOrden(row[7] != null ? Long.parseLong(row[7].toString()) : null)
                .tipcontCodigo(row[8] != null ? Integer.parseInt(row[8].toString()) : null)
                .valorSinCeros(row[9] != null ? row[9].toString() : null)
                .build());
        }

        RiesgoBusquedaResponseDTO response = RiesgoBusquedaResponseDTO.builder()
                .riesgos(riesgos)
                .encontrado(encontrado)
                .build();

        // Enrich first result with cargo data
        if (encontrado) {
            RiesgoAseguradoDTO first = riesgos.get(0);
            try {
                response.setModelo(storedProcedureRepository.getRiesgosCargue(first.getContNumero(), first.getRiesgoCodigo(), 3));
                response.setColor(storedProcedureRepository.getRiesgosCargue(first.getContNumero(), first.getRiesgoCodigo(), 4));
                response.setTipoAsistencia(storedProcedureRepository.getRiesgosCargue(first.getContNumero(), first.getRiesgoCodigo(), 5));
                response.setOpcionCobertura(storedProcedureRepository.getRiesgosCargue(first.getContNumero(), first.getRiesgoCodigo(), 6));
            } catch (Exception e) {
                log.warn("Error loading risk cargo data: {}", e.getMessage());
            }
        }
        return response;
    }

    @Override
    @Transactional
    public RiesgoAseguradoDTO createRisk(String contNumero, String riesgoCodigo, Integer codigoCampo, String valor) {
        Long numeroOrden = storedProcedureRepository.getConsecutivoSiab("NUMERO_ORDEN");
        return RiesgoAseguradoDTO.builder()
                .contNumero(contNumero)
                .riesgoCodigo(riesgoCodigo)
                .codigoCampo(codigoCampo)
                .valor(valor)
                .numeroOrden(numeroOrden)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public PolizaValidacionDTO validatePolicy(String contrato) {
        List<RiesgoAseguradoEntity> riesgos = riesgoRepository.findByContNumeroContrato(contrato);
        if (riesgos.isEmpty()) {
            return PolizaValidacionDTO.builder()
                    .contNumero("ASISTBOL")
                    .polizaValida(false)
                    .esInexistente(true)
                    .mensaje("Póliza no encontrada, asignando contrato inexistente ASISTBOL")
                    .build();
        }
        RiesgoAseguradoEntity first = riesgos.get(0);
        return PolizaValidacionDTO.builder()
                .contNumero(first.getContNumeroContrato())
                .polizaValida(true)
                .esInexistente(false)
                .ramoCodigo(first.getRamoCodigo() != null ? Integer.parseInt(first.getRamoCodigo()) : null)
                .productoCodigo(first.getProductoCodigo() != null ? Integer.parseInt(first.getProductoCodigo()) : null)
                .estadoPoliza("A")
                .build();
    }

    @Override
    public String evaluateBeneficiaryId(String contNumero, Long pecoNumeroOrden) {
        return storedProcedureRepository.evaluaPideIdTitular(contNumero, pecoNumeroOrden);
    }

    @Override
    public String getRiesgosCargue(String contNumero, String riesgoCodigo, Integer posicion) {
        return storedProcedureRepository.getRiesgosCargue(contNumero, riesgoCodigo, posicion);
    }

    /**
     * KEY-NEXT-ITEM step 1: Calls P_PRODUCTOS_CONSULTA then reads T_PRODUCTOS_CONSULTA.
     * Returns the list of available products for the given risk value.
     */
    @Override
    @Transactional
    public java.util.List<java.util.Map<String, Object>> getProductosConsulta(
            String ramo, String producto, Long pais, String valor, Integer codigoCampo) {
        log.info("P_PRODUCTOS_CONSULTA: ramo={}, producto={}, pais={}, valor={}, campo={}", ramo, producto, pais, valor, codigoCampo);
        // Execute procedure AND read GTT in the same connection callback to guarantee same session
        return jdbcTemplate.execute((java.sql.Connection con) -> {
            boolean originalAutoCommit = con.getAutoCommit();
            con.setAutoCommit(false);
            try {
                try (java.sql.CallableStatement cs = con.prepareCall(
                        "{call NASIST.P_PRODUCTOS_CONSULTA1(?, ?, ?, ?, ?)}")) {
                    cs.setString(1, ramo != null ? ramo : "0");
                    cs.setString(2, producto != null ? producto : "0");
                    if (pais != null) cs.setLong(3, pais); else cs.setNull(3, java.sql.Types.NUMERIC);
                    cs.setString(4, valor);
                    if (codigoCampo != null) cs.setInt(5, codigoCampo); else cs.setNull(5, java.sql.Types.NUMERIC);
                    cs.execute();
                }
                java.util.List<java.util.Map<String, Object>> rows = new java.util.ArrayList<>();
                try (java.sql.Statement stmt = con.createStatement();
                     java.sql.ResultSet rs = stmt.executeQuery(
                         "SELECT EXISTE, INTERNO, COD_RAMO, RAMO, COD_PRODUCTO, PRODUCTO, INEXISTENTE FROM NASIST.T_PRODUCTOS_CONSULTA")) {
                    java.sql.ResultSetMetaData meta = rs.getMetaData();
                    int colCount = meta.getColumnCount();
                    while (rs.next()) {
                        java.util.Map<String, Object> row = new java.util.LinkedHashMap<>();
                        for (int i = 1; i <= colCount; i++) {
                            row.put(meta.getColumnName(i), rs.getObject(i));
                        }
                        rows.add(row);
                    }
                }
                log.info("P_PRODUCTOS_CONSULTA returned {} rows", rows.size());
                return rows;
            } finally {
                con.setAutoCommit(originalAutoCommit);
            }
        });
    }

    /**
     * KEY-NEXT-ITEM step 2: Calls P_RIESGOS_CEDULA then reads T_RIESGOS_CEDULA.
     * Returns the list of contracts/risks for the selected product.
     */
    @Override
    @Transactional
    public java.util.List<java.util.Map<String, Object>> getRiesgosCedula(
            String ramo, String producto, String ramo2, String producto2, String valor, Long pais, String existente) {
        log.info("P_RIESGOS_CEDULA: ramo={}, producto={}, ramo2={}, producto2={}, valor={}, pais={}, existente={}", ramo, producto, ramo2, producto2, valor, pais, existente);
        // Execute procedure AND read GTT in the same connection callback
        return jdbcTemplate.execute((java.sql.Connection con) -> {
            boolean originalAutoCommit = con.getAutoCommit();
            con.setAutoCommit(false);
            try {
                try (java.sql.CallableStatement cs = con.prepareCall(
                        "{call NASIST.P_RIESGOS_CEDULA(?, ?, ?, ?, ?, ?, ?)}")) {
                    if (ramo != null && !ramo.isEmpty()) cs.setString(1, ramo); else cs.setNull(1, java.sql.Types.VARCHAR);
                    if (producto != null && !producto.isEmpty()) cs.setString(2, producto); else cs.setNull(2, java.sql.Types.VARCHAR);
                    cs.setString(3, ramo2);
                    cs.setString(4, producto2);
                    cs.setString(5, existente != null ? existente : "N");
                    cs.setString(6, valor);
                    if (pais != null) cs.setLong(7, pais); else cs.setNull(7, java.sql.Types.NUMERIC);
                    cs.execute();
                }
                java.util.List<java.util.Map<String, Object>> rows = new java.util.ArrayList<>();
                try (java.sql.Statement stmt = con.createStatement();
                     java.sql.ResultSet rs = stmt.executeQuery(
                         "SELECT POLIZA, INICIO, FIN, NUM_ORDEN, TIP_CONTRATO, RIESGO, RAMO_CODIGO, PRODUCTO_CODIGO, " +
                         "NUMERO_DOCUMENTO, TIPO_DOCUMENTO, COD_CAMPO, RIESGO2, VALOR_RIESGO_ORI, PRODUCTO, ESTADO, " +
                         "VALOR_RIESGO, DIRECCION, TELEFONO, NOMBRES_APELLIDOS, PREFERENCIAL " +
                         "FROM NASIST.T_RIESGOS_CEDULA ORDER BY INICIO DESC, ESTADO DESC")) {
                    java.sql.ResultSetMetaData meta = rs.getMetaData();
                    int colCount = meta.getColumnCount();
                    while (rs.next()) {
                        java.util.Map<String, Object> row = new java.util.LinkedHashMap<>();
                        for (int i = 1; i <= colCount; i++) {
                            row.put(meta.getColumnName(i), rs.getObject(i));
                        }
                        rows.add(row);
                    }
                }
                log.info("P_RIESGOS_CEDULA returned {} rows", rows.size());
                return rows;
            } finally {
                con.setAutoCommit(originalAutoCommit);
            }
        });
    }
}
