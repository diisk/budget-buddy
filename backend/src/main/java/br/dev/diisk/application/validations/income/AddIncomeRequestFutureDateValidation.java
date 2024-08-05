package br.dev.diisk.application.validations.income;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import br.dev.diisk.application.dtos.income.AddIncomeRequest;
import br.dev.diisk.application.exceptions.FutureDateException;
import br.dev.diisk.application.interfaces.income.IAddIncomeRequestValidator;
import br.dev.diisk.domain.entities.user.User;

@Component
public class AddIncomeRequestFutureDateValidation implements IAddIncomeRequestValidator {

    @Override
    public void validate(List<AddIncomeRequest> dtos, User user) {
        LocalDateTime now = LocalDateTime.now();
        for(AddIncomeRequest dto : dtos){
            if(dto.getDate().isAfter(now))
            throw new FutureDateException("date");
        }
    }

}
