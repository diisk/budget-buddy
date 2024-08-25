package br.dev.diisk.application.interfaces.fund_storage;

import br.dev.diisk.domain.entities.user.User;

public interface ISetFundStorageActiveCase {

    void execute(Long id, Boolean active, User user);

}
