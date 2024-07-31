package br.dev.diisk.infra.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.dev.diisk.application.interfaces.IResponseService;
import br.dev.diisk.application.interfaces.income.ICreateIncomeCategoryCase;
import br.dev.diisk.application.interfaces.income.ICreateIncomeCase;
import br.dev.diisk.application.interfaces.income.IListIncomesCategoriesCase;
import br.dev.diisk.application.interfaces.income.IListIncomesCase;
import br.dev.diisk.application.interfaces.income.IUpdateIncomeCase;
import br.dev.diisk.application.mappers.income.IncomeCategoryToResponseMapper;
import br.dev.diisk.application.mappers.income.IncomeToResponseMapper;
import br.dev.diisk.application.dtos.GenericResponse;
import br.dev.diisk.application.dtos.income.CreateIncomeCategoryRequest;
import br.dev.diisk.application.dtos.income.CreateIncomeRequest;
import br.dev.diisk.application.dtos.income.IncomeCategoryResponse;
import br.dev.diisk.application.dtos.income.IncomeResponse;
import br.dev.diisk.application.dtos.income.UpdateIncomeRequest;
import br.dev.diisk.domain.entities.income.Income;
import br.dev.diisk.domain.entities.income.IncomeCategory;
import br.dev.diisk.domain.entities.user.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("incomes")
@PreAuthorize("hasAuthority('DEFAULT')")
@SecurityRequirement(name = "bearer-key")
public class IncomeController {

    private IncomeCategoryToResponseMapper incomeCategoryToResponseMapper;
    private IncomeToResponseMapper incomeToResponseMapper;
    private ICreateIncomeCase createIncomeCase;
    private IUpdateIncomeCase updateIncomeCase;
    private ICreateIncomeCategoryCase createIncomeCategoryCase;
    private IResponseService responseService;
    private IListIncomesCategoriesCase listIncomesCategoriesCase;
    private IListIncomesCase listIncomesCase;

    public IncomeController(IncomeCategoryToResponseMapper incomeCategoryToResponseMapper,
            IncomeToResponseMapper incomeToResponseMapper, ICreateIncomeCase createIncomeCase,
            IUpdateIncomeCase updateIncomeCase, ICreateIncomeCategoryCase createCategoryCase,
            IResponseService responseService, IListIncomesCategoriesCase listCategoriesCase,
            IListIncomesCase listIncomesCase) {
        this.incomeCategoryToResponseMapper = incomeCategoryToResponseMapper;
        this.incomeToResponseMapper = incomeToResponseMapper;
        this.createIncomeCase = createIncomeCase;
        this.updateIncomeCase = updateIncomeCase;
        this.createIncomeCategoryCase = createCategoryCase;
        this.responseService = responseService;
        this.listIncomesCategoriesCase = listCategoriesCase;
        this.listIncomesCase = listIncomesCase;
    }

    @PostMapping("categories")
    public ResponseEntity<GenericResponse<IncomeCategoryResponse>> createCategory(
            @RequestBody CreateIncomeCategoryRequest dto, @AuthenticationPrincipal User user) {
        IncomeCategory incomeCategory = createIncomeCategoryCase.execute(dto, user);
        IncomeCategoryResponse response = incomeCategoryToResponseMapper.apply(incomeCategory);
        return responseService.ok(response);
    }

    @PatchMapping("{id}")
    public ResponseEntity<GenericResponse<IncomeResponse>> updateIncome(@PathVariable Long id,
            @RequestBody @Valid UpdateIncomeRequest dto,
            @AuthenticationPrincipal User user) {
        Income income = updateIncomeCase.execute(id, dto, user);
        IncomeResponse response = incomeToResponseMapper.apply(income);
        return responseService.ok(response);
    }

    @PostMapping
    public ResponseEntity<GenericResponse<IncomeResponse>> createIncome(@RequestBody @Valid CreateIncomeRequest dto,
            @AuthenticationPrincipal User user) {
        Income income = createIncomeCase.execute(dto, user);
        IncomeResponse response = incomeToResponseMapper.apply(income);
        return responseService.ok(response);
    }

    @GetMapping("categories")
    public ResponseEntity<GenericResponse<List<IncomeCategoryResponse>>> listCategories(
            @AuthenticationPrincipal User user) {
        Set<IncomeCategory> categories = listIncomesCategoriesCase.execute(user.getId());
        List<IncomeCategoryResponse> response = incomeCategoryToResponseMapper.mapList(categories);
        return responseService.ok(response);
    }

    @GetMapping
    public ResponseEntity<GenericResponse<List<IncomeResponse>>> listIncomes(
            @RequestParam LocalDateTime beginsAt,
            @RequestParam LocalDateTime endsAt,
            @AuthenticationPrincipal User user) {
        Set<Income> incomes = listIncomesCase.execute(user.getId(),
                beginsAt, endsAt);
        List<IncomeResponse> response = incomeToResponseMapper.mapList(incomes);
        return responseService.ok(response);
    }

}
