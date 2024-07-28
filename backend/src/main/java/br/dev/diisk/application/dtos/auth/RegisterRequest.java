package br.dev.diisk.application.dtos.auth;

import br.dev.diisk.domain.GlobalMessages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
        @Email(message = GlobalMessages.INVALID_FIELD)
        private String email;
        @NotBlank(message = GlobalMessages.BLANK_FIELD)
        private String name;
        @NotBlank(message = GlobalMessages.BLANK_FIELD)
        private String password;
}
