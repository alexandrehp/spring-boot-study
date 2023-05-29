package com.test.api.services;

import com.test.api.configs.exceptions.ValidacaoException;
import com.test.api.models.Usuario;
import com.test.api.repositories.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Transactional
    public Usuario salvar(Usuario usuario) {

        boolean usuarioExistente = repository.existsByLogin(usuario.getLogin());

        if (usuarioExistente) {
            throw new ValidacaoException("Já existe um usuário cadastrado com esse login");
        }

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return repository.save(usuario);
    }

    public Usuario findById(Long id) {
        return repository.getReferenceById(id);
    }

    public List<Usuario> findAll() {
        return repository.findAll();
    }
}
