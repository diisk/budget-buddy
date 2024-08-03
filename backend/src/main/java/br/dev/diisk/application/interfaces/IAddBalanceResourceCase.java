package br.dev.diisk.application.interfaces;

import br.dev.diisk.application.dtos.CreateBalanceResourceRequest;
import br.dev.diisk.domain.entities.BalanceResource;
import br.dev.diisk.domain.entities.user.User;

public interface IAddBalanceResourceCase {

    BalanceResource execute(CreateBalanceResourceRequest request, User owner);

}
