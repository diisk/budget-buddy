package br.dev.diisk.domain.entities.income;

import br.dev.diisk.domain.entities.FinanceCategory;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "incomes_categories")
public class IncomeCategory extends FinanceCategory {
    
}
