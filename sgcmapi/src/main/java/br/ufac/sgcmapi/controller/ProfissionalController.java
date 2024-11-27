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

import br.ufac.sgcmapi.controller.dto.ProfissionalDto;
import br.ufac.sgcmapi.controller.mapper.ProfissionalMapper;
import br.ufac.sgcmapi.model.Profissional;
import br.ufac.sgcmapi.service.ProfissionalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/profissional", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(
    name = "Profissional",
    description = "Endpoints para gerenciar profissionais"
)
public class ProfissionalController implements IController<ProfissionalDto> {

    private final ProfissionalService servico;
    private final ProfissionalMapper mapper;

    public ProfissionalController(
            ProfissionalService servico,
            ProfissionalMapper mapper) {
        this.servico = servico;
        this.mapper = mapper;
    }

    @Override
    @GetMapping("/")
    @Operation(
        summary = "Obter profissionais ou filtrar por termo de busca",
        description = "Obtém uma lista paginada de todos os profissionais cadastrados ou que contenham o termo de busca informado"
    )
    public ResponseEntity<Page<ProfissionalDto>> get(
            @RequestParam(required = false) String termoBusca,
            @RequestParam(required = false, defaultValue = "false") boolean unpaged,
            @SortDefault.SortDefaults({
                @SortDefault(sort = "nome", direction = Sort.Direction.ASC)
            })
            @ParameterObject Pageable page) {
        if (unpaged) {
            page = Pageable.unpaged();
        }
        Page<Profissional> registros = servico.get(termoBusca, page);
        Page<ProfissionalDto> dtos = registros.map(mapper::toDto);
        return ResponseEntity.ok(dtos);
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
        summary = "Obter profissional por ID",
        description = "Obtém o profissional com o ID informado"
    )
    public ResponseEntity<ProfissionalDto> get(@PathVariable("id") Long id) {
        Profissional registro = servico.get(id);
        if (registro == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        ProfissionalDto dto = mapper.toDto(registro);
        return ResponseEntity.ok(dto);
    }

    @Override
    @PostMapping("/")
    @Operation(
        summary = "Cadastrar profissional",
        description = "Cadastra um novo profissional"
    )
    public ResponseEntity<ProfissionalDto> insert(@RequestBody @Valid ProfissionalDto objeto) {
        Profissional objetoConvertido = mapper.toEntity(objeto);
        Profissional registro = servico.save(objetoConvertido);
        ProfissionalDto dto = mapper.toDto(registro);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @Override
    @PutMapping("/")
    @Operation(
        summary = "Atualizar profissional",
        description = "Atualiza um profissional existente"
    )
    public ResponseEntity<ProfissionalDto> update(@RequestBody @Valid ProfissionalDto objeto) {
        Profissional objetoConvertido = mapper.toEntity(objeto);
        Profissional registro = servico.save(objetoConvertido);
        ProfissionalDto dto = mapper.toDto(registro);
        return ResponseEntity.ok(dto);
    }

    @Override
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Excluir profissional",
        description = "Exclui o profissional com o ID informado"
    )
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        servico.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
    
}
