package api.goll.med.clinica.medica.infrastructure.security;


import api.goll.med.clinica.medica.infrastructure.entities.MedicosEntity;
import api.goll.med.clinica.medica.infrastructure.repository.MedicosRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    // Repositório para acessar dados de usuário no banco de dados

    private final MedicosRepository medicosRepository;

    public UserDetailsServiceImpl(MedicosRepository medicosRepository) {
        this.medicosRepository = medicosRepository;
    }

    // Implementação do método para carregar detalhes do usuário pelo crm
    @Override
    public UserDetails loadUserByUsername(String crm) throws UsernameNotFoundException {
        // Busca o usuário no banco de dados pelo e-mail
        MedicosEntity medicos = medicosRepository.findByCrm(crm)
                .orElseThrow(() -> new UsernameNotFoundException("Médico não encontrado: " + crm));

        // Cria e retorna um objeto UserDetails com base no usuário encontrado
        return org.springframework.security.core.userdetails.User
                .withUsername(medicos.getEmail()) // Define o nome de usuário como o e-mail
                .build(); // Constrói o objeto UserDetails
    }
}
