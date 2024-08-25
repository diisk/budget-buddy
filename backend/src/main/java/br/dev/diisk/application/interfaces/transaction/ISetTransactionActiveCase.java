package br.dev.diisk.application.interfaces.transaction;

import br.dev.diisk.domain.entities.user.User;

public interface ISetTransactionActiveCase {

    void execute(Long id, Boolean active, User owner);

}
