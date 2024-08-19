package br.dev.diisk.application.cases.fund_storage;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.dev.diisk.application.dtos.fund_storage.UpdateFundStorageRequest;
import br.dev.diisk.application.exceptions.persistence.DbValueNotFoundException;
import br.dev.diisk.application.interfaces.fund_storage.IUpdateFundStorageCase;
import br.dev.diisk.domain.entities.FundStorage;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.IFundStorageRepository;

@Service
public class UpdateFundStorageCase implements IUpdateFundStorageCase {

    private final IFundStorageRepository fundStorageRepository;
    private final ModelMapper mapper;

    public UpdateFundStorageCase(IFundStorageRepository fundStorageRepository, ModelMapper mapper) {
        this.fundStorageRepository = fundStorageRepository;
        this.mapper = mapper;
    }

    @Override
    public FundStorage execute(Long id, UpdateFundStorageRequest request, User owner) {
        FundStorage fundStorage = fundStorageRepository.findByIdAndUserId(id, owner.getId());
        if (fundStorage == null)
            throw new DbValueNotFoundException(FundStorage.class, "id");

        mapper.map(request, fundStorage);
        return fundStorageRepository.save(fundStorage);
    }

}
