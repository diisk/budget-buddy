package br.dev.diisk.application.cases.fund_storage;

import java.math.BigDecimal;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import br.dev.diisk.application.dtos.fund_storage.AddFundStorageDTO;
import br.dev.diisk.application.interfaces.fund_storage.IAddFundStorageCase;
import br.dev.diisk.domain.entities.FundStorage;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.IFundStorageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddFundStorageCase implements IAddFundStorageCase {

    private final IFundStorageRepository fundStorageRepository;

    @Transactional
    @Override
    @CacheEvict(value = "funds-storages", key = "#owner.getId()")
    public FundStorage execute(AddFundStorageDTO request, User owner) {
        FundStorage storage = new FundStorage();
        storage.setCreditCard(request.getCreditCard());
        storage.setBalance(BigDecimal.ZERO);
        storage.setName(request.getName());
        storage.setUser(owner);
        return fundStorageRepository.save(storage);
    }

}
