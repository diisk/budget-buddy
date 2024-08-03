package br.dev.diisk.application.cases;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import br.dev.diisk.application.dtos.CreateBalanceResourceRequest;
import br.dev.diisk.application.interfaces.IAddBalanceResourceCase;
import br.dev.diisk.domain.entities.BalanceResource;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.IBalanceResourceRepository;
import jakarta.transaction.Transactional;

@Service
public class AddBalanceResourceCase implements IAddBalanceResourceCase {

    private IBalanceResourceRepository balanceResourceRepository;

    public AddBalanceResourceCase(IBalanceResourceRepository balanceResourceRepository) {
        this.balanceResourceRepository = balanceResourceRepository;
    }

    @Transactional
    @Override
    public BalanceResource execute(CreateBalanceResourceRequest request, User owner) {
        BalanceResource resource = new BalanceResource();
        resource.setBalance(new BigDecimal(0));
        resource.setCreditCard(request.getCreditCard());
        resource.setName(request.getName());
        resource.setUser(owner);
        return balanceResourceRepository.save(resource);
    }

}
