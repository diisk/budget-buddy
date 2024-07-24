package br.dev.diisk.infra.controllers;

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
import org.springframework.web.bind.annotation.RestController;

import br.dev.diisk.application.interfaces.IResponseService;
import br.dev.diisk.domain.dtos.income.IncomeCategoryDTO;
import br.dev.diisk.domain.dtos.income.UpdateIncomeDTO;
import br.dev.diisk.domain.dtos.income.CreateIncomeDTO;
import br.dev.diisk.domain.entities.user.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("incomes")
@PreAuthorize("hasAuthority('DEFAULT')")
@SecurityRequirement(name = "bearer-key")
public class IncomeController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private IResponseService responseService;

    @PostMapping("categories")
    public ResponseEntity<?> addCategory(@RequestBody IncomeCategoryDTO dto, @AuthenticationPrincipal User user) {
        return responseService.ok();
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> updateIncome(@PathVariable Long id,@RequestBody UpdateIncomeDTO dto, @AuthenticationPrincipal User user) {
        return responseService.ok();
    }

    @PostMapping
    public ResponseEntity<?> addIncome(@RequestBody CreateIncomeDTO dto, @AuthenticationPrincipal User user) {
        return responseService.ok();
    }

    @GetMapping("categories")
    public ResponseEntity<?> getCategories(@AuthenticationPrincipal User user) {
        return responseService.ok();
    }

    @GetMapping
    public ResponseEntity<?> getIncomes(@AuthenticationPrincipal User user) {
        return responseService.ok();
    }

}
