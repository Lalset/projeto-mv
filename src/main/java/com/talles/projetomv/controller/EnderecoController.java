package com.talles.projetomv.controller;

import com.talles.projetomv.model.Endereco;
import com.talles.projetomv.repository.EnderecoRepository;
import com.talles.projetomv.repository.ClienteRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    private final EnderecoRepository enderecoRepository;
    private final ClienteRepository clienteRepository;

    public EnderecoController(EnderecoRepository enderecoRepository, ClienteRepository clienteRepository) {
        this.enderecoRepository = enderecoRepository;
        this.clienteRepository = clienteRepository;
    }

    @GetMapping
    public List<Endereco> listar() {
        return enderecoRepository.findAll();
    }

    @GetMapping("/cliente/{idCliente}")
    public List<Endereco> listarPorCliente(@PathVariable Long idCliente) {
        return enderecoRepository.findByClienteId(idCliente);
    }

    @PostMapping("/cliente/{idCliente}")
    public Endereco criar(@PathVariable Long idCliente, @RequestBody Endereco endereco) {
        var cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));
        endereco.setCliente(cliente);
        return enderecoRepository.save(endereco);
    }

    @PutMapping("/{id}")
    public Endereco atualizar(@PathVariable Long id, @RequestBody Endereco enderecoAtualizado) {
        return enderecoRepository.findById(id).map(e -> {
            e.setRua(enderecoAtualizado.getRua());
            e.setNumero(enderecoAtualizado.getNumero());
            e.setCidade(enderecoAtualizado.getCidade());
            e.setEstado(enderecoAtualizado.getEstado());
            e.setCep(enderecoAtualizado.getCep());
            return enderecoRepository.save(e);
        }).orElseThrow(() -> new RuntimeException("Endereço não encontrado!"));
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        enderecoRepository.deleteById(id);
    }
}
