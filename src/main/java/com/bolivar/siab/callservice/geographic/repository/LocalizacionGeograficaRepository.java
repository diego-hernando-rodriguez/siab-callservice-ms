package com.bolivar.siab.callservice.geographic.repository;

import com.bolivar.siab.callservice.geographic.models.LocalizacionGeograficaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LocalizacionGeograficaRepository extends JpaRepository<LocalizacionGeograficaEntity, Long> {
    Page<LocalizacionGeograficaEntity> findByNombreContainingIgnoreCaseAndTlgCodigo(String nombre, Integer tlgCodigo, Pageable pageable);
    List<LocalizacionGeograficaEntity> findByLocgeCodigoPadre(Long locgeCodigoPadre);
    Page<LocalizacionGeograficaEntity> findByPaisAndTlgCodigo(String pais, Integer tlgCodigo, Pageable pageable);
}
