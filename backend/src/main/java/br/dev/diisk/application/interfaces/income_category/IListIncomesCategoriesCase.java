package br.dev.diisk.application.interfaces.income_category;

import java.util.Set;

import br.dev.diisk.domain.entities.income.IncomeCategory;

public interface IListIncomesCategoriesCase {
    
    Set<IncomeCategory> execute(Long userId);
    
}
