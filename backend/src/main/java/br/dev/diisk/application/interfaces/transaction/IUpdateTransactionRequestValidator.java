package br.dev.diisk.application.interfaces.transaction;


import br.dev.diisk.application.dtos.transaction.UpdateTransactionRequest;
import br.dev.diisk.domain.entities.user.User;

public interface IUpdateTransactionRequestValidator {

    void validate(Long id, UpdateTransactionRequest dto, User user);

}
