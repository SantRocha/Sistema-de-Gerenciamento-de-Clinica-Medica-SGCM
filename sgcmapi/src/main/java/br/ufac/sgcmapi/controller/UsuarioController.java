package br.ufac.sgcmapi.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufac.sgcmapi.controller.dto.UsuarioDto;
import br.ufac.sgcmapi.controller.mapper.UsuarioMapper;
import br.ufac.sgcmapi.model.Usuario;
import br.ufac.sgcmapi.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping(value = "/config/usuario", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(
    name = "Usuario",
    description = "Endpoints para gerenciar usuários"
)
public class UsuarioController implements IController<Usuario> {

    private final UsuarioService servico;
    private final UsuarioMapper mapper;

    public UsuarioController(
            UsuarioService servico,
            UsuarioMapper mapper) {
        this.servico = servico;
        this.mapper = mapper;
    }

    @Override
    @GetMapping("/")
    @Operation(
        summary = "Obter usuários ou filtrar por termo de busca",
        description = "Obtém uma lista paginada de todos os usuários cadastrados ou que contenham o termo de busca informado"
    )
    public ResponseEntity<Page<UsuarioDto>> get(
            @RequestParam(required = false) String termoBusca,
            @RequestParam(required = false, defaultValue = "false") boolean unpaged,
            @SortDefault.SortDefaults({
                @SortDefault(sort = "nomeCompleto", direction = Sort.Direction.ASC)
            })
            @ParameterObject Pageable page) {
        if (unpaged) {
            page = Pageable.unpaged();
        }
        Page<Usuario> registros = servico.get(termoBusca, page);
        Page<UsuarioDto> dtos = registros.map(mapper::toDto);
        return ResponseEntity.ok(dtos);
    }

    @Override
    @GetMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
        @ApiResponse(
            responseCode = "404",
            description = "Usuário não encontrado",
            content = @Content(
                examples = @ExampleObject("")
            )
        )
    })
    @Operation(
        summary = "Obter um usuário pelo ID",
        description = "Obtém um usuário cadastrado pelo ID informado"
    )
    public ResponseEntity<UsuarioDto> get(@PathVariable("id") Long id) {
        Usuario registro = servico.get(id);
        if (registro == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        UsuarioDto dto = mapper.toDto(registro);
        return ResponseEntity.ok(dto);
    }

    @Override
    @PostMapping("/")
    @Operation(
        summary = "Cadastrar um novo usuário",
        description = "Cadastra um novo usuário no sistema"
    )
    public ResponseEntity<UsuarioDto> insert(@RequestBody Usuario objeto) {
        Usuario registro = servico.save(objeto);
        UsuarioDto dto = mapper.toDto(registro);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @Override
    @PutMapping("/")
    @Operation(
        summary = "Atualizar um usuário",
        description = "Atualiza um usuário cadastrado no sistema"
    )
    public ResponseEntity<UsuarioDto> update(@RequestBody Usuario objeto) {
        Usuario registro = servico.save(objeto);
        UsuarioDto dto = mapper.toDto(registro);
        return ResponseEntity.ok(dto);
    }

    @Override
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Excluir um usuário",
        description = "Exclui um usuário cadastrado no sistema"
    )
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        servico.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
    
}
