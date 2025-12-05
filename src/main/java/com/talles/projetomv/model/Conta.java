package com.talles.projetomv.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
    @JsonIgnoreProperties("contas")
    private Cliente cliente;
    
    @OneToMany(mappedBy = "conta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("conta")
    private List<Movimentacao> movimentacoes;

}
