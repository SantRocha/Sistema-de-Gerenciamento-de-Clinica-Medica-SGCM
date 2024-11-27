package br.ufac.sgcmapi.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.ufac.sgcmapi.model.Usuario;
import br.ufac.sgcmapi.repository.UsuarioRepository;

@Service
public class UsuarioService implements IService<Usuario> {

    private final UsuarioRepository repo;

    public UsuarioService(UsuarioRepository repo) {
        this.repo = repo;
    }

    @Override
    @Cacheable(
        value = "usuarios",
        condition = "#termoBusca == null or #termoBusca.isBlank()"
    )
    public Page<Usuario> get(String termoBusca, Pageable page) {
        if (termoBusca == null || termoBusca.isBlank()) {
            return repo.findAll(page);
        } else {
            return repo.busca(termoBusca, page);
        }
    }

    @Override
    @Cacheable(value = "usuario", unless = "#result == null")
    public Usuario get(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    @Caching(evict = {
        @CacheEvict(value = "usuario", key = "#objeto.id"),
        @CacheEvict(value = "usuarios", allEntries = true)
    })
    public Usuario save(Usuario objeto) {
        if (objeto.getSenha() == null || objeto.getSenha().isBlank()) {
            Long id = objeto.getId();
            Usuario usuario = get(id);
            if (usuario != null) {
                objeto.setSenha(usuario.getSenha());
            }
        } else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String senhaCriptografada = passwordEncoder.encode(objeto.getSenha());
            objeto.setSenha(senhaCriptografada);
        }
        return repo.save(objeto);
    }

    @Override
    @Caching(evict = {
        @CacheEvict(value = "usuario", key = "#id"),
        @CacheEvict(value = "usuarios", allEntries = true)
    })
    public void delete(Long id) {
        repo.deleteById(id);
    }

    public Usuario getByNomeUsuario(String nomeUsuario) {
        return repo.findByNomeUsuario(nomeUsuario);
    }
    
}
