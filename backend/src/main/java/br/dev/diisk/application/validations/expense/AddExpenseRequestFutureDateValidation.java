package br.dev.diisk.application.validations.expense;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import br.dev.diisk.application.dtos.expense.AddExpenseRequest;
import br.dev.diisk.application.exceptions.FutureDateException;
import br.dev.diisk.application.interfaces.expense.IAddExpenseRequestValidator;
import br.dev.diisk.domain.entities.user.User;

@Component
public class AddExpenseRequestFutureDateValidation implements IAddExpenseRequestValidator {

    @Override
    public void validate(List<AddExpenseRequest> dtos, User user) {
        LocalDateTime now = LocalDateTime.now();
        for(AddExpenseRequest dto : dtos){
            if(dto.getDate().isAfter(now))
            throw new FutureDateException("date");
        }
    }

}
