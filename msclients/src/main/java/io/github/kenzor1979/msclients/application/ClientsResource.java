package io.github.kenzor1979.msclients.application;

import io.github.kenzor1979.msclients.application.presentation.ClientSaveRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("clients")
@RequiredArgsConstructor
@Slf4j
public class ClientsResource {

    private final ClientService service;

    @GetMapping
    public String status() {
        log.info("Obtendo o status do microservice de clientes");
        return "ok";
    }

    @PostMapping
    public ResponseEntity save(@RequestBody ClientSaveRequest request) {
        var client = request.toModel();
        System.out.println("linha 27 - " + client.getNome());
        service.save(client);
        URI headerLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .query("cpf={cpf}")
                .buildAndExpand(client.getCpf())
                .toUri();
        return ResponseEntity.created(headerLocation).build();
    }
    // http://localhost:PORT/clientes?cpf=01234567890

    @GetMapping(params = "cpf")
    public ResponseEntity clientData(@RequestParam("cpf") String cpf) {
        System.out.println("teste");
        var client = service.getByCPF(cpf);
        if (client.isEmpty()) {
            System.out.println("cliente não encontrado");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(client);
    }
}
