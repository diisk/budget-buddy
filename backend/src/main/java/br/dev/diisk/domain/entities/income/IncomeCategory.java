package br.dev.diisk.domain.entities.income;

import java.util.Set;
import java.util.stream.Collectors;

import br.dev.diisk.application.dtos.income.CreateCategoryRequest;
import br.dev.diisk.domain.entities.FilterDescription;
import br.dev.diisk.domain.entities.FinanceCategory;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class IncomeCategory extends FinanceCategory {

    public IncomeCategory(CreateCategoryRequest dto) {
        setName(dto.getName());
        Set<FilterDescription> filters = dto.getFilters()
                .stream().map(FilterDescription::new).collect(Collectors.toSet());
        setFilters(filters);
    }
}
