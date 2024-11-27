package br.ufac.sgcmapi.controller.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.ufac.sgcmapi.controller.dto.AtendimentoDto;
import br.ufac.sgcmapi.model.Atendimento;

@Mapper(componentModel = "spring")
public interface AtendimentoMapper {

    @Mapping(target = "profissional_id", source = "profissional.id")
    @Mapping(target = "profissional_nome", source = "profissional.nome")
    @Mapping(target = "unidade_nome", source = "profissional.unidade.nome")
    @Mapping(target = "convenio_id", source = "convenio.id")
    @Mapping(target = "convenio_nome", source = "convenio.nome")
    @Mapping(target = "paciente_id", source = "paciente.id")
    @Mapping(target = "paciente_nome", source = "paciente.nome")
    AtendimentoDto toDto(Atendimento atendimento);

    @InheritInverseConfiguration
    Atendimento toEntity(AtendimentoDto dto);
    
}
