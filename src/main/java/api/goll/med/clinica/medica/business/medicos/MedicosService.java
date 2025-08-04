package api.goll.med.clinica.medica.business.medicos;


import api.goll.med.clinica.medica.business.converter.MedicosConverter;
import api.goll.med.clinica.medica.business.dtos.MedicoResponseDTO;
import api.goll.med.clinica.medica.infrastructure.entities.MedicosEntity;
import api.goll.med.clinica.medica.infrastructure.exceptions.ConflictException;
import api.goll.med.clinica.medica.infrastructure.exceptions.ResourceNotFoundException;
import api.goll.med.clinica.medica.infrastructure.repository.MedicosRepository;
import api.goll.med.clinica.medica.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedicosService {

        private final MedicosRepository medicosRepository;
        private final MedicosConverter medicosConverter;
        private final JwtUtil jwtUtil;

    public MedicoResponseDTO salvaMedico(MedicoResponseDTO medicoResponseDTO){
        if (medicosRepository.existsByCrm(medicoResponseDTO.getCrm())) {
            throw new ConflictException("CRM já cadastrado");
        }

        MedicosEntity medicos = medicosConverter.paraMedicosEntity(medicoResponseDTO);
        MedicosEntity medicosSalvo = medicosRepository.save(medicos);
        return medicosConverter.paraMedicosResponseDTO(medicosSalvo);
    }

        public void deletaCadastroComCrm(String crm){

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
                            .orElseThrow(() -> new ResourceNotFoundException("CRM não encontrado " + crm))
                    );
                }catch (ResourceNotFoundException e){
                    throw new ResourceNotFoundException("Crm não encontrado " + crm);
                }
        }

        public MedicoResponseDTO atualizaDadosMedico(String token, MedicoResponseDTO dto){

            String crm = jwtUtil.extrairCrmToken(token.substring(7));

            MedicosEntity medicos = medicosRepository.findByCrm(crm).orElseThrow(()
            -> new ResourceNotFoundException("Médico não localizado."));

            MedicosEntity medicosEntity = medicosConverter.updateDadosMedico(dto, medicos);

            return medicosConverter.paraMedicosResponseDTO(medicosRepository.save(medicosEntity));

        }


}
