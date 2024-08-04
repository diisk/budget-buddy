package br.dev.diisk.application.dtos.expense;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.dev.diisk.domain.GlobalMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AddExpenseRequest {
    @NotBlank(message = GlobalMessages.BLANK_OR_NULL_FIELD)
    private String description;

    @NotNull(message = GlobalMessages.BLANK_OR_NULL_FIELD)
    private Long categoryId;

    @NotNull(message = GlobalMessages.BLANK_OR_NULL_FIELD)
    private Long resourceId;

    @Positive(message = GlobalMessages.POSITIVE_VALUE)
    @NotNull(message = GlobalMessages.BLANK_OR_NULL_FIELD)
    private BigDecimal value;
    @NotNull(message = GlobalMessages.BLANK_OR_NULL_FIELD)
    private LocalDateTime date;

}
