package com.bolivar.siab.callservice.configuracion.repository;

import com.bolivar.siab.callservice.configuracion.models.CgRefCodesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CgRefCodesRepository extends JpaRepository<CgRefCodesEntity, CgRefCodesEntity.CgRefCodesId> {
    List<CgRefCodesEntity> findByRvDomain(String rvDomain);
    List<CgRefCodesEntity> findByRvDomainAndRvLowValue(String rvDomain, String rvLowValue);
}
