package com.test.api.records;

import jakarta.validation.constraints.NotBlank;
public record DadosCadastroUsuario(

        @NotBlank
        String nome,
        @NotBlank
        String login,
        @NotBlank
        String senha) {

}

