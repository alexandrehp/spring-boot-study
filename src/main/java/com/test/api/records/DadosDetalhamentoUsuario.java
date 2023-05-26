package com.test.api.records;

import com.test.api.models.Usuario;

public record DadosDetalhamentoUsuario(String nome, String login) {
    public DadosDetalhamentoUsuario(Usuario usuario) {
        this(usuario.getNome(), usuario.getLogin());
    }
}
