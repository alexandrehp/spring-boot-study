package com.test.api.services;

import com.test.api.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        //var username =  repository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        var username =  repository.findByLogin(login);
        if (username == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        return username;
    }
}
