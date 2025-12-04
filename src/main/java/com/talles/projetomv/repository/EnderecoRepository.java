package com.talles.projetomv.repository;

import com.talles.projetomv.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    List<Endereco> findByClienteId(Long idCliente);
}
