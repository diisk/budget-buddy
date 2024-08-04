package br.dev.diisk.application.cases.fund_storage;

import org.springframework.stereotype.Service;

import br.dev.diisk.application.interfaces.fund_storage.IIsvalidFundStorageCase;
import br.dev.diisk.domain.repositories.IFundStorageRepository;

@Service
public class IsValidFundStorageCase implements IIsvalidFundStorageCase {

    private IFundStorageRepository fundStorageRepository;

    @Override
    public Boolean execute(Long id, Long userId) {
        return fundStorageRepository.isValidFundStorage(id, userId);
    }

    

}
