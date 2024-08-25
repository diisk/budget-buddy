package br.dev.diisk.application.interfaces.fund_storage;


import br.dev.diisk.domain.entities.FundStorage;
import br.dev.diisk.domain.entities.user.User;

public interface IGetFundStorageCase {

    FundStorage execute(Long id, User owner);

}
