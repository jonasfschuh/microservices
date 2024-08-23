package io.github.kenzor1979.msclients.application.presentation;

import io.github.kenzor1979.msclients.domain.Client;
import lombok.Data;

@Data
public class ClientSaveRequest {
    private String cpf;
    private String nome;
    private Integer idade;

    public Client toModel() {
        return new Client(cpf, nome, idade);
    }
}



