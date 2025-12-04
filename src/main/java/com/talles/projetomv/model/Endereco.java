package com.talles.projetomv.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ENDERECO")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ENDERECO")
    private Long id;

    @Column(name = "RUA")
    private String rua;

    @Column(name = "NUMERO")
    private String numero;

    @Column(name = "CIDADE")
    private String cidade;

    @Column(name = "ESTADO")
    private String estado;

    @Column(name = "CEP")
    private String cep;

    @ManyToOne
    @JoinColumn(name = "ID_CLIENTE")
    private Cliente cliente;
}
