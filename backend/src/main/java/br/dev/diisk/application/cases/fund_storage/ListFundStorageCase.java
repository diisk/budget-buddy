package br.dev.diisk.application.cases.fund_storage;

import java.util.Set;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.dev.diisk.application.interfaces.fund_storage.IListFundStorageCase;
import br.dev.diisk.domain.entities.FundStorage;
import br.dev.diisk.domain.repositories.IFundStorageRepository;

@Service
public class ListFundStorageCase implements IListFundStorageCase {

    private IFundStorageRepository fundStorageRepository;

    public ListFundStorageCase(IFundStorageRepository fundStorageRepository) {
        this.fundStorageRepository = fundStorageRepository;
    }

    @Override
    @Cacheable("funds-storages")
    public Set<FundStorage> execute(Long userId) {
        return fundStorageRepository.findAllByUserId(userId);
    }

}
