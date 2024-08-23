package io.github.kenzor1979.mscards.application;

import io.github.kenzor1979.mscards.domain.ClienteCard;
import io.github.kenzor1979.mscards.infra.repository.ClienteCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteCardService {
    private final ClienteCardRepository repository;

    public List<ClienteCard> listCartoesByCpf(String cpf) {
        return repository.findByCpf(cpf);
    }
}
