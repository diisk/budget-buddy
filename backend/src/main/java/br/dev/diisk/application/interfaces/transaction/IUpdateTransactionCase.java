package br.dev.diisk.application.interfaces.transaction;

import br.dev.diisk.application.dtos.transaction.UpdateTransactionRequest;
import br.dev.diisk.domain.entities.transaction.Transaction;
import br.dev.diisk.domain.entities.user.User;

public interface IUpdateTransactionCase {

    Transaction execute(Long id, UpdateTransactionRequest dto, User owner);

}
