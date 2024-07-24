package br.dev.diisk.domain.dtos.income;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class FilterDescriptionDTO {
    private List<RuleDescriptionDTO> rules;
}
