package api.goll.med.clinica.medica.business.dtos;


import api.goll.med.clinica.medica.infrastructure.enums.Especialidade;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicoDTO {

    private String nome;
    private String email;
    private String senha;
    private String crm;
    private Especialidade especialidade;
    private List<EnderecoDTO> endereco;
}
