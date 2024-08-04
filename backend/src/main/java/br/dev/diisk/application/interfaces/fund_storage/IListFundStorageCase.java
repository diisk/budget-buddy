package br.dev.diisk.application.interfaces.fund_storage;

import java.util.Set;

import br.dev.diisk.domain.entities.FundStorage;

public interface IListFundStorageCase {

    Set<FundStorage> execute(Long userId);

}
