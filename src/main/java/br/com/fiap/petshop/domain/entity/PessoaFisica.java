package br.com.fiap.petshop.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "TB_PF", uniqueConstraints = {
        @UniqueConstraint(name = "UK_PF_CPF", columnNames = "NR_CPF")
})
@DiscriminatorValue("PF")
public class PessoaFisica extends Pessoa {

    @Column(name = "NR_CPF", nullable = false)
    private String cpf;

    @OneToOne(mappedBy = "pessoa", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Usuario usuario;

    public PessoaFisica() {
    }

    public PessoaFisica(Long id, String nome, LocalDate nascimento, String cpf, Usuario usuario) {
        super(id, nome, nascimento);
        this.cpf = cpf;
        this.usuario = usuario;
    }

    public String getCpf() {
        return cpf;
    }

    public PessoaFisica setCpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public PessoaFisica setUsuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    @Override
    public String toString() {
        return "PessoaFisica{" +
                "cpf='" + cpf + '\'' +
                ", usuario=" + usuario +
                "} " + super.toString();
    }
}
