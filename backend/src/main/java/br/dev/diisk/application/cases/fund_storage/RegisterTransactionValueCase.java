package br.dev.diisk.application.cases.fund_storage;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import br.dev.diisk.application.dtos.fund_storage.TransactionValueDTO;
import br.dev.diisk.application.interfaces.fund_storage.IGetFundStorageCase;
import br.dev.diisk.application.interfaces.fund_storage.IRegisterTransactionValueCase;
import br.dev.diisk.domain.entities.FundStorage;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.IFundStorageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegisterTransactionValueCase implements IRegisterTransactionValueCase {

    private final IFundStorageRepository fundStorageRepository;
    private final IGetFundStorageCase getFundStorageCase;

    @Override
    @Transactional
    @CacheEvict(value = "funds-storages", key = "#owner.getId()")
    public FundStorage execute(Long id, TransactionValueDTO dto, User owner) {
        FundStorage fundStorage = getFundStorageCase.execute(id, owner);
        Boolean subtract;
        switch (dto.getType()) {
            case EXPENSE:
                subtract = !dto.getUndo();
                break;
            case INCOME:
                subtract = dto.getUndo();
                break;
            default:
                throw new NotImplementedException("Not implemented.");
        }

        if (subtract) {
            fundStorage.setBalance(fundStorage.getBalance().subtract(dto.getValue()));
        } else {
            fundStorage.setBalance(fundStorage.getBalance().add(dto.getValue()));
        }
        return fundStorageRepository.save(fundStorage);
    }

}
