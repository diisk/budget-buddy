package br.dev.diisk.domain.entities.income;

import br.dev.diisk.domain.entities.Transaction;
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
@Table(name = "incomes")
public class Income extends Transaction{

    @ManyToOne(optional = false)
    private IncomeCategory category;

}
