package com.talles.projetomv.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "MOVIMENTACAO")
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MOV")
    private Long id;

    @Column(name = "TIPO")
    private String tipo; 

    @Column(name = "VALOR")
    private Double valor;

    @Column(name = "DATA_MOV")
    private LocalDate dataMovimentacao = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "ID_CONTA") 
    private Conta conta;
}
