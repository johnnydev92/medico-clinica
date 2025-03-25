package api.goll.med.clinica.medica.infrastructure.repository;


import api.goll.med.clinica.medica.infrastructure.entities.MedicosEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicosRepository extends JpaRepository<MedicosEntity, Long> {

    boolean existsByCrm(String crm);

    Optional<MedicosEntity> findByCrm(String crm);

    @Transactional
    void deleteByCrm(String crm);

}
