package io.github.kenzor1979.mscards.application.representation;

import io.github.kenzor1979.mscards.domain.ClienteCard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartoesPorClienteResponse {
    private String nome;
    private String bandeira;
    private BigDecimal limiteLiberado;

    public static CartoesPorClienteResponse fromModel(ClienteCard model) {
        return new CartoesPorClienteResponse(
                model.getCard().getNome(),
                model.getCard().getBandeira().toString(),
                model.getLimite()
        );
    }
}
