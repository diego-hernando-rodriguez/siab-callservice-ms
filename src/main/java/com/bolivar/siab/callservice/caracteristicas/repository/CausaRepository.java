package com.bolivar.siab.callservice.caracteristicas.repository;

import com.bolivar.siab.callservice.caracteristicas.models.CausaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CausaRepository extends JpaRepository<CausaEntity, CausaEntity.CausaId> {
    List<CausaEntity> findByRamoCodigoAndProductoCodigoAndEstado(String ramo, String producto, String estado);

    /**
     * LLAMADA_CAUSA_CODIG_LOV3: Causes with product and ramo descriptions.
     * Simplified version without contract filter (for initial load).
     */
    @Query(nativeQuery = true, value =
        "SELECT c.codigo, substr(c.descripcion,1,25) as causa, " +
        "p.descripcion as producto, c.ramo_codigo, c.producto_codigo, r.descripcion as ramo " +
        "FROM causas c, productos p, ramos r " +
        "WHERE r.codigo = p.ramo_codigo " +
        "AND c.ramo_codigo = p.ramo_codigo " +
        "AND c.producto_codigo = p.codigo " +
        "AND c.estado = 'A' " +
        "AND p.codigo = NVL(:producto, p.codigo) " +
        "AND p.ramo_codigo = NVL(:ramo, p.ramo_codigo) " +
        "ORDER BY c.codigo")
    List<Object[]> findCausasConProductoYRamo(@Param("ramo") String ramo, @Param("producto") String producto);
}
