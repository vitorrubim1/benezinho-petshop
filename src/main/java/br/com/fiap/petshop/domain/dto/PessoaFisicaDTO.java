package br.com.fiap.petshop.domain.dto;

import br.com.fiap.petshop.domain.entity.Authority;
import br.com.fiap.petshop.domain.entity.PessoaFisica;
import br.com.fiap.petshop.domain.entity.Usuario;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;
import java.util.Set;

public record PessoaFisicaDTO(
        Long id,
        String nome,
        @PastOrPresent LocalDate nascimento,
        String cpf,
        UsuarioDTO usuario
) {
    public static PessoaFisicaDTO of(PessoaFisica p) {
        return new PessoaFisicaDTO(p.getId(), p.getNome(), p.getNascimento(), p.getCpf(), UsuarioDTO.of(p.getUsuario()));
    }


    public static PessoaFisica of(PessoaFisicaDTO p) {
        return new PessoaFisica(p.id, p.nome, p.nascimento, p.cpf, UsuarioDTO.of(p.usuario));
    }
}