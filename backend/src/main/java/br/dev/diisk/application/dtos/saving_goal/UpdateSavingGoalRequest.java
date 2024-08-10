package br.dev.diisk.application.dtos.saving_goal;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.dev.diisk.domain.GlobalMessages;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UpdateSavingGoalRequest {

    @NotEmpty(message = GlobalMessages.EMPTY_FIELD)
    private String goalName;
    @Positive(message = GlobalMessages.POSITIVE_VALUE)
    private BigDecimal currentValue;
    private LocalDateTime endDate;
}
