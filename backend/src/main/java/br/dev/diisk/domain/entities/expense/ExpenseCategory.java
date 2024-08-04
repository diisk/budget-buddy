package br.dev.diisk.domain.entities.expense;

import java.math.BigDecimal;
import br.dev.diisk.domain.entities.FinanceCategory;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ExpenseCategory extends FinanceCategory {

    private BigDecimal budgetLimit;

    // @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    // private Set<FilterDescription> filters = new HashSet<>();

}
