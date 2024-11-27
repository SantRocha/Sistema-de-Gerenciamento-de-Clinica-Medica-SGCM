package br.ufac.sgcmapi.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.ufac.sgcmapi.model.Atendimento;
import br.ufac.sgcmapi.model.EStatus;
import br.ufac.sgcmapi.repository.AtendimentoRepository;

@Service
public class AtendimentoService implements IService<Atendimento> {

    private final AtendimentoRepository repo;

    public AtendimentoService(AtendimentoRepository repo) {
        this.repo = repo;
    }

    @Override
    @Cacheable(
        value = "atendimentos",
        condition = "#termoBusca == null or #termoBusca.isBlank()"
    )
    public Page<Atendimento> get(String termoBusca, Pageable page) {
        if (termoBusca == null || termoBusca.isBlank()) {
            return repo.findAll(page);
        } else {
            return repo.busca(termoBusca, page);
        }
    }

    @Override
    @Cacheable(value = "atendimento", unless = "#result == null")
    public Atendimento get(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    @Caching(evict = {
        @CacheEvict(value = "atendimento", key = "#objeto.id"),
        @CacheEvict(value = "atendimentos", allEntries = true)
    })
    public Atendimento save(Atendimento objeto) {
        return repo.save(objeto);
    }

    @Override
    @Caching(evict = {
        @CacheEvict(value = "atendimento", key = "#id"),
        @CacheEvict(value = "atendimentos", allEntries = true)
    })
    public void delete(Long id) {
        Atendimento registro = this.get(id);
        registro.setStatus(EStatus.CANCELADO);
        repo.save(registro);
    }

    @CachePut(value = "atendimento", key = "#id")
    @CacheEvict(value = "atendimentos", allEntries = true)
    public Atendimento updateStatus(Long id) {
        Atendimento registro = this.get(id);
        EStatus novoStatus = registro.getStatus().proximo();
        registro.setStatus(novoStatus);
        this.save(registro);
        return registro;
    }

    public List<LocalTime> getHorariosOcupadosProfissional(Long id, LocalDate data) {
        return repo.horariosOcupadosProfissional(id, data);
    }

    public List<LocalTime> getHorariosOcupadosPaciente(Long id, LocalDate data) {
        return repo.horariosOcupadosPaciente(id, data);
    }
    
}
