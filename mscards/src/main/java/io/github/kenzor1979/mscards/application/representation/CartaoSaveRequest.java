package io.github.kenzor1979.mscards.application.representation;

import io.github.kenzor1979.mscards.domain.BandeiraCartao;
import io.github.kenzor1979.mscards.domain.Card;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartaoSaveRequest {
    private String nome;
    private BandeiraCartao bandeira;
    private BigDecimal renda;
    private BigDecimal limite;

    public Card toModel() {
        return new Card(nome, bandeira, renda, limite);
    }
}
