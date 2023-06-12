package com.test.api.repositories;

import com.test.api.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByLogin(String login);

    //Optional<Usuario> findByLogin(String login);

    boolean existsByLogin(String login);
}
