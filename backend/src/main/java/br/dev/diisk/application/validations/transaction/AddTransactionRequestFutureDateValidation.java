package br.dev.diisk.application.validations.transaction;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import br.dev.diisk.application.dtos.transaction.AddTransactionRequest;
import br.dev.diisk.application.exceptions.date.FutureDateException;
import br.dev.diisk.application.interfaces.transaction.IAddTransactionRequestValidator;
import br.dev.diisk.domain.entities.user.User;

@Component
public class AddTransactionRequestFutureDateValidation implements IAddTransactionRequestValidator {

    @Override
    public void validate(List<AddTransactionRequest> dtos, User user) {
        LocalDateTime now = LocalDateTime.now();
        for(AddTransactionRequest dto : dtos){
            if(dto.getDate().isAfter(now))
            throw new FutureDateException("date");
        }
    }

}
