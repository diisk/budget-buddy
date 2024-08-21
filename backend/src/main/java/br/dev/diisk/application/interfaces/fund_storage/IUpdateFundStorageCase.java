package br.dev.diisk.application.interfaces.fund_storage;

import br.dev.diisk.application.dtos.fund_storage.UpdateFundStorageDTO;
import br.dev.diisk.domain.entities.FundStorage;
import br.dev.diisk.domain.entities.user.User;

public interface IUpdateFundStorageCase {

    FundStorage execute(Long id, UpdateFundStorageDTO request, User owner);

}
