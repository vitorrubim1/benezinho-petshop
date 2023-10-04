package br.com.fiap.petshop.domain.entity.servico;

import br.com.fiap.petshop.domain.entity.animal.Animal;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Consulta extends Servico {
    public Consulta() {
    }

    public Consulta(Long id, BigDecimal valor, LocalDateTime abertura, LocalDateTime autorizacao, LocalDateTime conclusao, String descricao, String observacao, Animal animal) {
        super(id, valor, abertura, autorizacao, conclusao, descricao, observacao, animal);
    }

    @Override
    public String toString() {
        return "Consulta{} " + super.toString();
    }
}
