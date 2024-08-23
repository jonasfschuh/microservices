package io.github.kenzor1979.mscards.infra.repository;

import io.github.kenzor1979.mscards.domain.Card;
import io.github.kenzor1979.mscards.domain.ClienteCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteCardRepository extends JpaRepository<ClienteCard, Long> {
    List<ClienteCard> findByCpf(String cpf);
}
