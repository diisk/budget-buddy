package br.dev.diisk.infra.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.dev.diisk.application.interfaces.IResponseService;
import br.dev.diisk.application.interfaces.transaction.IAddTransactionCase;
import br.dev.diisk.application.interfaces.transaction.IListTransactionByTypeCase;
import br.dev.diisk.application.interfaces.transaction.ISetTransactionActiveCase;
import br.dev.diisk.application.interfaces.transaction.IUpdateTransactionCase;
import br.dev.diisk.application.interfaces.transaction_category.IAddTransactionCategoryCase;
import br.dev.diisk.application.interfaces.transaction_category.IListTransactionCategoryCase;
import br.dev.diisk.application.interfaces.transaction_category.ISetTransactionCategoryActiveCase;
import br.dev.diisk.application.interfaces.transaction_category.IUpdateTransactionCategoryCase;
import br.dev.diisk.application.mappers.transaction.TransactionToTransactionResponseMapper;
import br.dev.diisk.application.dtos.response.SuccessResponse;
import br.dev.diisk.application.dtos.transaction.AddTransactionRequest;
import br.dev.diisk.application.dtos.transaction.TransactionResponse;
import br.dev.diisk.application.dtos.transaction.UpdateTransactionRequest;
import br.dev.diisk.application.dtos.transaction_category.AddTransactionCategoryRequest;
import br.dev.diisk.application.dtos.transaction_category.TransactionCategoryResponse;
import br.dev.diisk.application.dtos.transaction_category.UpdateTransactionCategoryRequest;
import br.dev.diisk.domain.entities.transaction.Transaction;
import br.dev.diisk.domain.entities.transaction.TransactionCategory;
import br.dev.diisk.domain.entities.user.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/transactions")
@PreAuthorize("hasAuthority('DEFAULT')")
@SecurityRequirement(name = "bearer-key")
@RequiredArgsConstructor
public class TransactionController {

    private final ModelMapper mapper;
    private final TransactionToTransactionResponseMapper transactionToResponseMapper;
    private final IAddTransactionCase addTransactionCase;
    private final IAddTransactionCategoryCase addTransactionCategoryCase;
    private final IResponseService responseService;
    private final IListTransactionCategoryCase listTransactionCategoryCase;
    private final IListTransactionByTypeCase listTransactionByTypeCase;
    private final IUpdateTransactionCase updateTransactionCase;
    private final ISetTransactionActiveCase setTransactionActiveCase;
    private final IUpdateTransactionCategoryCase updateTransactionCategoryCase;
    private final ISetTransactionCategoryActiveCase setTransactionCategoryActiveCase;

    @PostMapping("categories")
    public ResponseEntity<SuccessResponse<TransactionCategoryResponse>> addCategory(
            @RequestBody @Valid AddTransactionCategoryRequest dto, @AuthenticationPrincipal User user) {
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

    @PatchMapping("{id}")
    public ResponseEntity<SuccessResponse<TransactionResponse>> updateTransaction(
            @PathVariable Long id,
            @RequestBody @Valid UpdateTransactionRequest dto,
            @AuthenticationPrincipal User user) {
        Transaction transaction = updateTransactionCase.execute(id, dto, user);
        TransactionResponse response = transactionToResponseMapper.apply(transaction);
        return responseService.created(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<SuccessResponse<Object>> deleteTransaction(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        setTransactionActiveCase.execute(id, false, user);
        return responseService.ok();
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

    @PatchMapping("categories/{id}")
    public ResponseEntity<SuccessResponse<TransactionCategoryResponse>> updateCategory(
            @PathVariable Long id,
            @RequestBody @Valid UpdateTransactionCategoryRequest dto,
            @AuthenticationPrincipal User user) {
        TransactionCategory transactionCategory = updateTransactionCategoryCase.execute(id, dto, user);
        TransactionCategoryResponse response = mapper.map(transactionCategory, TransactionCategoryResponse.class);
        return responseService.ok(response);
    }

    @DeleteMapping("categories/{id}")
    public ResponseEntity<SuccessResponse<TransactionCategoryResponse>> updateCategory(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        setTransactionCategoryActiveCase.execute(id, false, user);
        return responseService.ok();
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
