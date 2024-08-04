package br.dev.diisk.domain.repositories.saving_goal;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.dev.diisk.domain.entities.SavingGoal;

public interface ISavingGoalRepository extends JpaRepository<SavingGoal, Long> {

    @Query("""
            SELECT sg FROM SavingGoal sg
            WHERE (
                sg.user.id = :userId
                AND sg.endDate > :referenceDate
            )
            """)
    Set<SavingGoal> findActivesByUser(Long userId, LocalDateTime referenceDate);

}
