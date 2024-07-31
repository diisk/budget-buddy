package br.dev.diisk.application.dtos;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class HistoryGraphsDTO {
    private List<HistoryDTO> incomeHistory;
    private List<HistoryDTO> expenseHistory;
}
