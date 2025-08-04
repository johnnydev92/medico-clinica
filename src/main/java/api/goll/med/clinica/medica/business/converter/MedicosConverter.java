package api.goll.med.clinica.medica.business.converter;

import api.goll.med.clinica.medica.business.dtos.MedicoRequestDTO;
import api.goll.med.clinica.medica.business.dtos.MedicoResponseDTO;
import api.goll.med.clinica.medica.infrastructure.entities.MedicosEntity;
import org.springframework.stereotype.Component;

@Component
public class MedicosConverter {

    public MedicosEntity paraMedicosEntity(MedicoResponseDTO medicoResponseDTO){

        return MedicosEntity.builder().
                nome(medicoResponseDTO.getNome())
                .email(medicoResponseDTO.getEmail())
                .crm(medicoResponseDTO.getCrm())
                .especialidade(medicoResponseDTO.getEspecialidade())
                .build();

    }

    public MedicoResponseDTO paraMedicosResponseDTO(MedicosEntity medicosDTO){

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

    public MedicosEntity paraMedicos(MedicoRequestDTO medicoRequestDTO){

        return MedicosEntity.builder().
                nome(medicoRequestDTO.getNome())
                .email(medicoRequestDTO.getEmail())
                .crm(medicoRequestDTO.getCrm())
                .especialidade(medicoRequestDTO.getEspecialidade())
                .build();

    }

    public MedicoRequestDTO paraMedicosRequestDTO(MedicosEntity medicosRequestDTO){

        return MedicoRequestDTO.builder()
                .nome(medicosRequestDTO.getNome())
                .email(medicosRequestDTO.getEmail())
                .crm(medicosRequestDTO.getCrm())
                .especialidade(medicosRequestDTO.getEspecialidade())
                .build();
    }

}
