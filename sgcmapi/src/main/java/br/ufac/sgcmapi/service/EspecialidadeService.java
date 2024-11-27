package br.ufac.sgcmapi.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.ufac.sgcmapi.model.Especialidade;
import br.ufac.sgcmapi.repository.EspecialidadeRepository;

@Service
public class EspecialidadeService implements IService<Especialidade> {

    private final EspecialidadeRepository repo;

    public EspecialidadeService(EspecialidadeRepository repo) {
        this.repo = repo;
    }

    @Override
    @Cacheable(
        value = "especialidades",
        condition = "#termoBusca == null or #termoBusca.isBlank()"
    )
    public Page<Especialidade> get(String termoBusca, Pageable page) {
        if (termoBusca == null || termoBusca.isBlank()) {
            return repo.findAll(page);
        } else {
            return repo.busca(termoBusca, page);
        }
    }

    @Override
    @Cacheable(value = "especialidade", unless = "#result == null")
    public Especialidade get(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    @Caching(evict = {
        @CacheEvict(value = "especialidade", key = "#objeto.id"),
        @CacheEvict(value = "especialidades", allEntries = true)
    })
    public Especialidade save(Especialidade objeto) {
       return repo.save(objeto);
    }

    @Override
    @Caching(evict = {
        @CacheEvict(value = "especialidade", key = "#id"),
        @CacheEvict(value = "especialidades", allEntries = true)
    })
    public void delete(Long id) {
        repo.deleteById(id);
    }
    
}
