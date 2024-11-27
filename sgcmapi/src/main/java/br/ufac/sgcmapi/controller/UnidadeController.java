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

import br.ufac.sgcmapi.model.Unidade;
import br.ufac.sgcmapi.service.UnidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/config/unidade", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(
    name = "Unidade",
    description = "Endpoints para gerenciar unidades de atendimento"
)
public class UnidadeController implements IController<Unidade> {

    private final UnidadeService servico;

    public UnidadeController(UnidadeService servico) {
        this.servico = servico;
    }

    @Override
    @GetMapping("/")
    @Operation(
        summary = "Obter unidades ou filtrar por termo de busca",
        description = "Obtém uma lista paginada de todas as unidades cadastradas ou que contenham o termo de busca informado"
    )
    public ResponseEntity<Page<Unidade>> get(
            @RequestParam(required = false) String termoBusca,
            @RequestParam(required = false, defaultValue = "false") boolean unpaged,
            @SortDefault.SortDefaults({
                @SortDefault(sort = "nome", direction = Sort.Direction.ASC)
            })
            @ParameterObject Pageable page) {
        if (unpaged) {
            page = Pageable.unpaged();
        }
        Page<Unidade> registros = servico.get(termoBusca, page);
        return ResponseEntity.ok(registros);
    }

    @Override
    @GetMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Profissional encontrado"),
        @ApiResponse(
            responseCode = "404",
            description = "Profissional não encontrado",
            content = @Content(
                examples = @ExampleObject("")
            )
        )
    })
    @Operation(
        summary = "Obter unidade por ID",
        description = "Obtém a unidade de atendimento com o ID informado"
    )
    public ResponseEntity<Unidade> get(@PathVariable("id") Long id) {
        Unidade registro = servico.get(id);
        if (registro == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(registro);
    }

    @Override
    @PostMapping("/")
    @Operation(
        summary = "Cadastrar unidade",
        description = "Cadastra uma nova unidade de atendimento"
    )
    public ResponseEntity<Unidade> insert(@RequestBody Unidade objeto) {
        Unidade registro = servico.save(objeto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registro);
    }

    @Override
    @PutMapping("/")
    @Operation(
        summary = "Atualizar unidade",
        description = "Atualiza os dados de uma unidade de atendimento"
    )
    public ResponseEntity<Unidade> update(@RequestBody Unidade objeto) {
        Unidade registro = servico.save(objeto);
        return ResponseEntity.ok(registro);
    }

    @Override
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Excluir unidade",
        description = "Exclui uma unidade de atendimento"
    )
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        servico.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
    
}
