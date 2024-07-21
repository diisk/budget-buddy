package br.dev.diisk.domain.entities.expense;

import br.dev.diisk.domain.entities.FinanceEntry;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Expense extends FinanceEntry{
    
    @ManyToOne
    private ExpenseCategory category;

}
