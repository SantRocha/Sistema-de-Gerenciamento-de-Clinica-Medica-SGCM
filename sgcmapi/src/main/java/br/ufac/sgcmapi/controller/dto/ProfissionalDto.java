package br.ufac.sgcmapi.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ProfissionalDto(
    Long id,
    @NotBlank String nome,
    @NotBlank
    @Email(
        message = "deve ser uma conta do domínio @ufac.br",
        regexp = "^(.+)@ufac\\.br$")
    String email,
    @NotBlank String registroConselho,
    @NotBlank
    @Pattern(regexp = "^\\(\\d{2}\\) \\d{4,5}-\\d{4}$")
    String telefone,
    @NotNull Long especialidade_id,
    String especialidade_nome,
    @NotNull Long unidade_id,
    String unidade_nome
) {
    
}
