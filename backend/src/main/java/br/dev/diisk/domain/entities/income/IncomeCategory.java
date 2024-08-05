package br.dev.diisk.domain.entities.income;

import br.dev.diisk.domain.entities.TransactionCategory;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "incomes_categories")
public class IncomeCategory extends TransactionCategory {
    
}
