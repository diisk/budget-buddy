package br.dev.diisk.application.interfaces.transaction;

import br.dev.diisk.domain.entities.transaction.Transaction;
import br.dev.diisk.domain.entities.user.User;

public interface IGetTransactionCase {

    Transaction execute(Long id, User owner);

}
