package api.goll.med.clinica.medica.infrastructure.repository;

import api.goll.med.clinica.medica.infrastructure.entities.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoEntity, Long> {
}
