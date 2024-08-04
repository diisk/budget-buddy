package br.dev.diisk.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.dev.diisk.domain.entities.user.User;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
public abstract class FinanceEntry extends RastreableEntity{
    
    private BigDecimal value;
    private String description;
    private LocalDateTime date;
    
    @ManyToOne
    private FundStorage resource;

    @ManyToOne
    private User user;
}
