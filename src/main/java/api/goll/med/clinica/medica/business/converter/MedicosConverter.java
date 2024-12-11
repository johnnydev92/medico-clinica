package api.goll.med.clinica.medica.business.converter;

import api.goll.med.clinica.medica.business.dtos.EnderecoDTO;
import api.goll.med.clinica.medica.business.dtos.MedicoDTO;
import api.goll.med.clinica.medica.infrastructure.entities.EnderecoEntity;
import api.goll.med.clinica.medica.infrastructure.entities.MedicosEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MedicosConverter {

    public MedicosEntity ParaMedicos(MedicoDTO medicoDTO){

        return MedicosEntity.builder().
                nome(medicoDTO.getNome())
                .email(medicoDTO.getEmail())
                .senha(medicoDTO.getSenha())
                .crm(medicoDTO.getCrm())
                .especialidade(medicoDTO.getEspecialidade())
                .endereco(paraListaEndereco(medicoDTO.getEndereco()))
                .build();

    }

    public MedicoDTO paraMedicosDTO(MedicosEntity medicosDTO){

        return MedicoDTO.builder()
                .nome(medicosDTO.getNome())
                .senha(medicosDTO.getSenha())
                .crm(medicosDTO.getCrm())
                .especialidade(medicosDTO.getEspecialidade())
                .endereco(paraListaEnderecoDTO(medicosDTO.getEndereco()))
                .build();
    }

    public List<EnderecoEntity> paraListaEndereco(List<EnderecoDTO> enderecoDTOS){

        List<EnderecoEntity> enderecoEntityList = new ArrayList<>();
        for (EnderecoDTO enderecoDTO: enderecoDTOS){
            enderecoEntityList.add(paraEndereco(enderecoDTO));
        }
        return enderecoEntityList;

    }

    public List<EnderecoDTO> paraListaEnderecoDTO(List<EnderecoEntity> enderecoDTOList){
        return enderecoDTOList.stream().map(this::paraEnderecoDTO).toList();
    }

    public EnderecoEntity paraEndereco(EnderecoDTO enderecoDTO){
        return EnderecoEntity.builder()
                .logradouro(enderecoDTO.getLogradouro())
                .numero(enderecoDTO.getNumero())
                .cidade(enderecoDTO.getCidade())
                .complemento(enderecoDTO.getComplemento())
                .cep(enderecoDTO.getCep())
                .uf(enderecoDTO.getUf())
                .build();
    }

    public EnderecoDTO paraEnderecoDTO(EnderecoEntity endereco){
        return EnderecoDTO.builder()
                .id(endereco.getId())
                .logradouro(endereco.getLogradouro())
                .numero(endereco.getNumero())
                .cidade(endereco.getCidade())
                .complemento(endereco.getComplemento())
                .cep(endereco.getCep())
                .uf(endereco.getUf())
                .build();
    }

    public MedicosEntity updateDadosMedico(MedicoDTO usuarioDTO, MedicosEntity entity) {
        return MedicosEntity.builder()
                .nome(usuarioDTO.getNome() != null ? usuarioDTO.getNome() : entity.getNome())
                .id(entity.getId())
                .senha(usuarioDTO.getSenha() != null ? usuarioDTO.getSenha() : entity.getSenha())
                .email(usuarioDTO.getEmail() != null ? usuarioDTO.getEmail() : entity.getEmail())
                .endereco(entity.getEndereco())
                .build();
    }

    public EnderecoEntity updateEndereco(EnderecoDTO dto, EnderecoEntity entity){
        return EnderecoEntity.builder()
                .id(entity.getId())
                .logradouro(dto.getLogradouro() != null ? dto.getLogradouro() : entity.getLogradouro())
                .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
                .cidade(dto.getCidade() != null ? dto.getCidade() : entity.getCidade())
                .cep(dto.getCep() != null ? dto.getCep() : entity.getCep())
                .complemento(dto.getComplemento() != null ? dto.getComplemento() : entity.getComplemento())
                .uf(dto.getUf() != null ? dto.getUf() : entity.getUf())
                .build();
    }
}
