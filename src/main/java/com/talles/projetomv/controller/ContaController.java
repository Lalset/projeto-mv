package com.talles.projetomv.controller;

import com.talles.projetomv.model.Conta;
import com.talles.projetomv.repository.ContaRepository;
import com.talles.projetomv.repository.ClienteRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/contas")
public class ContaController {

    private final ContaRepository contaRepository;
    private final ClienteRepository clienteRepository;

    public ContaController(ContaRepository contaRepository, ClienteRepository clienteRepository) {
        this.contaRepository = contaRepository;
        this.clienteRepository = clienteRepository;
    }

    @GetMapping
    public List<Conta> listar() {
        return contaRepository.findAll();
    }

    @GetMapping("/cliente/{idCliente}")
    public List<Conta> listarPorCliente(@PathVariable Long idCliente) {
        return contaRepository.findByClienteId(idCliente);
    }

    @PostMapping("/cliente/{idCliente}")
    public Conta criar(@PathVariable Long idCliente, @RequestBody Conta conta) {
        var cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));
        conta.setCliente(cliente);
        return contaRepository.save(conta);
    }

    @PutMapping("/{id}")
    public Conta atualizar(@PathVariable Long id, @RequestBody Conta contaAtualizada) {
        return contaRepository.findById(id).map(conta -> {
            conta.setBanco(contaAtualizada.getBanco());
            conta.setAgencia(contaAtualizada.getAgencia());
            conta.setNumeroConta(contaAtualizada.getNumeroConta());
            return contaRepository.save(conta);
        }).orElseThrow(() -> new RuntimeException("Conta não encontrada!"));
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        contaRepository.deleteById(id);
    }
}
