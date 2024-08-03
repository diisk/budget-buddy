package br.dev.diisk.application.interfaces;

import br.dev.diisk.domain.entities.BalanceResource;

public interface IGetBalanceResourceCase {

    BalanceResource execute(String name, Long userId);

}
