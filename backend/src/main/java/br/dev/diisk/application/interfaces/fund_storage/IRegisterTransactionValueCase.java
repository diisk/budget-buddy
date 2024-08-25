package br.dev.diisk.application.interfaces.fund_storage;


import br.dev.diisk.application.dtos.fund_storage.TransactionValueDTO;
import br.dev.diisk.domain.entities.FundStorage;
import br.dev.diisk.domain.entities.user.User;

public interface IRegisterTransactionValueCase {

    FundStorage execute(Long fundStorageId, TransactionValueDTO dto, User owner);

}
