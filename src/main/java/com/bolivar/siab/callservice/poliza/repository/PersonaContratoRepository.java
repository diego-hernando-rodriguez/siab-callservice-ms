package com.bolivar.siab.callservice.poliza.repository;

import com.bolivar.siab.callservice.poliza.models.PersonaContratoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PersonaContratoRepository extends JpaRepository<PersonaContratoEntity, Long> {
    List<PersonaContratoEntity> findByContNumero(String contNumero);
}
