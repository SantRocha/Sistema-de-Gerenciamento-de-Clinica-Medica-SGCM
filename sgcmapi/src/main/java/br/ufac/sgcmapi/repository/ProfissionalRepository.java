package br.ufac.sgcmapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.ufac.sgcmapi.model.Profissional;

public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {

    @Query("SELECT p FROM Profissional p" +
        " LEFT JOIN Especialidade e ON e = p.especialidade" +
        " LEFT JOIN Unidade u ON u = p.unidade" +
        " WHERE p.nome LIKE %?1%" +
        " OR p.registroConselho LIKE %?1%" +
        " OR p.telefone LIKE %?1%" +
        " OR p.email LIKE %?1%" +
        " OR e.nome LIKE %?1%" +
        " OR u.nome LIKE %?1%")
    Page<Profissional> busca(String termoBusca, Pageable page);
    
}
