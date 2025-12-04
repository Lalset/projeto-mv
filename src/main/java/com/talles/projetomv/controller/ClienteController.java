package com.talles.projetomv.controller;

import com.talles.projetomv.model.Cliente;
import com.talles.projetomv.repository.ClienteRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping
    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    @PostMapping
    public Cliente criar(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @PutMapping("/{id}")
    public Cliente atualizar(@PathVariable Long id, @RequestBody Cliente clienteAtualizado) {
        return clienteRepository.findById(id).map(c -> {
            c.setNome(clienteAtualizado.getNome());
            c.setTelefone(clienteAtualizado.getTelefone());
            return clienteRepository.save(c);
        }).orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        clienteRepository.deleteById(id);
    }
}
