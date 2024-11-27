package br.ufac.sgcmapi.controller.dto;

import java.time.LocalDate;

import br.ufac.sgcmapi.validator.HorarioAtendimento;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AtendimentoDto(
    Long id,
    @NotNull @FutureOrPresent LocalDate data,
    @NotBlank @HorarioAtendimento String hora,
    @NotNull Long profissional_id,
    String profissional_nome,
    String unidade_nome,
    Long convenio_id,
    String convenio_nome,
    @NotNull Long paciente_id,
    String paciente_nome,
    String status
) {
    
}
