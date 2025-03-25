package api.goll.med.clinica.medica.controller;

import api.goll.med.clinica.medica.business.dtos.MedicoResponseDTO;
import api.goll.med.clinica.medica.business.medicos.MedicosService;
import api.goll.med.clinica.medica.infrastructure.security.JwtUtil;
import api.goll.med.clinica.medica.infrastructure.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicos")
@RequiredArgsConstructor
@Tag(name = "medicos", description = "Cadastro, login, busca e delete de medicos")
@SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
public class MedicosController {

    private final MedicosService medicosService;
    private final JwtUtil jwtUtil;

    @PostMapping
    @Operation(summary = "Salva medicos", description = "Cria um novo medico")
    @ApiResponse(responseCode = "201", description = "Medico salvo com sucesso")
    @ApiResponse(responseCode = "409", description = "Medico já cadastrado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<MedicoResponseDTO> salvaMedicos(@RequestBody MedicoResponseDTO medicoResponseDTO) {
        return ResponseEntity.ok(medicosService.salvaMedico(medicoResponseDTO));

    }


    @GetMapping
    @Operation(summary = "Busca medico cadastrado", description = "Busca dados de medico por crm")
    @ApiResponse(responseCode = "200", description = "Cadastro de medico encontrado com sucesso")
    @ApiResponse(responseCode = "404", description = "Cadastro não encontrado. Tente novamente.")
    @ApiResponse(responseCode = "406", description = "Erro de busca. Tente novamente.")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<MedicoResponseDTO> buscaMedicoPorCrm(@RequestParam("crm") String crm) {

        MedicoResponseDTO medicoResponseDTO = medicosService.buscaMedicoPorCrm(crm);
        String token = "Bearer " + jwtUtil.generateToken(crm);
        return ResponseEntity.ok(medicoResponseDTO);
    }

    @DeleteMapping("/{crm}")
    @Operation(summary = "Deleta medico cadastrado", description = "Deleta medico cadastrado com token")
    @ApiResponse(responseCode = "200", description = "Medico deletado com sucesso")
    @ApiResponse(responseCode = "401", description = "Token inválido. Tente mais tarde.")
    @ApiResponse(responseCode = "403", description = "Medico não encontrado. Tente novamente")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<Void> deletaUsuarioPorCrm(@PathVariable String crm,
                                                    @RequestHeader ("Authorization") String token) {
        medicosService.deletaCadastroComToken(token);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/medicos")
    @Operation(summary = "Atualiza dados de medico", description = "Atualiza dados de medico com token")
    @ApiResponse(responseCode = "200", description = "Dados atualizados com sucesso")
    @ApiResponse(responseCode = "401", description = "Token inválido. Tente mais tarde.")
    @ApiResponse(responseCode = "403", description = "Cadastro não encontrado. Tente novamente.")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<MedicoResponseDTO> atualizaDadosMedico(@RequestBody MedicoResponseDTO dto,
                                                         @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(medicosService.atualizaDadosMedico(token, dto));

    }


}
