package io.github.kenzor1979.mscreditassessor.domain.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ApprovedCard {
    private String card;
    private String flag;
    private BigDecimal approvedLimit;
}
