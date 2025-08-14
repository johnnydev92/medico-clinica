package api.goll.med.clinica.medica.business.converter;

import api.goll.med.clinica.medica.business.dtos.MedicoRequestDTO;
import api.goll.med.clinica.medica.business.dtos.MedicoResponseDTO;
import api.goll.med.clinica.medica.infrastructure.entities.MedicosEntity;
import org.springframework.stereotype.Component;

@Component
public class MedicosConverter {

    public MedicosEntity paraMedicosEntity(MedicoRequestDTO medicoRequestDTO){

        return MedicosEntity.builder().
                nome(medicoRequestDTO.getNome())
                .email(medicoRequestDTO.getEmail())
                .crm(medicoRequestDTO.getCrm())
                .especialidade(medicoRequestDTO.getEspecialidade())
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

    public MedicosEntity updateDadosMedico(MedicoRequestDTO medicoRequestDTO, MedicosEntity entity) {
        return MedicosEntity.builder()
                .nome(medicoRequestDTO.getNome() != null ? medicoRequestDTO.getNome() : entity.getNome())
                .id(entity.getId())
                .email(medicoRequestDTO.getEmail() != null ? medicoRequestDTO.getEmail() : entity.getEmail())
                .crm(entity.getCrm())
                .especialidade(medicoRequestDTO.getEspecialidade() != null ? medicoRequestDTO.getEspecialidade() : entity.getEspecialidade())
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
