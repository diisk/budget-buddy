package br.dev.diisk.domain.entities.transaction;

import java.math.BigDecimal;

import br.dev.diisk.domain.entities.RastreableEntity;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.enums.TransactionTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "transactions_categories")
public class TransactionCategory extends RastreableEntity{

    @Enumerated(EnumType.STRING)
    private TransactionTypeEnum type;
    
    private BigDecimal budgetLimit;

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    private User user;

}
