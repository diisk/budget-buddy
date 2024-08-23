package br.dev.diisk.application.validations.transaction;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import br.dev.diisk.application.dtos.transaction.UpdateTransactionRequest;
import br.dev.diisk.application.exceptions.date.FutureDateException;
import br.dev.diisk.application.interfaces.transaction.IUpdateTransactionRequestValidator;
import br.dev.diisk.domain.entities.user.User;

@Component
public class UpdateTransactionRequestFutureDateValidation implements IUpdateTransactionRequestValidator {

    @Override
    public void validate(Long id, UpdateTransactionRequest dto, User user) {
        LocalDateTime now = LocalDateTime.now();
        if(dto.getDate().isAfter(now))
            throw new FutureDateException("date");
    }

}
