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
                .crm(medicosDTO.getCrm())
                .especialidade(medicosDTO.getEspecialidade())
                .build();
    }

    public MedicosEntity updateDadosMedico(MedicoResponseDTO usuarioDTO, MedicosEntity entity) {
        return MedicosEntity.builder()
                .nome(usuarioDTO.getNome() != null ? usuarioDTO.getNome() : entity.getNome())
                .id(entity.getId())
                .email(usuarioDTO.getEmail() != null ? usuarioDTO.getEmail() : entity.getEmail())
                .build();
    }

}
