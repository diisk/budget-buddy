package br.dev.diisk.application.cases.fund_storage;

import org.springframework.stereotype.Service;

import br.dev.diisk.application.cases.rastreable_entity.FindByIdAndUserCase;
import br.dev.diisk.application.interfaces.fund_storage.IGetFundStorageCase;
import br.dev.diisk.domain.entities.FundStorage;
import br.dev.diisk.domain.repositories.IFundStorageRepository;

@Service
public class GetFundStorageCase extends FindByIdAndUserCase<FundStorage> implements IGetFundStorageCase {

    public GetFundStorageCase(IFundStorageRepository repository) {
        super(repository);
    }

}
