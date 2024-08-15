package br.dev.diisk.application.interfaces.transaction;


import java.util.List;

import br.dev.diisk.application.dtos.transaction.AddTransactionRequest;
import br.dev.diisk.domain.entities.user.User;

public interface IAddTransactionRequestValidator {

    void validate(List<AddTransactionRequest> dtos, User user);

}
