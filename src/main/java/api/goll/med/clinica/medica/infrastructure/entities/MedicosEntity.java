package api.goll.med.clinica.medica.infrastructure.entities;



import api.goll.med.clinica.medica.infrastructure.enums.Especialidade;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "medicos")
@Builder
public class MedicosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty(required = true)
    @Column(name = "nome", updatable = true)
    private String nome;
    @JsonProperty(required = true)
    @Column(name = "email", updatable = true)
    private String email;
    @JsonProperty(required = true)
    @Column(name = "senha", updatable = true)
    private String senha;
    @JsonProperty(required = true)
    @Column(name = "crm", updatable = true)
    private String crm;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private List<EnderecoEntity> endereco;

    @Enumerated(EnumType.STRING)
    @Column(name = "especialidade", updatable = true)
    @JsonProperty(required = true)
    private Especialidade especialidade;
}
