package com.talles.projetomv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.talles.projetomv.model.Conta;
import java.util.List;

public interface ContaRepository extends JpaRepository<Conta, Long> {
	List<Conta> findByClienteId(Long clienteId);
}
