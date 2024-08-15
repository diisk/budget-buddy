package br.dev.diisk.application.interfaces.transaction;

import br.dev.diisk.application.dtos.transaction.AddTransactionRequest;
import br.dev.diisk.domain.entities.transaction.Transaction;
import br.dev.diisk.domain.entities.user.User;

public interface IAddTransactionCase {

    Transaction execute(AddTransactionRequest dto, User owner);

}
