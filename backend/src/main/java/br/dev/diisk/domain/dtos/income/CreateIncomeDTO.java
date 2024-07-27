package br.dev.diisk.domain.dtos.income;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.dev.diisk.domain.GlobalMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CreateIncomeDTO {
    @NotBlank(message = GlobalMessages.BLANK_FIELD)
    private String description;

    private Long categoryId;

    @NotBlank(message = GlobalMessages.BLANK_FIELD)
    @Positive(message = GlobalMessages.POSITIVE_VALUE)
    private BigDecimal value;
    @NotBlank(message = GlobalMessages.BLANK_FIELD)
    private LocalDateTime date;

}
