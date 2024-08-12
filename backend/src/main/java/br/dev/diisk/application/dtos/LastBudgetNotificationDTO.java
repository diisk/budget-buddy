package br.dev.diisk.application.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class LastBudgetNotificationDTO {
    private String categoryName;
    private String message;
}
