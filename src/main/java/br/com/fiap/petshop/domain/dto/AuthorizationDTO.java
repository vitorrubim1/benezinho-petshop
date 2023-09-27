package br.com.fiap.petshop.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthorizationDTO (@NotNull @Email String username, @NotNull @Min(6) @NotBlank String password) {
}
