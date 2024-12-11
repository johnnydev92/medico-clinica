package api.goll.med.clinica.medica.business.medicos;


import api.goll.med.clinica.medica.business.converter.MedicosConverter;
import api.goll.med.clinica.medica.business.dtos.MedicoDTO;
import api.goll.med.clinica.medica.infrastructure.entities.MedicosEntity;
import api.goll.med.clinica.medica.infrastructure.exceptions.ConflictException;
import api.goll.med.clinica.medica.infrastructure.exceptions.ResourceNotFoundException;
import api.goll.med.clinica.medica.infrastructure.repository.EnderecoRepository;
import api.goll.med.clinica.medica.infrastructure.repository.MedicosRepository;
import api.goll.med.clinica.medica.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedicosService {

        private final MedicosRepository medicosRepository;
        private final EnderecoRepository enderecoRepository;
        private final MedicosConverter medicosConverter;
        private final JwtUtil jwtUtil;
        private final PasswordEncoder passwordEncoder;

        public MedicoDTO salvaMedico(MedicoDTO medicoDTO){
            crmExiste(medicoDTO.getCrm());
            medicoDTO.setSenha(passwordEncoder.encode(medicoDTO.getSenha()));
            MedicosEntity medicos = medicosConverter.ParaMedicos(medicoDTO);

        }

        public void crmExiste(String crm){
            try {

                boolean existe = verificaCrmExistente(crm);
                if (existe) {
                    throw new ConflictException("Crm já existe" + crm);

                }

            }catch (ConflictException e){
                throw new ConflictException("Crm já existe" + e.getCause());

            }

        }

        public boolean verificaCrmExistente(String crm){
            return medicosRepository.existsByCrm(crm);
        }

        public MedicoDTO buscaMedicoPorCrm(String crm){
            try {
                return medicosConverter.paraMedicosDTO(medicosRepository.findByCRM(crm)
                        .orElseThrow(() -> new ResourceNotFoundException("CRM não encontrado " + crm))
                );
            }catch (ResourceNotFoundException e){
                throw new ResourceNotFoundException("Crm não encontrado " + crm);
            }
        }



}
