package com.test.api.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Getter
@EqualsAndHashCode(of = "id")
@ToString(of = {"id", "nome"})
@NoArgsConstructor
@Entity
@Table(name = "perfis")
public class Perfil implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @Override
    public String getAuthority() {
        return nome;
    }

//    @Test
//    public void getAuthority() {
//        Perfil perfil = new Perfil(1L, "ROLE_ADMIN");
//        assertEquals("ROLE_ADMIN", perfil.getAuthority());
//    }
//
//    @Test
//    public void getAuthority_NomeNulo() {
//        Perfil perfil = new Perfil(1L, null);
//        assertNull(perfil.getAuthority());
//    }
//
//    @Test
//    public void getAuthority_NomeVazio() {
//        Perfil perfil = new Perfil(1L, "");
//        assertNull(perfil.getAuthority());
//    }
//
//    @Test
//    public void getAuthority_NomeVazioEspaco() {
}
