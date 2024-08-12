package br.dev.diisk.domain.repositories.notification;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.dev.diisk.domain.entities.notification.Notification;

public interface INotificationRepository extends JpaRepository<Notification, Long> {

    @Query("""
            SELECT n FROM Notification n
            WHERE (
                n.user.id = :userId
            ) ORDER BY n.createdAt DESC
            """)
    List<Notification> findAllByUserId(Long userId);

}
