package br.ufac.sgcmapi.controller.dto;

public record UsuarioDto(
    Long id,
    String nomeCompleto,
    String nomeUsuario,
    String papel,
    boolean ativo
) {

    // public static UsuarioDto toDto(Usuario usuario) {
    //     UsuarioDto dto = new UsuarioDto(
    //         usuario.getId(),
    //         usuario.getNomeCompleto(),
    //         usuario.getNomeUsuario(),
    //         usuario.getPapel().name(),
    //         usuario.isAtivo());
    //     return dto;
    // }

    // public static Usuario toEntity(UsuarioDto dto) {
    //     Usuario usuario = new Usuario();
    //     usuario.setId(dto.id());
    //     usuario.setNomeCompleto(dto.nomeCompleto());
    //     usuario.setNomeUsuario(dto.nomeUsuario());
    //     usuario.setPapel(EPapel.valueOf(dto.papel()));
    //     usuario.setAtivo(dto.ativo());
    //     return usuario;
    // }
    
}
