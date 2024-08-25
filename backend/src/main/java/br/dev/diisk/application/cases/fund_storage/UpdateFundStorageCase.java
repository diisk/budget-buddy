package br.dev.diisk.application.cases.fund_storage;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import br.dev.diisk.application.dtos.fund_storage.UpdateFundStorageRequest;
import br.dev.diisk.application.interfaces.fund_storage.IGetFundStorageCase;
import br.dev.diisk.application.interfaces.fund_storage.IUpdateFundStorageCase;
import br.dev.diisk.domain.entities.FundStorage;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.IFundStorageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateFundStorageCase implements IUpdateFundStorageCase {

    private final IFundStorageRepository fundStorageRepository;
    private final ModelMapper mapper;
    private final IGetFundStorageCase getFundStorageCase;

    @Override
    @Transactional
    @CacheEvict(value = "funds-storages", key = "#owner.getId()")
    public FundStorage execute(Long id, UpdateFundStorageRequest request, User owner) {
        FundStorage fundStorage = getFundStorageCase.execute(id, owner);
        mapper.map(request, fundStorage);
        return fundStorageRepository.save(fundStorage);
    }

}
