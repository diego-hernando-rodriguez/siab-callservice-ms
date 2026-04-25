package com.bolivar.siab.callservice.casomanagement.repository;

import com.bolivar.siab.callservice.casomanagement.models.InformacionUsuariosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InformacionUsuariosRepository extends JpaRepository<InformacionUsuariosEntity, InformacionUsuariosEntity.InfoUsuarioId> {
    List<InformacionUsuariosEntity> findByUsuNumeroDocumentoAndUsuTipoDocumento(String usuNumeroDocumento, String usuTipoDocumento);
}
