package br.dev.diisk.application.interfaces.income;

import java.util.Set;

import br.dev.diisk.domain.entities.income.IncomeCategory;

public interface IListIncomesCategoriesCase {
    
    Set<IncomeCategory> execute(Long userId);
    
}
