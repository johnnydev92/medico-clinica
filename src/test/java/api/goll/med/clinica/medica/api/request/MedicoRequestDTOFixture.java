package api.goll.med.clinica.medica.api.request;

import api.goll.med.clinica.medica.business.dtos.MedicoRequestDTO;
import api.goll.med.clinica.medica.infrastructure.enums.Especialidade;

public class MedicoRequestDTOFixture {

    public static MedicoRequestDTO build(

    String nome,
    String email,
    String crm,
    String token,
    Especialidade especialidade){

        return new MedicoRequestDTO(nome, email, crm, token, especialidade);
    }


}
