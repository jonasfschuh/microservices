package io.github.kenzor1979.mscreditassessor.domain.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustomerCards {
    private String nome;
    private String bandeira;
    private BigDecimal limiteLiberado;
}
