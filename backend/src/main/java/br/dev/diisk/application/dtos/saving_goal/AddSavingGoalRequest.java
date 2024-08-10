package br.dev.diisk.application.dtos.saving_goal;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.dev.diisk.domain.GlobalMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AddSavingGoalRequest {

    @NotBlank(message = GlobalMessages.BLANK_OR_NULL_FIELD)
    private String goalName;

    @Positive(message = GlobalMessages.POSITIVE_VALUE)
    private BigDecimal targetValue;

    @NotNull(message = GlobalMessages.BLANK_OR_NULL_FIELD)
    private LocalDateTime endDate;
}
