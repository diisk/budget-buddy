package br.dev.diisk.domain.repositories.notification;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.dev.diisk.domain.entities.notification.BudgetNotification;

public interface IBudgetNotificationRepository extends JpaRepository<BudgetNotification, Long> {

    @Query("""
            SELECT n FROM BudgetNotification n
            JOIN FETCH n.category c
            WHERE (
                n.user.id = :userId
                AND n.createdAt <= :referenceDate
                AND n.createdAt = (
                    SELECT MAX(n2.createdAt) FROM BudgetNotification n2
                    WHERE (
                        n2.user.id = :userId
                        AND n2.createdAt <= :referenceDate
                        AND c.id = n2.category.id
                    )
                )
            )
            """)
    Set<BudgetNotification> findLastNotifications(Long userId, LocalDateTime referenceDate);

}
