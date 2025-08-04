package api.goll.med.clinica.medica.api.response;

import api.goll.med.clinica.medica.business.dtos.MedicoResponseDTO;
import api.goll.med.clinica.medica.infrastructure.enums.Especialidade;

public class MedicoResponseDTOFixture {

    public static MedicoResponseDTO build(

    String nome,
    String email,
    String crm,
    String token,
    Especialidade especialidade){

        return new MedicoResponseDTO(nome, email, crm, token, especialidade);

    }
}
