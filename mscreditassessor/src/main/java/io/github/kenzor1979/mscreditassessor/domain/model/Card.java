package io.github.kenzor1979.mscreditassessor.domain.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Card {
    private Long id;
    private String nome;
    private String bandeira;
    private BigDecimal limiteBasico;
}
