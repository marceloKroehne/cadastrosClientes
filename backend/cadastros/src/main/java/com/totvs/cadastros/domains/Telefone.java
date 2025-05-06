package com.totvs.cadastros.domains;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="telefones")
public class Telefone {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(unique = true)
    private String telefone;

    @ManyToOne
    @JoinColumn(name="usuario_id")
    private Usuario usuario;
}
