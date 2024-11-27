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

import br.ufac.sgcmapi.model.Paciente;
import br.ufac.sgcmapi.service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping(value = "/paciente", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(
    name = "Paciente",
    description = "Endpoints para gerenciar pacientes"
)
public class PacienteController implements IController<Paciente> {

    private final PacienteService servico;

    public PacienteController(PacienteService servico) {
        this.servico = servico;
    }

    @Override
    @GetMapping("/")
    @Operation(
        summary = "Obter pacientes ou filtrar por termo de busca",
        description = "Obtém uma lista paginada de todos os pacientes cadastrados ou que contenham o termo de busca informado"
    )
    public ResponseEntity<Page<Paciente>> get(
            @RequestParam(required = false) String termoBusca,
            @RequestParam(required = false, defaultValue = "false") boolean unpaged,
            @SortDefault.SortDefaults({
                @SortDefault(sort = "nome", direction = Sort.Direction.ASC)
            })
            @ParameterObject Pageable page) {        
        if (unpaged) {
            page = Pageable.unpaged();
        }
        Page<Paciente> registros = servico.get(termoBusca, page);
        return ResponseEntity.ok(registros);
    }

    @Override
    @GetMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Paciente encontrado"),
        @ApiResponse(
            responseCode = "404",
            description = "Paciente não encontrado",
            content = @Content(
                examples = @ExampleObject("")
            )
        )
    })
    @Operation(
        summary = "Obter paciente por ID",
        description = "Obtém o paciente com o ID informado"
    )
    public ResponseEntity<Paciente> get(@PathVariable("id") Long id) {
        Paciente registro = servico.get(id);
        if (registro == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(registro);
    }

    @Override
    @PostMapping("/")
    @Operation(
        summary = "Cadastrar paciente",
        description = "Cadastra um novo paciente"
    )
    public ResponseEntity<Paciente> insert(@RequestBody Paciente objeto) {
        Paciente registro = servico.save(objeto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registro);
    }

    @Override
    @PutMapping("/")
    @Operation(
        summary = "Atualizar paciente",
        description = "Atualiza um paciente existente"
    )
    public ResponseEntity<Paciente> update(@RequestBody Paciente objeto) {
        Paciente registro = servico.save(objeto);
        return ResponseEntity.ok(registro);
    }

    @Override
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Deletar paciente",
        description = "Deleta o paciente com o ID informado"
    )
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        servico.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
    
}
