package com.bolivar.siab.callservice.configuracion.repository;

import com.bolivar.siab.callservice.configuracion.models.CgCodeControlsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CgCodeControlsRepository extends JpaRepository<CgCodeControlsEntity, String> {
}
