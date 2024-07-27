package br.dev.diisk.application.interfaces.income;


import br.dev.diisk.domain.dtos.income.CreateIncomeCategoryDTO;
import br.dev.diisk.domain.entities.user.User;

public interface CreateIncomeCategoryValidator {

    void validate(CreateIncomeCategoryDTO dto, User user);

}
