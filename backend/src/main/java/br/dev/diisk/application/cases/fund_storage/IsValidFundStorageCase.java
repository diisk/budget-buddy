package br.dev.diisk.application.cases.fund_storage;

import org.springframework.stereotype.Service;

import br.dev.diisk.application.interfaces.fund_storage.IIsValidFundStorageCase;
import br.dev.diisk.domain.repositories.IFundStorageRepository;

@Service
public class IsValidFundStorageCase implements IIsValidFundStorageCase {

    private final IFundStorageRepository fundStorageRepository;

    public IsValidFundStorageCase(IFundStorageRepository fundStorageRepository) {
        this.fundStorageRepository = fundStorageRepository;
    }

    @Override
    public Boolean execute(Long id, Long userId) {
        return fundStorageRepository.isValidFundStorage(id, userId);
    }

    

}
