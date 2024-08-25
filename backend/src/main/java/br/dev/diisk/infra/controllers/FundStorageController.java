package br.dev.diisk.infra.controllers;

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
import org.springframework.web.bind.annotation.RestController;
import br.dev.diisk.application.interfaces.IResponseService;
import br.dev.diisk.application.interfaces.fund_storage.IAddFundStorageCase;
import br.dev.diisk.application.interfaces.fund_storage.IListFundStorageCase;
import br.dev.diisk.application.interfaces.fund_storage.ISetFundStorageActiveCase;
import br.dev.diisk.application.interfaces.fund_storage.IUpdateFundStorageCase;
import br.dev.diisk.application.dtos.fund_storage.AddFundStorageDTO;
import br.dev.diisk.application.dtos.fund_storage.AddFundStorageRequest;
import br.dev.diisk.application.dtos.fund_storage.FundStorageResponse;
import br.dev.diisk.application.dtos.fund_storage.UpdateFundStorageRequest;
import br.dev.diisk.application.dtos.response.SuccessResponse;
import br.dev.diisk.domain.entities.FundStorage;
import br.dev.diisk.domain.entities.user.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/fund-storages")
@PreAuthorize("hasAuthority('DEFAULT')")
@SecurityRequirement(name = "bearer-key")
@RequiredArgsConstructor
public class FundStorageController {

    private final ModelMapper mapper;
    private final IResponseService responseService;
    private final IAddFundStorageCase addFundStorageCase;
    private final IUpdateFundStorageCase updateFundStorageCase;
    private final IListFundStorageCase listFundStorageCase;
    private final ISetFundStorageActiveCase setFundStorageActiveCase;

    @PostMapping
    public ResponseEntity<SuccessResponse<FundStorageResponse>> addFundStorage(
            @RequestBody @Valid AddFundStorageRequest dto,
            @AuthenticationPrincipal User user) {
        FundStorage fundStorage = addFundStorageCase.execute(mapper.map(dto, AddFundStorageDTO.class), user);
        FundStorageResponse response = mapper.map(fundStorage, FundStorageResponse.class);
        return responseService.created(response);
    }

    @PatchMapping("{id}")
    public ResponseEntity<SuccessResponse<FundStorageResponse>> updateFundStorage(
            @PathVariable Long id,
            @RequestBody @Valid UpdateFundStorageRequest dto,
            @AuthenticationPrincipal User user) {
        FundStorage fundStorage = updateFundStorageCase.execute(id, dto, user);
        FundStorageResponse response = mapper.map(fundStorage, FundStorageResponse.class);
        return responseService.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<SuccessResponse<FundStorageResponse>> deleteFundStorage(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        setFundStorageActiveCase.execute(id, false, user);
        return responseService.ok();
    }

    @GetMapping
    public ResponseEntity<SuccessResponse<List<FundStorageResponse>>> listFundStorage(
            @AuthenticationPrincipal User user) {
        Set<FundStorage> fundStorages = listFundStorageCase.execute(user.getId());
        List<FundStorageResponse> response = fundStorages.stream().map(fs -> mapper.map(fs, FundStorageResponse.class))
                .toList();
        return responseService.ok(response);
    }

}
