package br.dev.diisk.application.dtos.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.dev.diisk.domain.GlobalMessages;
import br.dev.diisk.domain.entities.user.User;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
public class UpdateTransactionRequest {

    @JsonIgnore
    @Setter
    private User user;

    private String description;
    private Long categoryId;
    private Long fundStorageId;
    @Positive(message = GlobalMessages.POSITIVE_VALUE)
    private BigDecimal value;
    private LocalDateTime date;

}
