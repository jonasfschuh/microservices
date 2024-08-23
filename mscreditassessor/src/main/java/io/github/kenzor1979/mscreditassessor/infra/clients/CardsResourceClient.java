package io.github.kenzor1979.mscreditassessor.infra.clients;

import io.github.kenzor1979.mscreditassessor.domain.model.Card;
import io.github.kenzor1979.mscreditassessor.domain.model.CustomerCards;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "mscards", path="/cards")
public interface CardsResourceClient {
    @GetMapping(params = "cpf")
    ResponseEntity<List<CustomerCards>> getCartoesByCliente (@RequestParam("cpf") String cpf);
    @GetMapping(params = "renda")
    ResponseEntity<List<Card>> getCartoesRendaAteh(@RequestParam("renda") Long renda);

}
