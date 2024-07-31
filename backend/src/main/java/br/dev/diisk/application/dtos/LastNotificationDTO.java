package br.dev.diisk.application.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class LastNotificationDTO {
    private String categoryName;
    private String message;
}
