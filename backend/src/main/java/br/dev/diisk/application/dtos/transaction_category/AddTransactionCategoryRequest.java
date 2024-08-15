package br.dev.diisk.application.dtos.transaction_category;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.dev.diisk.domain.GlobalMessages;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.enums.TransactionTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
public class AddTransactionCategoryRequest {

    @JsonIgnore
    @Setter
    private User user;

    @NotBlank(message = GlobalMessages.BLANK_OR_NULL_FIELD)
    private String name;

    @NotNull(message = GlobalMessages.BLANK_OR_NULL_FIELD)
    private TransactionTypeEnum type;

    @Positive(message = GlobalMessages.POSITIVE_VALUE)
    private BigDecimal budgetLimit;
}
