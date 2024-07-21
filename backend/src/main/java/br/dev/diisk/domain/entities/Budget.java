package br.dev.diisk.domain.entities;

import java.math.BigDecimal;

import br.dev.diisk.domain.entities.expense.ExpenseCategory;
import br.dev.diisk.domain.entities.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@Getter
@AllArgsConstructor
public class Budget extends RastreableEntity{

    @OneToOne
    private ExpenseCategory category;

    @OneToOne
    private User user;
    
    private BigDecimal value;

}
