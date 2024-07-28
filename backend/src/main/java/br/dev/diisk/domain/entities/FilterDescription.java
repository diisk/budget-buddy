package br.dev.diisk.domain.entities;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import br.dev.diisk.application.dtos.income.FilterDescriptionDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class FilterDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<RuleDescription> rules = new HashSet<>();

    public FilterDescription(FilterDescriptionDTO dto){
        this.rules = dto.getRules()
        .stream().map(RuleDescription::new).collect(Collectors.toSet());
    }

}
