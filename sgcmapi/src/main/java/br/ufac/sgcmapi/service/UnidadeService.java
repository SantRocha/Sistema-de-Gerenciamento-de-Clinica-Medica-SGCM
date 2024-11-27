package br.ufac.sgcmapi.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.ufac.sgcmapi.model.Unidade;
import br.ufac.sgcmapi.repository.UnidadeRepository;

@Service
public class UnidadeService implements IService<Unidade> {

    private final UnidadeRepository repo;

    public UnidadeService(UnidadeRepository repo) {
        this.repo = repo;
    }

    @Override
    @Cacheable(
        value = "unidades",
        condition = "#termoBusca == null or #termoBusca.isBlank()"
    )
    public Page<Unidade> get(String termoBusca, Pageable page) {
        if (termoBusca == null || termoBusca.isBlank()) {
            return repo.findAll(page);
        } else {
            return repo.busca(termoBusca, page);
        }
    }

    @Override
    @Cacheable(value = "unidade", unless = "#result == null")
    public Unidade get(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    @Caching(evict = {
        @CacheEvict(value = "unidade", key = "#objeto.id"),
        @CacheEvict(value = "unidades", allEntries = true)
    })
    public Unidade save(Unidade objeto) {
        return repo.save(objeto);
    }

    @Override
    @Caching(evict = {
        @CacheEvict(value = "unidade", key = "#id"),
        @CacheEvict(value = "unidades", allEntries = true)
    })
    public void delete(Long id) {
        repo.deleteById(id);        
    }
    
}
