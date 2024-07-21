package br.dev.diisk.domain.dtos.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AuthRegisterResponseDTO {
        private Long id;
        private String email;
}
