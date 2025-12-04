package com.talles.projetomv.controller;

import com.talles.projetomv.model.Movimentacao;
import com.talles.projetomv.repository.MovimentacaoRepository;
import com.talles.projetomv.repository.ContaRepository;
import com.talles.projetomv.service.ReceitaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimentacoes")
public class MovimentacaoController {

    private final MovimentacaoRepository movimentacaoRepository;
    private final ContaRepository contaRepository;
    private final ReceitaService receitaService;

    public MovimentacaoController(MovimentacaoRepository movimentacaoRepository,
                                  ContaRepository contaRepository,
                                  ReceitaService receitaService) {
        this.movimentacaoRepository = movimentacaoRepository;
        this.contaRepository = contaRepository;
        this.receitaService = receitaService;
    }

    @GetMapping
    public List<Movimentacao> listar() {
        return movimentacaoRepository.findAll();
    }

    @GetMapping("/conta/{idConta}")
    public List<Movimentacao> listarPorConta(@PathVariable Long idConta) {
        return movimentacaoRepository.findByContaId(idConta);
    }

    @PostMapping("/conta/{idConta}")
    public Movimentacao criar(@PathVariable Long idConta, @RequestBody Movimentacao movimentacao) {
        var conta = contaRepository.findById(idConta)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada!"));
        movimentacao.setConta(conta);
        return movimentacaoRepository.save(movimentacao);
    }

    @PutMapping("/{id}")
    public Movimentacao atualizar(@PathVariable Long id, @RequestBody Movimentacao novaMovimentacao) {
        return movimentacaoRepository.findById(id).map(m -> {
            m.setTipo(novaMovimentacao.getTipo());
            m.setValor(novaMovimentacao.getValor());
            return movimentacaoRepository.save(m);
        }).orElseThrow(() -> new RuntimeException("Movimentação não encontrada!"));
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        movimentacaoRepository.deleteById(id);
    }

    @GetMapping("/receita/{idConta}")
    public String calcularReceita(@PathVariable Long idConta) {
        var movs = movimentacaoRepository.findByContaId(idConta);
        double valor = receitaService.calcularTaxa(movs.size());
        return "A taxa total a ser paga pela XPTO é: R$ " + valor;
    }
}
