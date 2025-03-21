package api.goll.med.clinica.medica.business.dtos;


import api.goll.med.clinica.medica.infrastructure.enums.Especialidade;
import lombok.*;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicoResponseDTO {

    private String nome;
    private String email;
    private String senha;
    private String crm;
    private Especialidade especialidade;

}
