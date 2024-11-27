package br.ufac.sgcmapi.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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

import br.ufac.sgcmapi.controller.dto.AtendimentoDto;
import br.ufac.sgcmapi.controller.mapper.AtendimentoMapper;
import br.ufac.sgcmapi.model.Atendimento;
import br.ufac.sgcmapi.service.AtendimentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/atendimento", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(
    name = "Atendimento",
    description = "Endpoints para gerenciar atendimentos/agendamentos"
)
public class AtendimentoController implements IController<AtendimentoDto> {

    private final AtendimentoService servico;
    private final AtendimentoMapper mapper;

    public AtendimentoController(
            AtendimentoService servico,
            AtendimentoMapper mapper) {
        this.servico = servico;
        this.mapper = mapper;
    }

    @Override
    @GetMapping("/")
    @Operation(
        summary = "Obter atendimentos ou filtrar por termo de busca",
        description = "Obtém uma lista paginada de todos os atendimentos ou agendamentos cadastrados ou que contenham o termo de busca informado"
    )
    public ResponseEntity<Page<AtendimentoDto>> get(
            @RequestParam(required = false) String termoBusca,
            @RequestParam(required = false, defaultValue = "false") boolean unpaged,
            @SortDefault.SortDefaults({
                @SortDefault(sort = "data", direction = Sort.Direction.ASC),
                @SortDefault(sort = "hora", direction = Sort.Direction.ASC)
            })
            @ParameterObject Pageable page) {
        if (unpaged) {
            page = Pageable.unpaged();
        }
        Page<Atendimento> registros = servico.get(termoBusca, page);
        Page<AtendimentoDto> dtos = registros.map(mapper::toDto);
        return ResponseEntity.ok(dtos);
    }

    @Override
    @GetMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Atendimento encontrado"),
        @ApiResponse(
            responseCode = "404",
            description = "Atendimento não encontrado",
            content = @Content(
                examples = @ExampleObject("")
            )
        )
    })
    @Operation(
        summary = "Obter atendimento por ID",
        description = "Obtém um atendimento ou agendamento específico a partir do ID informado"
    )
    public ResponseEntity<AtendimentoDto> get(@PathVariable("id") Long id) {
        Atendimento registro = servico.get(id);
        if (registro == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        AtendimentoDto dto = mapper.toDto(registro);
        return ResponseEntity.ok(dto);
    }

    @Override
    @PostMapping("/")
    @Operation(
        summary = "Cadastrar novo atendimento",
        description = "Cadastra um novo atendimento ou agendamento no sistema"
    )
    public ResponseEntity<AtendimentoDto> insert(@RequestBody @Valid AtendimentoDto objeto) {
        Atendimento objetoConvertido = mapper.toEntity(objeto);
        Atendimento registro = servico.save(objetoConvertido);
        AtendimentoDto dto= mapper.toDto(registro);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @Override
    @PutMapping("/")
    @Operation(
        summary = "Atualizar atendimento",
        description = "Atualiza um atendimento ou agendamento existente no sistema"
    )
    public ResponseEntity<AtendimentoDto> update(@RequestBody @Valid AtendimentoDto objeto) {
        Atendimento objetoConvertido = mapper.toEntity(objeto);
        Atendimento registro = servico.save(objetoConvertido);
        AtendimentoDto dto= mapper.toDto(registro);
        return ResponseEntity.ok(dto);
    }

    @Override
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Cancelar atendimento",
        description = "Cancela um atendimento ou agendamento do sistema"
    )
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        servico.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PutMapping("/status/{id}")
    @Operation(
        summary = "Atualizar status do atendimento",
        description = "Atualiza o status de um atendimento ou agendamento"
    )
    public ResponseEntity<AtendimentoDto> updateStatus(@PathVariable("id") Long id) {
        Atendimento registro = servico.updateStatus(id);
        AtendimentoDto dto = mapper.toDto(registro);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/horarios-ocupados-profissional/{id}/{data}")
    @Operation(
        summary = "Obter horários ocupados por profissional",
        description = "Obtém uma lista de horários ocupados por um profissional em uma determinada data"
    )
    public ResponseEntity<List<LocalTime>> getHorariosOcupadosProfissional(@PathVariable("id") Long id,
            @PathVariable("data") LocalDate data) {
        List<LocalTime> horarios = servico.getHorariosOcupadosProfissional(id, data);
        return ResponseEntity.ok(horarios);
    }

    @GetMapping("/horarios-ocupados-paciente/{id}/{data}")
    @Operation(
        summary = "Obter horários ocupados por paciente",
        description = "Obtém uma lista de horários ocupados por um paciente em uma determinada data"
    )
    public ResponseEntity<List<LocalTime>> getHorariosOcupadosPaciente(@PathVariable("id") Long id,
            @PathVariable("data") LocalDate data) {
        List<LocalTime> horarios = servico.getHorariosOcupadosPaciente(id, data);
        return ResponseEntity.ok(horarios);
    }
    
}
