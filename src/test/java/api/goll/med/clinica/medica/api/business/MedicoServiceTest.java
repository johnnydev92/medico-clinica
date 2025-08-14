package api.goll.med.clinica.medica.api.business;


import api.goll.med.clinica.medica.api.request.MedicoRequestDTOFixture;
import api.goll.med.clinica.medica.api.response.MedicoResponseDTOFixture;
import api.goll.med.clinica.medica.business.converter.MedicosConverter;
import api.goll.med.clinica.medica.business.dtos.MedicoRequestDTO;
import api.goll.med.clinica.medica.business.dtos.MedicoResponseDTO;
import api.goll.med.clinica.medica.business.medicos.MedicosService;
import api.goll.med.clinica.medica.infrastructure.entities.MedicosEntity;
import api.goll.med.clinica.medica.infrastructure.enums.Especialidade;
import api.goll.med.clinica.medica.infrastructure.exceptions.ConflictException;
import api.goll.med.clinica.medica.infrastructure.exceptions.ResourceNotFoundException;
import api.goll.med.clinica.medica.infrastructure.repository.MedicosRepository;
import api.goll.med.clinica.medica.infrastructure.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MedicoServiceTest {

    @InjectMocks
    private MedicosService medicosService;

    @Mock
    private MedicosRepository medicosRepository;

    @Mock
    private MedicosConverter medicosConverter;

    @Mock
    private JwtUtil jwtUtil;

    MedicosEntity medicosEntity;

    MedicoRequestDTO medicoRequestDTO;

    MedicoResponseDTO medicoResponseDTO;


    @BeforeEach
    public void setup(){

        medicosEntity = MedicosEntity.builder()
                .nome("Usuario")
                .email("usuario@gmail.com")
                .crm("00000000-0/BR")
                .especialidade(Especialidade.CARDIOLOGIA)
                .build();

        medicoRequestDTO = MedicoRequestDTOFixture.build("Usuario",
                "usuario@gmail.com",
                "00000000-0/BR", "abcdefg", Especialidade.CARDIOLOGIA);

        medicoResponseDTO = MedicoResponseDTOFixture.build("Usuario",
                "usuario@gmail.com",
                "00000000-0/BR", "abcdefg", Especialidade.CARDIOLOGIA);

    }

    @Test
    void deveSalvarMedicoComSucesso(){
        when(medicosRepository.
                existsByCrm("00000000-0/BR")).thenReturn(false);
        when(medicosConverter.
                paraMedicosEntity(medicoRequestDTO)).
                thenReturn(medicosEntity);
        when(medicosRepository.save
                (medicosEntity)).thenReturn(medicosEntity);
        when(medicosConverter.paraMedicosResponseDTO(medicosEntity))
                .thenReturn(medicoResponseDTO);

        MedicoResponseDTO resultado =
                medicosService.salvaMedico(medicoRequestDTO);

        assertNotNull(resultado);
        assertEquals("00000000-0/BR", resultado.getCrm());
        assertEquals("Usuario", resultado.getNome());

        verify(medicosRepository).existsByCrm("00000000-0/BR");
        verify(medicosRepository).save(medicosEntity);

        verify(medicosConverter).paraMedicosEntity(medicoRequestDTO);
        verify(medicosConverter).paraMedicosResponseDTO(medicosEntity);
        verifyNoMoreInteractions(medicosRepository);

    }

    @Test
    void deveLancarConflictExceptionQuandoCrmJaExiste() {
        // Arrange
        when(medicosRepository.existsByCrm("00000000-0/BR")).thenReturn(true);

        // Act & Assert
        ConflictException exception =
                assertThrows(ConflictException.class, () -> {
            medicosService.salvaMedico(medicoRequestDTO);
        });

        assertEquals("CRM já cadastrado", exception.getMessage());

        verify(medicosRepository).existsByCrm("00000000-0/BR");
        verifyNoMoreInteractions(medicosRepository, medicosConverter);
    }

    @Test
    void deveDeletarCadastroComCrmComSucesso(){

        doNothing().when(medicosRepository).
                deleteByCrm(medicoResponseDTO.getCrm());

        medicosService.deletaCadastroComCrm(medicoRequestDTO.getCrm(), medicoRequestDTO.getToken());

        verify(medicosRepository).deleteByCrm(medicoResponseDTO.getCrm());

    }

    @Test
    void deveVerificarCrmExistente(){

        String crmExistente = "00000000-0/BR";
        when(medicosRepository.existsByCrm(crmExistente))
                .thenReturn(true);
        ConflictException ex = assertThrows(ConflictException.class,
                ()->{medicosService.crmExiste(crmExistente);});
        assertEquals("Crm já existe" + crmExistente, ex.getMessage());

        String crmInexistente = "12345678-9/BR";
        when(medicosRepository.existsByCrm(crmInexistente)).
                thenReturn(false);
        assertDoesNotThrow(
                ()-> medicosService.crmExiste(crmInexistente));

        verify(medicosRepository).existsByCrm(crmExistente);
        verify(medicosRepository).existsByCrm(crmInexistente);
        verifyNoMoreInteractions(medicosRepository);
    }

    @Test
    void deveLancarResourceNotFoundExceptionQuandoCrmNaoExiste() {
        String crmInexistente = "99999999-9/BR";

        when(medicosRepository.findByCrm(crmInexistente))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            medicosService.buscaMedicoPorCrm(crmInexistente);
        });

        assertEquals("Crm não encontrado " + crmInexistente, exception.getMessage());

        verify(medicosRepository).findByCrm(crmInexistente);
        verifyNoMoreInteractions(medicosRepository, medicosConverter);
    }

    @Test
    void DeveBuscarMedicoPorCrm(){

        when(medicosRepository
                .findByCrm(medicoResponseDTO.getCrm()))
                .thenReturn(Optional.of(medicosEntity));
        when(medicosConverter.
                paraMedicosResponseDTO(medicosEntity))
                .thenReturn(medicoResponseDTO);

        MedicoResponseDTO resultado =
                medicosService.buscaMedicoPorCrm
                        (medicoResponseDTO.getCrm());

        assertEquals(medicoResponseDTO, resultado);
        verify(medicosRepository).findByCrm(medicoResponseDTO.getCrm());
        verify(medicosConverter).paraMedicosResponseDTO(medicosEntity);
        verifyNoMoreInteractions(medicosRepository);
    }

    @Test
    void DeveAtualizarDadosMedicoComSucesso(){

        when(jwtUtil.extrairCrmToken
                (medicoRequestDTO.getToken()
                        .substring(7))).
                thenReturn(medicoRequestDTO.getCrm());

        when(medicosRepository.findByCrm(medicoRequestDTO.getCrm())).
                thenReturn(Optional.of(medicosEntity));

        when(medicosConverter.updateDadosMedico
                (medicoRequestDTO, medicosEntity)).
                thenReturn(medicosEntity);

        when(medicosRepository.save(medicosEntity))
                .thenReturn(medicosEntity);
        when(medicosConverter.paraMedicosResponseDTO
                (medicosEntity)).thenReturn(medicoResponseDTO);

        MedicoResponseDTO resultado =
                medicosService.atualizaDadosMedico
                        (medicoResponseDTO.getToken(), medicoRequestDTO);

        assertEquals(medicoResponseDTO, resultado);
        verify(jwtUtil).extrairCrmToken
                (medicoResponseDTO.getToken()
                        .substring(7));
        verify(medicosRepository)
                .findByCrm(medicoResponseDTO.getCrm());
        verify(medicosConverter).
                updateDadosMedico(medicoRequestDTO, medicosEntity);
        verify(medicosRepository).save(medicosEntity);
        verify(medicosConverter).paraMedicosResponseDTO(medicosEntity);
        verifyNoMoreInteractions(medicosRepository, jwtUtil, medicosConverter);

    }

    @Test
    void deveLancarResourceNotFoundExceptionQuandoCrmExtraidoDoTokenNaoExiste() {
        // Arrange
        String token = "Bearer fake-token";
        String crm = "00000000-0/BR";

        when(jwtUtil.extrairCrmToken("fake-token")).thenReturn(crm);
        when(medicosRepository.findByCrm(crm)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            medicosService.atualizaDadosMedico(token, medicoRequestDTO);
        });

        assertEquals("Médico não localizado.", exception.getMessage());

        verify(jwtUtil).extrairCrmToken("fake-token");
        verify(medicosRepository).findByCrm(crm);
        verifyNoMoreInteractions(jwtUtil, medicosRepository, medicosConverter);
    }


}
