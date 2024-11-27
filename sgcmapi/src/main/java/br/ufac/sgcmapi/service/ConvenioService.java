package br.ufac.sgcmapi.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import br.ufac.sgcmapi.model.Convenio;
import br.ufac.sgcmapi.repository.ConvenioRepository;
import br.ufac.sgcmapi.service.dto.ItemRespostaAnsDto;
import br.ufac.sgcmapi.service.dto.RespostaAnsDto;

@Service
public class ConvenioService implements IService<Convenio> {

    private final ConvenioRepository repo;

    public ConvenioService(ConvenioRepository repo) {
        this.repo = repo;
    }

    @Override
    @Cacheable(
        value = "convenios",
        condition = "#termoBusca == null or #termoBusca.isBlank()"
    )
    public Page<Convenio> get(String termoBusca, Pageable page) {
        if (termoBusca == null || termoBusca.isBlank()) {
            return repo.findAll(page);
        } else {
            return repo.busca(termoBusca, page);
        }
    }

    @Override
    @Cacheable(value = "convenio", unless = "#result == null")
    public Convenio get(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    @Caching(evict = {
        @CacheEvict(value = "convenio", key = "#objeto.id"),
        @CacheEvict(value = "convenios", allEntries = true)
    })
    public Convenio save(Convenio objeto) {
        return repo.save(objeto);
    }

    @Override
    @Caching(evict = {
        @CacheEvict(value = "convenio", key = "#id"),
        @CacheEvict(value = "convenios", allEntries = true)
    })
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Scheduled(fixedDelay = 30000)
    public void verificarRegistroAns() {
        String url = "https://www.ans.gov.br/operadoras-entity/v1/operadoras";
        List<Convenio> convenios = repo.findAll();
        convenios.forEach(convenio -> {
            String cnpjSemFormatacao = convenio.getCnpj()
                .replaceAll("[^0-9]", "");
            RestClient restClient = RestClient.create();
            RespostaAnsDto resultado = restClient.get()
                .uri(url + "?cnpj={cnpj}", cnpjSemFormatacao)
                .retrieve()
                .body(RespostaAnsDto.class);
            if (!resultado.content().isEmpty()) {
                ItemRespostaAnsDto item = resultado.content().get(0);
                convenio.setAtivo(item.ativa());
                repo.save(convenio);
            }
        });
    }
    
}
