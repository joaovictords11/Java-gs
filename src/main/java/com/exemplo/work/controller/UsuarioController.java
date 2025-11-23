package com.exemplo.work.controller;

import com.exemplo.work.dto.LoginDto;
import com.exemplo.work.dto.UsuarioDto;
import com.exemplo.work.model.Usuario;
import com.exemplo.work.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Usuario> cadastrar(@RequestBody @Valid UsuarioDto dto) {
        return ResponseEntity.ok(service.cadastrar(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody LoginDto dto) {
        // Retorna o objeto Usuário completo se o login for sucesso
        // O Front-end deve salvar o ID desse usuário para fazer as próximas requisições
        return ResponseEntity.ok(service.autenticar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obterPerfil(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }
}