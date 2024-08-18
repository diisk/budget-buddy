package br.dev.diisk.application.dtos.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.dev.diisk.domain.GlobalMessages;
import br.dev.diisk.domain.entities.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
public class AddTransactionRequest {

    @JsonIgnore
    @Setter
    private User user;

    @NotBlank(message = GlobalMessages.BLANK_OR_NULL_FIELD)
    private String description;

    @NotBlank(message = GlobalMessages.BLANK_OR_NULL_FIELD)
    private String type;

    @NotNull(message = GlobalMessages.BLANK_OR_NULL_FIELD)
    private Long categoryId;

    @NotNull(message = GlobalMessages.BLANK_OR_NULL_FIELD)
    private Long fundStorageId;

    @Positive(message = GlobalMessages.POSITIVE_VALUE)
    @NotNull(message = GlobalMessages.BLANK_OR_NULL_FIELD)
    private BigDecimal value;
    @NotNull(message = GlobalMessages.BLANK_OR_NULL_FIELD)
    private LocalDateTime date;

}
