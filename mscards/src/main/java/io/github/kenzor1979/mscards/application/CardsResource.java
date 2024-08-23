package io.github.kenzor1979.mscards.application;

import io.github.kenzor1979.mscards.application.representation.CartaoSaveRequest;
import io.github.kenzor1979.mscards.application.representation.CartoesPorClienteResponse;
import io.github.kenzor1979.mscards.domain.Card;
import io.github.kenzor1979.mscards.domain.ClienteCard;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("cards")
@RequiredArgsConstructor
public class CardsResource {

    private final CardService cardService;
    private final ClienteCardService clienteCardService;

    @GetMapping
    public String status() {
        return "ok";
    }

    @PostMapping
    public ResponseEntity cadastra(@RequestBody CartaoSaveRequest request ) {
        Card card = request.toModel();
        cardService.save(card);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(params = "renda")
    public ResponseEntity<List<Card>> getCartoesRendaAteh(@RequestParam("renda") Long renda) {
        List<Card> list = cardService.getCartoesRendaMenorIgual(renda);
        return ResponseEntity.ok(list);
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<List<CartoesPorClienteResponse>> getCartoesByCliente (
            @RequestParam("cpf") String cpf) {
        List<ClienteCard> lista = clienteCardService.listCartoesByCpf(cpf);
        List<CartoesPorClienteResponse> resultList = lista.stream()
                .map(CartoesPorClienteResponse::fromModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resultList);
    }
}
