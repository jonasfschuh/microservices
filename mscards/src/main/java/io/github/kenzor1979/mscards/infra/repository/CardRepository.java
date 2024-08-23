package io.github.kenzor1979.mscards.infra.repository;

import io.github.kenzor1979.mscards.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByRendaLessThanEqual(Object rendaBigDecimal);
}
