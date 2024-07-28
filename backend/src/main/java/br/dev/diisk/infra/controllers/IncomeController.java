package br.dev.diisk.infra.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
import br.dev.diisk.application.mappers.income.IncomeCategoryToResponseMapper;
import br.dev.diisk.application.dtos.income.CreateCategoryRequest;
import br.dev.diisk.application.dtos.income.CreateIncomeRequest;
import br.dev.diisk.application.dtos.income.UpdateIncomeRequest;
import br.dev.diisk.domain.cases.income.ICreateIncomeCase;
import br.dev.diisk.domain.cases.income.ICreateCategoryCase;
import br.dev.diisk.domain.cases.income.IUpdateIncomeCase;
import br.dev.diisk.infra.dtos.income.CreateIncomeResponse;
import br.dev.diisk.domain.entities.GenericResponse;
import br.dev.diisk.domain.entities.income.Income;
import br.dev.diisk.domain.entities.income.IncomeCategory;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.income.IncomeCategoryRepository;
import br.dev.diisk.domain.repositories.income.IncomeRepository;
import br.dev.diisk.infra.dtos.income.IncomeCategoryResponse;
import br.dev.diisk.infra.dtos.income.ListIncomesResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("incomes")
@PreAuthorize("hasAuthority('DEFAULT')")
@SecurityRequirement(name = "bearer-key")
public class IncomeController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private IncomeCategoryToResponseMapper incomeCategoryToResponseMapper;

    @Autowired
    private ICreateIncomeCase createIncomeCase;

    @Autowired
    private IUpdateIncomeCase updateIncomeCase;

    @Autowired
    private ICreateCategoryCase createCategoryCase;

    @Autowired
    private IncomeCategoryRepository incomeCategoryRepository;

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private IResponseService responseService;

    @PostMapping("categories")
    public ResponseEntity<GenericResponse<IncomeCategoryResponse>> createCategory(
            @RequestBody CreateCategoryRequest dto, @AuthenticationPrincipal User user) {
        IncomeCategory incomeCategory = createCategoryCase.execute(dto, user);
        IncomeCategoryResponse response = incomeCategoryToResponseMapper.apply(incomeCategory);
        return responseService.ok(response);
    }

    @PatchMapping("{id}")
    public ResponseEntity<GenericResponse<ListIncomesResponse>> updateIncome(@PathVariable Long id,
            @RequestBody UpdateIncomeRequest dto,
            @AuthenticationPrincipal User user) {
        Income income = updateIncomeCase.execute(id, dto, user);
        return responseService.ok(new ListIncomesResponse(income));
    }

    @PostMapping
    public ResponseEntity<GenericResponse<CreateIncomeResponse>> createIncome(@RequestBody CreateIncomeRequest dto,
            @AuthenticationPrincipal User user) {
        Income income = createIncomeCase.execute(dto, user);
        return responseService.ok(new CreateIncomeResponse(income));
    }

    @GetMapping("categories")
    public ResponseEntity<GenericResponse<List<IncomeCategoryResponse>>> listCategories(
            @AuthenticationPrincipal User user) {
        Set<IncomeCategory> categories = incomeCategoryRepository.findAllByUserId(user.getId());
        List<IncomeCategoryResponse> response = incomeCategoryToResponseMapper.mapList(categories);
        return responseService.ok(response);
    }

    @GetMapping
    public ResponseEntity<GenericResponse<List<ListIncomesResponse>>> listIncomes(
            @RequestParam LocalDateTime beginsAt,
            @RequestParam LocalDateTime endsAt,
            @AuthenticationPrincipal User user) {
        Set<Income> incomes = incomeRepository.findAllByUserIdAndPeriod(user.getId(),
                beginsAt, endsAt);
        return responseService.ok(incomes.stream().map(ListIncomesResponse::new).toList());
    }

}
