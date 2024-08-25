package br.dev.diisk.domain.entities.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.dev.diisk.domain.entities.FundStorage;
import br.dev.diisk.domain.entities.RastreableEntity;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.interfaces.IEntityWithUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
public class Transaction extends RastreableEntity  implements IEntityWithUser{

    @ManyToOne(optional = false)
    private TransactionCategory category;

    @Column(nullable = false)
    private BigDecimal value;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime date;

    @ManyToOne(optional = false)
    private FundStorage fundStorage;

    @ManyToOne(optional = false)
    private User user;

}
