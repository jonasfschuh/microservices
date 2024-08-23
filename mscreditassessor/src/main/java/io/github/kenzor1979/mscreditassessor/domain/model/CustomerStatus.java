package io.github.kenzor1979.mscreditassessor.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerStatus {
    private CustomerData customer;
    private List<CustomerCards> cards;
}
