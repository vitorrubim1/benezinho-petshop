package br.com.fiap.petshop.domain.entity;

import br.com.fiap.petshop.infra.security.entity.Pessoa;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "TB_DOCUMENTO")
public class Documento implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_DOCUMENTO")
    @SequenceGenerator(name = "SQ_DOCUMENTO", sequenceName = "SQ_DOCUMENTO", allocationSize = 1, initialValue = 1)
    @Column(name = "ID_DOCUMENTO")
    private Long id;

    @Column(name = "DB_NUMERO")
    private String numero;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "pessoa",
            referencedColumnName = "ID_PESSOA",
            foreignKey = @ForeignKey(name = "FK_DOCUMENTO_PESSOA")
    )
    private Pessoa pessoa;

    protected Documento() {
    }

    public Documento(Long id, String numero, Pessoa pessoa) {
        this.id = id;
        this.numero = numero;
        this.pessoa = pessoa;
    }

    public Long getId() {
        return id;
    }

    public Documento setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNumero() {
        return numero;
    }

    public Documento setNumero(String numero) {
        this.numero = numero;
        return this;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public Documento setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }


    @Override
    public String toString() {
        return "Documento{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", pessoa=" + pessoa +
                '}';
    }
}
