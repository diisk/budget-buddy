package br.dev.diisk.application.cases.fund_storage;

import java.math.BigDecimal;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.dev.diisk.application.cases.rastreable_entity.SetEntityActiveCase;
import br.dev.diisk.application.exceptions.domain.FundStorageMustBeEmptyException;
import br.dev.diisk.application.interfaces.fund_storage.IGetFundStorageCase;
import br.dev.diisk.application.interfaces.fund_storage.ISetFundStorageActiveCase;
import br.dev.diisk.domain.entities.FundStorage;
import br.dev.diisk.domain.entities.user.User;
import jakarta.transaction.Transactional;

@Service
public class SetFundStorageActiveCase extends SetEntityActiveCase<FundStorage> implements ISetFundStorageActiveCase {

    private final IGetFundStorageCase getFundStorageCase;

    public SetFundStorageActiveCase(JpaRepository<FundStorage, Long> repository,
            IGetFundStorageCase getFundStorageCase) {
        super(repository);
        this.getFundStorageCase = getFundStorageCase;
    }

    @Override
    @Transactional
    @CacheEvict(value = "funds-storages", key = "#owner.getId()")
    public void execute(Long id, Boolean active, User user) {
        FundStorage fundStorage = getFundStorageCase.execute(id, user);

        if (fundStorage.getBalance().compareTo(BigDecimal.ZERO) != 0)
            throw new FundStorageMustBeEmptyException(getClass(), "balance");

        super.execute(id, active, user);
    }

}
