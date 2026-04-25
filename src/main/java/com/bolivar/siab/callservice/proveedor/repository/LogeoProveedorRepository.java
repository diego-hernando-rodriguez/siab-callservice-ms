package com.bolivar.siab.callservice.proveedor.repository;

import com.bolivar.siab.callservice.proveedor.models.LogeoProveedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LogeoProveedorRepository extends JpaRepository<LogeoProveedorEntity, Long> {
    List<LogeoProveedorEntity> findByServCodigoAndClservCodigoAndLocgeCodigoAndEstado(
            Integer servCodigo, Integer clservCodigo, Long locgeCodigo, String estado);
    List<LogeoProveedorEntity> findByEstado(String estado);
}
