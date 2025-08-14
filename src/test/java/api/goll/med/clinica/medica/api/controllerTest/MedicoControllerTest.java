package api.goll.med.clinica.medica.api.controllerTest;

import api.goll.med.clinica.medica.api.request.MedicoRequestDTOFixture;
import api.goll.med.clinica.medica.api.response.MedicoResponseDTOFixture;
import api.goll.med.clinica.medica.business.dtos.MedicoRequestDTO;
import api.goll.med.clinica.medica.business.dtos.MedicoResponseDTO;
import api.goll.med.clinica.medica.business.medicos.MedicosService;
import api.goll.med.clinica.medica.controller.GlobalExceptionHandler;
import api.goll.med.clinica.medica.controller.MedicosController;
import api.goll.med.clinica.medica.infrastructure.enums.Especialidade;
import api.goll.med.clinica.medica.infrastructure.exceptions.ConflictException;
import api.goll.med.clinica.medica.infrastructure.exceptions.ResourceNotFoundException;
import api.goll.med.clinica.medica.infrastructure.repository.MedicosRepository;
import api.goll.med.clinica.medica.infrastructure.security.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class MedicoControllerTest {

    @InjectMocks
    MedicosController medicosController;

    @Mock
    MedicosService medicosService;

    @Mock
    MedicosRepository medicosRepository;

    @Mock
    JwtUtil jwtUtil;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String url;

    private MedicoRequestDTO medicoRequestDTO;

    private MedicoResponseDTO medicoResponseDTO;

    private String json;



    @BeforeEach
        void setup() throws JsonProcessingException {
            mockMvc = MockMvcBuilders.standaloneSetup(medicosController)
                    .setControllerAdvice(new GlobalExceptionHandler())
                    .alwaysDo(print())
                    .build();

            url = "/medicos";


            medicoRequestDTO = MedicoRequestDTOFixture.build("Usuario",
                "usuario@gmail.com",
                "00000000-0/BR", "abcdefg", Especialidade.CARDIOLOGIA);

            medicoResponseDTO = MedicoResponseDTOFixture.build("Usuario",
                "usuario@gmail.com",
                "00000000-0/BR", "abcdefg", Especialidade.CARDIOLOGIA);

            json = objectMapper.writeValueAsString
                     (medicoRequestDTO);

    }

    @Test
    void deveSalvarMedicosComSucesso() throws Exception {

        when(medicosService.salvaMedico(medicoRequestDTO)).
                thenReturn(medicoResponseDTO);

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isOk());

        verify(medicosService).salvaMedico(medicoRequestDTO);
        verifyNoMoreInteractions(medicosService);
    }

    @Test
    void NaoDeveSalvarMedicosCasoCadastroExista() throws Exception {

        when(medicosService.salvaMedico(medicoRequestDTO))
                .thenThrow(new ConflictException("CRM já cadastrado"));

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isConflict())
                .andExpect(content().string("CRM já cadastrado"));

        verify(medicosService).salvaMedico(medicoRequestDTO);
        verifyNoMoreInteractions(medicosService);

    }

    @Test
    void deveAtualizarDadosMedicosComSucesso() throws Exception {

        String jwtToken = medicoRequestDTO.getToken().substring(7); // token puro para o serviço

        when(medicosService.atualizaDadosMedico(jwtToken, medicoRequestDTO))
                .thenReturn(medicoResponseDTO);

        mockMvc.perform(put("/medicos/update")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtToken)
                .content(json)
        ).andExpect(status().isOk());

        verify(medicosService).atualizaDadosMedico(jwtToken, medicoRequestDTO);
        verifyNoMoreInteractions(medicosService);
    }

    @Test
    void NaoDeveAtualizarCasoCRmNaoEncontrado() throws Exception{

       when(medicosService.atualizaDadosMedico
               (medicoRequestDTO.getToken(), medicoRequestDTO))
               .thenThrow(new ResourceNotFoundException("Médico não localizado."));

        mockMvc.perform(put("/medicos/update")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", medicoRequestDTO.getToken())
                .content(json)
        ).andExpect(status().isNotFound());

        verifyNoMoreInteractions(medicosService);

    }

    @Test
    void DeveBuscarMedicoPorCRM() throws Exception{

        when(medicosService.buscaMedicoPorCrm("00000000-0/BR"))
                .thenReturn(medicoResponseDTO);

        mockMvc.perform(get(url)
                .param("crm", "00000000-0/BR")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isOk());

        verify(medicosService).buscaMedicoPorCrm(medicoRequestDTO.getCrm());
        verifyNoMoreInteractions(medicosService);

    }

    @Test
    void NaoDeveBuscarMedicoCasoCRmNaoEncontradoOuInvalido() throws Exception{

        when(medicosService.
                buscaMedicoPorCrm(medicoRequestDTO.getCrm())).
                thenThrow
                        (new ResourceNotFoundException
                                ("CRM não encontrado ou inválido"));

        mockMvc.perform(get(url)
                .param("crm", medicoRequestDTO.getCrm())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isNotFound());

        verifyNoMoreInteractions(medicosService);

    }

    @Test
    void deveDeletarDadosMedicosComSucesso() throws Exception {

        String jwtToken = medicoRequestDTO.getToken().substring(7);
        doNothing().when(medicosService).
                deletaCadastroComCrm
                        ("00000000-0-BR", jwtToken);

        mockMvc.perform(delete(url + "/{crm}",
                "00000000-0-BR")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtToken)
                .content(json)
        ).andExpect(status().isAccepted());

        verify(medicosService).deletaCadastroComCrm("00000000-0-BR",
                jwtToken);
        verifyNoMoreInteractions(medicosService);
    }


}
