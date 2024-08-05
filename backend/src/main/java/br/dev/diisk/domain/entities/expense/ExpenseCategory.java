package br.dev.diisk.domain.entities.expense;

import java.math.BigDecimal;
import br.dev.diisk.domain.entities.TransactionCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "expenses_categories")
public class ExpenseCategory extends TransactionCategory {

    @Column(nullable = false)
    private BigDecimal budgetLimit;

    // @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    // private Set<FilterDescription> filters = new HashSet<>();

}
