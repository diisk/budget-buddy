package br.dev.diisk.infra.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
import br.dev.diisk.domain.dtos.income.CreateIncomeCategoryDTO;
import br.dev.diisk.domain.dtos.income.IncomeDTO;
import br.dev.diisk.domain.dtos.income.UpdateIncomeDTO;
import br.dev.diisk.domain.cases.income.IAddIncomeCase;
import br.dev.diisk.domain.cases.income.IAddIncomeCategoryCase;
import br.dev.diisk.domain.cases.income.IUpdateIncomeCase;
import br.dev.diisk.domain.dtos.income.CreateIncomeDTO;
import br.dev.diisk.domain.dtos.income.IncomeCategoryDTO;
import br.dev.diisk.domain.entities.GenericResponse;
import br.dev.diisk.domain.entities.income.Income;
import br.dev.diisk.domain.entities.income.IncomeCategory;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.income.IIncomeCategoryRepository;
import br.dev.diisk.domain.repositories.income.IIncomeRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("incomes")
@PreAuthorize("hasAuthority('DEFAULT')")
@SecurityRequirement(name = "bearer-key")
public class IncomeController {

    @Autowired
    private IAddIncomeCase addIncomeCase;

    @Autowired
    private IUpdateIncomeCase updateIncomeCase;

    @Autowired
    private IAddIncomeCategoryCase addIncomeCategoryCase;

    @Autowired
    private IIncomeCategoryRepository incomeCategoryRepository;

    @Autowired
    private IIncomeRepository incomeRepository;

    @Autowired
    private IResponseService responseService;

    @PostMapping("categories")
    public ResponseEntity<?> addCategory(@RequestBody CreateIncomeCategoryDTO dto, @AuthenticationPrincipal User user) {
        IncomeCategory incomeCategory = addIncomeCategoryCase.execute(dto, user);
        return responseService.ok(new IncomeCategoryDTO(incomeCategory));
    }

    @PatchMapping("{id}")
    public ResponseEntity<GenericResponse<IncomeDTO>> updateIncome(@PathVariable Long id,
            @RequestBody UpdateIncomeDTO dto,
            @AuthenticationPrincipal User user) {
        Income income = updateIncomeCase.execute(id, dto, user);
        return responseService.ok(new IncomeDTO(income));
    }

    @PostMapping
    public ResponseEntity<GenericResponse<IncomeDTO>> addIncome(@RequestBody CreateIncomeDTO dto,
            @AuthenticationPrincipal User user) {
        Income income = addIncomeCase.execute(dto, user);
        return responseService.ok(new IncomeDTO(income));
    }

    @GetMapping("categories")
    public ResponseEntity<GenericResponse<List<IncomeCategoryDTO>>> getCategories(@AuthenticationPrincipal User user) {
        Set<IncomeCategory> categories = incomeCategoryRepository.findAllByUserId(user.getId());
        return responseService.ok(categories.stream().map(IncomeCategoryDTO::new).collect(Collectors.toList()));
    }

    @GetMapping
    public ResponseEntity<GenericResponse<List<IncomeDTO>>> getIncomes(
            @RequestParam LocalDateTime beginsAt,
            @RequestParam LocalDateTime endsAt,
            @AuthenticationPrincipal User user) {
        Set<Income> incomes = incomeRepository.findAllByUserIdAndPeriod(user.getId(),
                beginsAt, endsAt);
        return responseService.ok(incomes.stream().map(IncomeDTO::new).toList());
    }

}
