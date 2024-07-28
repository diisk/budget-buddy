package br.dev.diisk.infra.dtos.income;

import java.util.List;

import br.dev.diisk.application.dtos.income.FilterDescriptionDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class IncomeCategoryResponse {

    private Long id;
    private String name;
    private List<FilterDescriptionDTO> filters;

    // public ListCategoriesResponse(IncomeCategory entity){
    // this.id = entity.getId();
    // this.name = entity.getName();
    // this.filters =
    // entity.getFilters().stream().map(FilterDescriptionDTO::new).toList();
    // }
}
