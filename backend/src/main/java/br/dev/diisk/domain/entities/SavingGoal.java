package br.dev.diisk.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.dev.diisk.domain.entities.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "saving_goals")
public class SavingGoal extends RastreableEntity{
    
    private String goalName;
    private BigDecimal targetValue;
    private BigDecimal currentValue;
    private LocalDateTime endDate;

    @ManyToOne(optional = false)
    private User user;

}
