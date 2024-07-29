package br.dev.diisk.domain.entities.expense;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import br.dev.diisk.domain.entities.FilterDescription;
import br.dev.diisk.domain.entities.FinanceCategory;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ExpenseCategory extends FinanceCategory {

    private BigDecimal budgetLimit;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<FilterDescription> filters = new HashSet<>();

}
