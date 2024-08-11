package br.dev.diisk.application.cases.income_category;

import java.util.Set;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.dev.diisk.application.interfaces.income_category.IListIncomesCategoriesCase;
import br.dev.diisk.domain.entities.income.IncomeCategory;
import br.dev.diisk.domain.repositories.income.IIncomeCategoryRepository;

@Service
public class ListIncomesCategoriesCase implements IListIncomesCategoriesCase{

    private IIncomeCategoryRepository incomeCategoryRepository;

    public ListIncomesCategoriesCase(IIncomeCategoryRepository incomeCategoryRepository) {
        this.incomeCategoryRepository = incomeCategoryRepository;
    }

    @Override
    @Cacheable(value = "incomes-categories", key = "#userId")
    public Set<IncomeCategory> execute(Long userId) {
        return incomeCategoryRepository.findAllByUserId(userId);
    }

}
