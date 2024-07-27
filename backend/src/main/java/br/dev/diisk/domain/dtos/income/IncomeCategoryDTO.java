package br.dev.diisk.domain.dtos.income;

import java.util.List;

import br.dev.diisk.domain.entities.income.IncomeCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class IncomeCategoryDTO {
    private Long id;
    private String name;
    private List<FilterDescriptionDTO> filters;

    public IncomeCategoryDTO(IncomeCategory entity){
        this.id = entity.getId();
        this.name = entity.getName();
        this.filters = entity.getFilters().stream().map(FilterDescriptionDTO::new).toList();
    }
}
