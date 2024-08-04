package br.dev.diisk.application.interfaces.income;


import java.util.List;

import br.dev.diisk.application.dtos.income.AddIncomeRequest;
import br.dev.diisk.domain.entities.user.User;

public interface IAddIncomeRequestValidator {

    void validate(List<AddIncomeRequest> dtos, User user);

}
