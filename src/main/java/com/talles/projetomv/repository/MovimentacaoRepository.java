package com.talles.projetomv.repository;

import com.talles.projetomv.model.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
    List<Movimentacao> findByContaId(Long contaId);
}
