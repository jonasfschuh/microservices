package io.github.kenzor1979.mscreditassessor.infra.clients;

import io.github.kenzor1979.mscreditassessor.domain.model.CustomerData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@FeignClient(value = "http://host.docker.internal:8080", path = "/clients")
@FeignClient(value = "msclients", path = "/clients")
public interface CustomerResourceClient {

    @GetMapping(params = "cpf")
    ResponseEntity<CustomerData> clientData(@RequestParam("cpf") String cpf);

}
