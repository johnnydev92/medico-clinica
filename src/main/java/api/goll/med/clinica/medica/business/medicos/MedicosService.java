package api.goll.med.clinica.medica.business.medicos;


import api.goll.med.clinica.medica.business.converter.MedicosConverter;
import api.goll.med.clinica.medica.business.dtos.MedicoRequestDTO;
import api.goll.med.clinica.medica.business.dtos.MedicoResponseDTO;
import api.goll.med.clinica.medica.infrastructure.entities.MedicosEntity;
import api.goll.med.clinica.medica.infrastructure.exceptions.ConflictException;
import api.goll.med.clinica.medica.infrastructure.exceptions.ResourceNotFoundException;
import api.goll.med.clinica.medica.infrastructure.repository.MedicosRepository;
import api.goll.med.clinica.medica.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedicosService {

        private final MedicosRepository medicosRepository;
        private final MedicosConverter medicosConverter;
        private final JwtUtil jwtUtil;

    public MedicoResponseDTO salvaMedico(MedicoRequestDTO medicoRequestDTO){
        if (medicosRepository.existsByCrm(medicoRequestDTO.getCrm())) {
            throw new ConflictException("CRM já cadastrado");
        }

        MedicosEntity medicos = medicosConverter.paraMedicosEntity(medicoRequestDTO);
        MedicosEntity medicosSalvo = medicosRepository.save(medicos);
        return medicosConverter.paraMedicosResponseDTO(medicosSalvo);
    }

    public void deletaCadastroComCrm(String crm, String token) {

        String tokenLimpo = token.substring(7);

        // Extrai o CRM do token
        String crmExtraido = jwtUtil.extrairCrmToken(tokenLimpo);

        if (!jwtUtil.validateToken(tokenLimpo, crm)) {
            throw new ConflictException("Token inválido. Tente mais tarde.");
        }

        boolean medicoExiste = medicosRepository.existsByCrm(crm);
        if (!medicoExiste) {
            throw new ResourceNotFoundException("Médico não encontrado. Tente novamente.");
        }

        medicosRepository.deleteByCrm(crm);
    }

        public void crmExiste(String crm){
            try {

                boolean existe = verificaCrmExistente(crm);
                        if (existe) {
                        throw new ConflictException("Crm já existe" + crm);

                    }

                    }
                        catch (ConflictException e){
                        throw new ConflictException(e.getMessage());

                    }

            }

        public boolean verificaCrmExistente(String crm){
            return medicosRepository.existsByCrm(crm);
        }

            public MedicoResponseDTO buscaMedicoPorCrm(String crm){
                try {
                    return medicosConverter.paraMedicosResponseDTO(medicosRepository.findByCrm(crm)
                            .orElseThrow(() -> new ResourceNotFoundException("CRM não encontrado ou inválido " + crm))
                    );
                }catch (ResourceNotFoundException e){
                    throw new ResourceNotFoundException("Crm não encontrado ou inválido " + crm);
                }
        }

        public MedicoResponseDTO atualizaDadosMedico(String token, MedicoRequestDTO dto){

            // Remove o prefixo "Bearer " do token
            String tokenLimpo = token.substring(7);

            // Extrai o CRM do token (payload)
            String crm = jwtUtil.extrairCrmToken(tokenLimpo);

            // Verifica se o token está expirado
            if (jwtUtil.isTokenExpired(tokenLimpo)) {
                throw new ConflictException("Token expirado.");
            }

            // Busca o médico pelo CRM extraído do token
            MedicosEntity medico = medicosRepository.findByCrm(crm)
                    .orElseThrow(() -> new ResourceNotFoundException("Médico não localizado."));

            // Atualiza os dados
            MedicosEntity medicoAtualizado = medicosConverter.updateDadosMedico(dto, medico);

            // Salva e retorna DTO atualizado
            return medicosConverter.paraMedicosResponseDTO(medicosRepository.save(medicoAtualizado));
        }


}
