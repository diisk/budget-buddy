package br.dev.diisk.application.dtos.income;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class IncomeCategoryResponse {

    private Long id;
    private String name;
    // private List<FilterDescriptionDTO> filters;
    
}
