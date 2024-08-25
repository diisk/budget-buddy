package br.dev.diisk.domain.entities;

import java.math.BigDecimal;

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
@Getter
@Setter
@NoArgsConstructor
@Table(name = "funds_storages")
public class FundStorage extends RastreableEntity implements IEntityWithUser {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private Boolean creditCard;

    @ManyToOne(optional = false)
    private User user;

}
