package io.github.kenzor1979.mscreditassessor.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CustomerReviewReturn {
    private List<ApprovedCard> cards;
}
