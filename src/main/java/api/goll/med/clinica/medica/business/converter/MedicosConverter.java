package api.goll.med.clinica.medica.business.converter;

import api.goll.med.clinica.medica.business.dtos.MedicoResponseDTO;
import api.goll.med.clinica.medica.infrastructure.entities.MedicosEntity;
import org.springframework.stereotype.Component;

@Component
public class MedicosConverter {

    public MedicosEntity ParaMedicos(MedicoResponseDTO medicoResponseDTO){

        return MedicosEntity.builder().
                nome(medicoResponseDTO.getNome())
                .email(medicoResponseDTO.getEmail())
                .crm(medicoResponseDTO.getCrm())
                .especialidade(medicoResponseDTO.getEspecialidade())
                .build();

    }

    public MedicoResponseDTO paraMedicosDTO(MedicosEntity medicosDTO){

        return MedicoResponseDTO.builder()
                .nome(medicosDTO.getNome())
                .email(medicosDTO.getEmail())
                .crm(medicosDTO.getCrm())
                .especialidade(medicosDTO.getEspecialidade())
                .build();
    }

    public MedicosEntity updateDadosMedico(MedicoResponseDTO medicoResponseDTO, MedicosEntity entity) {
        return MedicosEntity.builder()
                .nome(medicoResponseDTO.getNome() != null ? medicoResponseDTO.getNome() : entity.getNome())
                .id(entity.getId())
                .email(medicoResponseDTO.getEmail() != null ? medicoResponseDTO.getEmail() : entity.getEmail())
                .crm(entity.getCrm())
                .especialidade(medicoResponseDTO.getEspecialidade() != null ? medicoResponseDTO.getEspecialidade() : entity.getEspecialidade())
                .build();
    }

}
