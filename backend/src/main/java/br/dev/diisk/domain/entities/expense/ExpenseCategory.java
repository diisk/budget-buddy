package br.dev.diisk.domain.entities.expense;

import java.math.BigDecimal;

import br.dev.diisk.domain.entities.FinanceCategory;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ExpenseCategory extends FinanceCategory {

    private BigDecimal budgetLimit;
}
