package io.github.kenzor1979.msclients.infra.repository;

import io.github.kenzor1979.msclients.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByCpf(String cpf);
}


