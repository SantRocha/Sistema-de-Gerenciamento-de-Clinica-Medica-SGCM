package br.ufac.sgcmapi.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.ufac.sgcmapi.model.Paciente;
import br.ufac.sgcmapi.repository.PacienteRepository;

@Service
public class PacienteService implements IService<Paciente> {

    private final PacienteRepository repo;

    public PacienteService(PacienteRepository repo) {
        this.repo = repo;
    }

    @Override
    @Cacheable(
        value = "pacientes",
        condition = "#termoBusca == null or #termoBusca.isBlank()"
    )
    public Page<Paciente> get(String termoBusca, Pageable page) {
        if (termoBusca == null || termoBusca.isBlank()) {
            return repo.findAll(page);
        } else {
            return repo.busca(termoBusca, page);
        }
    }

    @Override
    @Cacheable(value = "paciente", unless = "#result == null")
    public Paciente get(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    @Caching(evict = {
        @CacheEvict(value = "paciente", key = "#objeto.id"),
        @CacheEvict(value = "pacientes", allEntries = true)
    })
    public Paciente save(Paciente objeto) {
        return repo.save(objeto);
    }

    @Override
    @Caching(evict = {
        @CacheEvict(value = "paciente", key = "#id"),
        @CacheEvict(value = "pacientes", allEntries = true)
    })
    public void delete(Long id) {
        repo.deleteById(id);
    }
    
}
