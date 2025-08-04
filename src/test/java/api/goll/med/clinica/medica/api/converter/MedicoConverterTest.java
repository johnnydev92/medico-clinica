package api.goll.med.clinica.medica.api.converter;

import api.goll.med.clinica.medica.api.request.MedicoRequestDTOFixture;
import api.goll.med.clinica.medica.business.converter.MedicosConverter;
import api.goll.med.clinica.medica.business.dtos.MedicoRequestDTO;
import api.goll.med.clinica.medica.business.dtos.MedicoResponseDTO;
import api.goll.med.clinica.medica.infrastructure.entities.MedicosEntity;
import api.goll.med.clinica.medica.infrastructure.enums.Especialidade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class MedicoConverterTest {

    @InjectMocks
    MedicosConverter medicosConverter;

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
                "00000000-0/BR", "abc", Especialidade.CARDIOLOGIA);


    }

    @Test
    void deveConverterParaMedicoEntity(){

        MedicosEntity entity =
                medicosConverter.paraMedicos(medicoRequestDTO);

        assertThat(medicosEntity)
                .usingRecursiveComparison()
                .isEqualTo(entity);
    }
}
