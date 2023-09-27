package br.com.fiap.petshop.domain.dto;

import br.com.fiap.petshop.domain.entity.Authority;
import br.com.fiap.petshop.domain.entity.Pessoa;
import br.com.fiap.petshop.domain.entity.PessoaFisica;
import br.com.fiap.petshop.domain.entity.Usuario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record UsuarioDTO(Long id, @NotNull @Email String username, Long pessoa, String authorization,
                         Set<Authority> authorities) {

    public static UsuarioDTO of(Usuario p, String key) {
        return new UsuarioDTO(p.getId(), p.getUsername(), p.getPessoa().getId(), key, p.getAuthorities());
    }

    public static UsuarioDTO of(Usuario p) {
        return new UsuarioDTO(p.getId(), p.getUsername(), p.getPessoa().getId(), "", p.getAuthorities());
    }


    public static Usuario of(UsuarioDTO p) {
        var pf = new PessoaFisica();
        pf.setId(p.pessoa);
        return new Usuario(p.id, p.username, "", pf, p.authorities);
    }


}
