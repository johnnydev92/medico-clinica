package api.goll.med.clinica.medica.business.dtos;


import api.goll.med.clinica.medica.infrastructure.enums.Especialidade;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicoRequestDTO {


    private String nome;
    private String email;
    private String crm;
    private String token;
    private Especialidade especialidade;

}
