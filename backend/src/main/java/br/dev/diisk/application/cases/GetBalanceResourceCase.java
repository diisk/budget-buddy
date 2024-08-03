package br.dev.diisk.application.cases;

import org.springframework.stereotype.Service;

import br.dev.diisk.application.interfaces.IGetBalanceResourceCase;
import br.dev.diisk.domain.entities.BalanceResource;
import br.dev.diisk.domain.repositories.IBalanceResourceRepository;

@Service
public class GetBalanceResourceCase implements IGetBalanceResourceCase {

    private IBalanceResourceRepository balanceResourceRepository;

    public GetBalanceResourceCase(IBalanceResourceRepository balanceResourceRepository) {
        this.balanceResourceRepository = balanceResourceRepository;
    }
    
    @Override
    public BalanceResource execute(String name, Long userId) {
        return balanceResourceRepository.findByNameAndUserId(name, userId);
    }

}
