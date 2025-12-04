package com.talles.projetomv.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "CONTA") 
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CONTA")
    private Long id;

    @Column(name = "BANCO")
    private String banco;

    @Column(name = "AGENCIA")
    private String agencia;

    @Column(name = "NUMERO_CONTA")
    private String numeroConta;

    @Column(name = "ATIVA")
    private String ativa = "S";

    @ManyToOne
    @JoinColumn(name = "ID_CLIENTE")
    private Cliente cliente;
}
