package br.dev.diisk.domain.entities.expense;

import br.dev.diisk.domain.entities.FinanceEntry;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "expenses")
@Getter
@Setter
@NoArgsConstructor
public class Expense extends FinanceEntry{
    
    @ManyToOne(optional = false)
    private ExpenseCategory category;

}
