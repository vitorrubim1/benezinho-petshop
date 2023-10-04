package br.com.fiap.petshop.domain.entity;

import br.com.fiap.petshop.infra.security.entity.Pessoa;
import jakarta.persistence.*;

@Entity
@Table(name = "TB_TELEFONE")
public class Telefone {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_TELEFONE")
    @SequenceGenerator(name = "SQ_TELEFONE", sequenceName = "SQ_TELEFONE", allocationSize = 1, initialValue = 1)
    @Column(name = "ID_TELEFONE")
    private Long id;

    @Column(name = "TL_DDD")
    private int ddd;

    @Column(name = "TL_NUMERO")
    private String numero;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "pessoa",
            referencedColumnName = "ID_PESSOA",
            foreignKey = @ForeignKey(name = "FK_TELEFONE_PESSOA")
    )
    private Pessoa pessoa;

    public Telefone() {
    }


    public Telefone(int ddd, String numero, Pessoa pessoa) {
        this.ddd = ddd;
        this.numero = numero;
        this.pessoa = pessoa;
    }

    public Telefone(Long idTelefone, int ddd, String numero, Pessoa pessoa) {
        this.id = idTelefone;
        this.ddd = ddd;
        this.numero = numero;
        this.pessoa = pessoa;
    }


    public Long getId() {
        return id;
    }

    public Telefone setId(Long id) {
        this.id = id;
        return this;
    }

    public int getDdd() {
        return ddd;
    }

    public Telefone setDdd(int ddd) {
        this.ddd = ddd;
        return this;
    }

    public String getNumero() {
        return numero;
    }

    public Telefone setNumero(String numero) {
        this.numero = numero;
        return this;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public Telefone setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }


    @Override
    public String toString() {
        return "Telefone{" +
                "id=" + id +
                ", ddd=" + ddd +
                ", numero='" + numero + '\'' +
                ", pessoa=" + pessoa +
                '}';
    }
}

