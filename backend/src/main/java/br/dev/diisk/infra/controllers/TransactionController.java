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
import br.dev.diisk.application.interfaces.transaction.IListTransactionByTypeCase;
import br.dev.diisk.application.interfaces.transaction_category.IAddTransactionCategoryCase;
import br.dev.diisk.application.interfaces.transaction_category.IListTransactionCategoryCase;
import br.dev.diisk.application.mappers.transaction.TransactionToTransactionResponseMapper;
import br.dev.diisk.application.dtos.response.SuccessResponse;
import br.dev.diisk.application.dtos.transaction.AddTransactionRequest;
import br.dev.diisk.application.dtos.transaction.TransactionResponse;
import br.dev.diisk.application.dtos.transaction_category.AddTransactionCategoryRequest;
import br.dev.diisk.application.dtos.transaction_category.TransactionCategoryResponse;
import br.dev.diisk.domain.entities.transaction.Transaction;
import br.dev.diisk.domain.entities.transaction.TransactionCategory;
import br.dev.diisk.domain.entities.user.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/transactions")
@PreAuthorize("hasAuthority('DEFAULT')")
@SecurityRequirement(name = "bearer-key")
public class TransactionController {

    private final ModelMapper mapper;
    private final TransactionToTransactionResponseMapper transactionToResponseMapper;
    private final IAddTransactionCase addTransactionCase;
    private final IAddTransactionCategoryCase addTransactionCategoryCase;
    private final IResponseService responseService;
    private final IListTransactionCategoryCase listTransactionCategoryCase;
    private final IListTransactionByTypeCase listTransactionByTypeCase;

    public TransactionController(ModelMapper mapper, TransactionToTransactionResponseMapper transactionToResponseMapper,
            IAddTransactionCase addTransactionCase, IAddTransactionCategoryCase addTransactionCategoryCase,
            IResponseService responseService, IListTransactionCategoryCase listTransactionCategoryCase,
            IListTransactionByTypeCase listTransactionByTypeCase) {
        this.mapper = mapper;
        this.transactionToResponseMapper = transactionToResponseMapper;
        this.addTransactionCase = addTransactionCase;
        this.addTransactionCategoryCase = addTransactionCategoryCase;
        this.responseService = responseService;
        this.listTransactionCategoryCase = listTransactionCategoryCase;
        this.listTransactionByTypeCase = listTransactionByTypeCase;
    }

    @PostMapping("categories")
    public ResponseEntity<SuccessResponse<TransactionCategoryResponse>> addCategory(
            @Valid @RequestBody AddTransactionCategoryRequest dto, @AuthenticationPrincipal User user) {
        TransactionCategory transactionCategory = addTransactionCategoryCase.execute(dto, user);
        TransactionCategoryResponse response = mapper.map(transactionCategory, TransactionCategoryResponse.class);
        return responseService.created(response);
    }

    @PostMapping
    public ResponseEntity<SuccessResponse<TransactionResponse>> addTransaction(
            @RequestBody @Valid AddTransactionRequest dto,
            @AuthenticationPrincipal User user) {
        Transaction transaction = addTransactionCase.execute(dto, user);
        TransactionResponse response = transactionToResponseMapper.apply(transaction);
        return responseService.created(response);
    }

    @GetMapping("categories")
    public ResponseEntity<SuccessResponse<List<TransactionCategoryResponse>>> listCategory(
            @RequestParam String type,
            @AuthenticationPrincipal User user) {
        Set<TransactionCategory> categories = listTransactionCategoryCase.execute(user.getId(), type);
        List<TransactionCategoryResponse> response = categories.stream()
                .map(tCat -> mapper.map(tCat, TransactionCategoryResponse.class)).toList();
        return responseService.ok(response);
    }

    @GetMapping
    public ResponseEntity<SuccessResponse<List<TransactionResponse>>> listTransaction(
            @RequestParam(required = false) LocalDateTime beginsAt,
            @RequestParam(required = false) LocalDateTime endsAt,
            @RequestParam String type,
            @AuthenticationPrincipal User user) {
        Set<Transaction> transactions = listTransactionByTypeCase.execute(user.getId(), type,
                beginsAt, endsAt);
        List<TransactionResponse> response = transactionToResponseMapper.mapList(transactions);
        return responseService.ok(response);
    }

}
