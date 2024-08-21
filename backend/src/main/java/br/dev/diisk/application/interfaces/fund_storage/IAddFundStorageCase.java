package br.dev.diisk.application.interfaces.fund_storage;

import br.dev.diisk.application.dtos.fund_storage.AddFundStorageDTO;
import br.dev.diisk.domain.entities.FundStorage;
import br.dev.diisk.domain.entities.user.User;

public interface IAddFundStorageCase {

    FundStorage execute(AddFundStorageDTO request, User owner);

}
