package br.dev.diisk.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.dev.diisk.domain.entities.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
public abstract class Transaction extends RastreableEntity{
    
    @Column(nullable = false)
    private BigDecimal value;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime date;
    
    @ManyToOne(optional = false)
    private FundStorage storage;

    @ManyToOne(optional = false)
    private User user;
}
