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
                .senha(medicoResponseDTO.getSenha())
                .crm(medicoResponseDTO.getCrm())
                .especialidade(medicoResponseDTO.getEspecialidade())
                .build();

    }

    public MedicoResponseDTO paraMedicosDTO(MedicosEntity medicosDTO){

        return MedicoResponseDTO.builder()
                .nome(medicosDTO.getNome())
                .senha(medicosDTO.getSenha())
                .crm(medicosDTO.getCrm())
                .especialidade(medicosDTO.getEspecialidade())
                .build();
    }

    public MedicosEntity updateDadosMedico(MedicoResponseDTO usuarioDTO, MedicosEntity entity) {
        return MedicosEntity.builder()
                .nome(usuarioDTO.getNome() != null ? usuarioDTO.getNome() : entity.getNome())
                .id(entity.getId())
                .senha(usuarioDTO.getSenha() != null ? usuarioDTO.getSenha() : entity.getSenha())
                .email(usuarioDTO.getEmail() != null ? usuarioDTO.getEmail() : entity.getEmail())
                .build();
    }

}
