package br.dev.diisk.application.interfaces.income;

import java.time.LocalDateTime;
import java.util.Set;

import br.dev.diisk.domain.entities.income.Income;

public interface IListIncomesCase {
    
    Set<Income> execute(Long userId, LocalDateTime beginsAt, LocalDateTime endsAt);
    
}
