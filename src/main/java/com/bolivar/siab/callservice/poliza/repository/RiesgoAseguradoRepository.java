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
     * SQL-01: Search by VALOR_SIN_CEROS (LTRIM logic), excluding CODIGO_CAMPO IN (48, 4, 81).
     */
    @Query(value = "SELECT * FROM NASIST.RIESGOS_ASEGURADOS WHERE LTRIM(VALOR, '0') = LTRIM(:valor, '0') " +
                   "AND CODIGO_CAMPO NOT IN (48, 4, 81)", nativeQuery = true)
    List<RiesgoAseguradoEntity> findByValorSinCeros(@Param("valor") String valor);

    List<RiesgoAseguradoEntity> findByContNumero(String contNumero);

    List<RiesgoAseguradoEntity> findByContNumeroAndRiesgoCodigo(String contNumero, String riesgoCodigo);
}
