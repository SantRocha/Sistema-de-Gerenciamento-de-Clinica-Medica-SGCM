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

import br.ufac.sgcmapi.model.Especialidade;
import br.ufac.sgcmapi.service.EspecialidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/config/especialidade", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(
    name = "Especialidade",
    description = "Endpoints para gerenciar especialidades"
)
public class EspecialidadeController implements IController<Especialidade> {

    private final EspecialidadeService servico;

    public EspecialidadeController(EspecialidadeService servico) {
        this.servico = servico;
    }

    @Override
    @GetMapping("/")
    @Operation(
        summary = "Obter especialidades ou filtrar por termo de busca",
        description = "Obtém uma lista paginada de todas as especialidades cadastradas ou que contenham o termo de busca informado"
    )
    public ResponseEntity<Page<Especialidade>> get(
            @RequestParam(required = false) String termoBusca,
            @RequestParam(required = false, defaultValue = "false") boolean unpaged,
            @SortDefault.SortDefaults({
                @SortDefault(sort = "nome", direction = Sort.Direction.ASC)
            })
            @ParameterObject Pageable page) {
        if (unpaged) {
            page = Pageable.unpaged();
        }
        Page<Especialidade> registros = servico.get(termoBusca, page);
        return ResponseEntity.ok(registros);
    }

    @Override
    @GetMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Especialidade encontrada"),
        @ApiResponse(
            responseCode = "404",
            description = "Especialidade não encontrada",
            content = @Content(
                examples = @ExampleObject("")
            )
        )
    })
    @Operation(
        summary = "Obter especialidade por ID",
        description = "Obtém a especialidade cadastrada com o ID informado"
    )
    public ResponseEntity<Especialidade> get(@PathVariable("id") Long id) {
        Especialidade registro = servico.get(id);
        if (registro == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(registro);
    }

    @Override
    @PostMapping("/")
    @Operation(
        summary = "Cadastrar especialidade",
        description = "Cadastra uma nova especialidade"
    )
    public ResponseEntity<Especialidade> insert(@RequestBody Especialidade objeto) {
        Especialidade registro = servico.save(objeto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registro);
    }

    @Override
    @PutMapping("/")
    @Operation(
        summary = "Atualizar especialidade",
        description = "Atualiza a especialidade cadastrada"
    )
    public ResponseEntity<Especialidade> update(@RequestBody Especialidade objeto) {
        Especialidade registro = servico.save(objeto);
        return ResponseEntity.ok(registro);
    }

    @Override
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Excluir especialidade",
        description = "Exclui a especialidade cadastrada com o ID informado"
    )
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        servico.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
    
}
