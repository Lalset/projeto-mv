package com.talles.projetomv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.talles.projetomv.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {}
