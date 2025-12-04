package com.talles.projetomv.controller;

import com.talles.projetomv.service.ReceitaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receita")
public class ReceitaController {

    private final ReceitaService receitaService;

    public ReceitaController(ReceitaService receitaService) {
        this.receitaService = receitaService;
    }

    @GetMapping("/{qtdMov}")
    public String calcular(@PathVariable int qtdMov) {
        double valor = receitaService.calcularTaxa(qtdMov);
        return "A taxa total a ser paga pela XPTO é: R$ " + valor;
    }
}
