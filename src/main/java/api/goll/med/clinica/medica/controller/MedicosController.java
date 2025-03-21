package api.goll.med.clinica.medica.controller;

import api.goll.med.clinica.medica.business.dtos.MedicoResponseDTO;
import api.goll.med.clinica.medica.business.medicos.MedicosService;
import api.goll.med.clinica.medica.infrastructure.security.JwtUtil;
import api.goll.med.clinica.medica.infrastructure.security.SecurityConfig;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<MedicoResponseDTO> salvaMedicos(@RequestBody MedicoResponseDTO medicoResponseDTO) {
        return ResponseEntity.ok(medicosService.salvaMedico(medicoResponseDTO));

    }


    @GetMapping
    public ResponseEntity<MedicoResponseDTO> buscaMedicoPorCrm(@RequestParam("crm") String crm) {

        MedicoResponseDTO medicoResponseDTO = medicosService.buscaMedicoPorCrm(crm);
        String token = "Bearer " + jwtUtil.generateToken(crm);
        return ResponseEntity.ok(medicoResponseDTO);
    }

    @DeleteMapping("/{crm}")
    public ResponseEntity<Void> deletaUsuarioPorCrm(@PathVariable String crm,
                                                    @RequestHeader ("Authorization") String token) {
        medicosService.deletaCadastroComToken(token);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<MedicoResponseDTO> atualizaDadosMedico(@RequestBody MedicoResponseDTO dto,
                                                         @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(medicosService.atualizaDadosMedico(token, dto));

    }


}
