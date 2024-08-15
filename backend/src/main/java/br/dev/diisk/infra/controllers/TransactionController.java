package br.dev.diisk.infra.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.dev.diisk.application.interfaces.IResponseService;
import br.dev.diisk.application.interfaces.transaction.IAddTransactionCase;
import br.dev.diisk.application.interfaces.transaction.IListTransactionCase;
import br.dev.diisk.application.interfaces.transaction_category.IAddTransactionCategoryCase;
import br.dev.diisk.application.interfaces.transaction_category.IListTransactionCategoryCase;
import br.dev.diisk.application.mappers.transaction.TransactionToResponseMapper;
import br.dev.diisk.application.dtos.GenericResponse;
import br.dev.diisk.application.dtos.transaction.AddTransactionRequest;
import br.dev.diisk.application.dtos.transaction.TransactionResponse;
import br.dev.diisk.application.dtos.transaction_category.AddTransactionCategoryRequest;
import br.dev.diisk.application.dtos.transaction_category.TransactionCategoryResponse;
import br.dev.diisk.domain.entities.transaction.Transaction;
import br.dev.diisk.domain.entities.transaction.TransactionCategory;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.enums.TransactionTypeEnum;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("transactions")
@PreAuthorize("hasAuthority('DEFAULT')")
@SecurityRequirement(name = "bearer-key")
public class TransactionController {

    private ModelMapper mapper;
    private TransactionToResponseMapper transactionToResponseMapper;
    private IAddTransactionCase addTransactionCase;
    private IAddTransactionCategoryCase addTransactionCategoryCase;
    private IResponseService responseService;
    private IListTransactionCategoryCase listTransactionCategoryCase;
    private IListTransactionCase listTransactionCase;

    public TransactionController(ModelMapper mapper, TransactionToResponseMapper transactionToResponseMapper,
            IAddTransactionCase addTransactionCase, IAddTransactionCategoryCase addTransactionCategoryCase,
            IResponseService responseService, IListTransactionCategoryCase listTransactionCategoryCase,
            IListTransactionCase listTransactionCase) {
        this.mapper = mapper;
        this.transactionToResponseMapper = transactionToResponseMapper;
        this.addTransactionCase = addTransactionCase;
        this.addTransactionCategoryCase = addTransactionCategoryCase;
        this.responseService = responseService;
        this.listTransactionCategoryCase = listTransactionCategoryCase;
        this.listTransactionCase = listTransactionCase;
    }

    @PostMapping("categories")
    public ResponseEntity<GenericResponse<TransactionCategoryResponse>> addCategory(
            @Valid @RequestBody AddTransactionCategoryRequest dto, @AuthenticationPrincipal User user) {
        TransactionCategory transactionCategory = addTransactionCategoryCase.execute(dto, user);
        TransactionCategoryResponse response = mapper.map(transactionCategory, TransactionCategoryResponse.class);
        return responseService.ok(response);
    }

    @PostMapping
    public ResponseEntity<GenericResponse<TransactionResponse>> addTransaction(
            @RequestBody @Valid AddTransactionRequest dto,
            @AuthenticationPrincipal User user) {
        Transaction transaction = addTransactionCase.execute(dto, user);
        TransactionResponse response = transactionToResponseMapper.apply(transaction);
        return responseService.ok(response);
    }

    @GetMapping("categories")
    public ResponseEntity<GenericResponse<List<TransactionCategoryResponse>>> listCategory(
            @RequestParam TransactionTypeEnum type,
            @AuthenticationPrincipal User user) {
        Set<TransactionCategory> categories = listTransactionCategoryCase.execute(user.getId(), type);
        List<TransactionCategoryResponse> response = categories.stream()
                .map(tCat -> mapper.map(tCat, TransactionCategoryResponse.class)).toList();
        return responseService.ok(response);
    }

    @GetMapping
    public ResponseEntity<GenericResponse<List<TransactionResponse>>> listTransaction(
            @RequestParam(required = false) LocalDateTime beginsAt,
            @RequestParam(required = false) LocalDateTime endsAt,
            @RequestParam TransactionTypeEnum type,
            @AuthenticationPrincipal User user) {
        Set<Transaction> transactions = listTransactionCase.execute(user.getId(), type,
                beginsAt, endsAt);
        List<TransactionResponse> response = transactionToResponseMapper.mapList(transactions);
        return responseService.ok(response);
    }

}
