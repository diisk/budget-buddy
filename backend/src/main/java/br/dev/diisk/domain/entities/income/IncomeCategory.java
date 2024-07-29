package br.dev.diisk.domain.entities.income;

import br.dev.diisk.domain.entities.FinanceCategory;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class IncomeCategory extends FinanceCategory {
    
}
