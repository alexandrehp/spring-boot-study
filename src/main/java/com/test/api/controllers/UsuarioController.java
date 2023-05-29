package com.test.api.controllers;

import com.test.api.models.Usuario;
import com.test.api.records.usuario.DadosCadastroUsuario;
import com.test.api.records.usuario.DadosDetalhamentoUsuario;
import com.test.api.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroUsuario dados, UriComponentsBuilder uriBuilder) {
        var usuario =  service.salvar(new Usuario(dados));

        var uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoUsuario(usuario));
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var usuario = service.findById(id);
        return ResponseEntity.ok(new DadosDetalhamentoUsuario(usuario));
    }

    @GetMapping
    public ResponseEntity listar() {
        return ResponseEntity.ok(service.findAll());
    }

}
