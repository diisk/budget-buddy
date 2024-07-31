package br.dev.diisk.application.cases.income;

import java.util.Set;
import org.springframework.stereotype.Service;
import br.dev.diisk.application.interfaces.income.IListIncomesCategoriesCase;
import br.dev.diisk.domain.entities.income.IncomeCategory;
import br.dev.diisk.domain.repositories.income.IIncomeCategoryRepository;

@Service
public class ListIncomesCategoriesCase implements IListIncomesCategoriesCase{

    private IIncomeCategoryRepository incomeCategoryRepository;

    public ListIncomesCategoriesCase(IIncomeCategoryRepository incomeCategoryRepository) {
        this.incomeCategoryRepository = incomeCategoryRepository;
    }

    @Override
    public Set<IncomeCategory> execute(Long userId) {
        return incomeCategoryRepository.findAllByUserId(userId);
    }

}
