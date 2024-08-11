package br.dev.diisk.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.dev.diisk.domain.entities.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "MonthlyHistory")
public class MonthlyHistory extends RastreableEntity{

    @Column(nullable = false)
    private LocalDateTime referenceDate;

    @Column(nullable = false)
    private BigDecimal totalIncomes;

    @Column(nullable = false)
    private BigDecimal totalExpenses;

    @Column(nullable = false)
    private BigDecimal netBalance;

    @ManyToOne(optional = false)
    private User user;

}
