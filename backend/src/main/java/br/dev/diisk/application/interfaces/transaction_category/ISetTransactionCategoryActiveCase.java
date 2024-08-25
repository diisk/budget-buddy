package br.dev.diisk.application.interfaces.transaction_category;

import br.dev.diisk.domain.entities.user.User;

public interface ISetTransactionCategoryActiveCase {

    void execute(Long id, Boolean active, User user);

}
