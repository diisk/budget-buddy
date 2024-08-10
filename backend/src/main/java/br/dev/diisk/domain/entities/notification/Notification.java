package br.dev.diisk.domain.entities.notification;

import br.dev.diisk.domain.entities.RastreableEntity;
import br.dev.diisk.domain.entities.expense.ExpenseCategory;
import br.dev.diisk.domain.entities.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "notifications")
public class Notification extends RastreableEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private Boolean readed;

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private ExpenseCategory category;

}
