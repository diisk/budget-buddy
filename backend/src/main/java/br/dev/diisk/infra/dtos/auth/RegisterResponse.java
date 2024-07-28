package br.dev.diisk.infra.dtos.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RegisterResponse {
        private Long id;
        private String email;
}
