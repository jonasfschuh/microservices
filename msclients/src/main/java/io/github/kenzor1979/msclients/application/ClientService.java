package io.github.kenzor1979.msclients.application;

import io.github.kenzor1979.msclients.domain.Client;
import io.github.kenzor1979.msclients.infra.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;

    @Transactional
    public Client save(Client client) {
        return repository.save(client);
    }

    public Optional<Client> getByCPF(String cpf) {
        return repository.findByCpf(cpf);
    }

}
