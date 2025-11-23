package com.exemplo.work.service;

import com.exemplo.work.dto.LoginDto;
import com.exemplo.work.dto.UsuarioDto;
import com.exemplo.work.model.Usuario;
import com.exemplo.work.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private final UsuarioRepository repo;

    public UsuarioService(UsuarioRepository repo) {
        this.repo = repo;
    }

    public Usuario cadastrar(UsuarioDto dto) {
        if(repo.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalStateException("Email já cadastrado");
        }
        Usuario usuario = new Usuario(null, dto.getNome(), dto.getEmail(), dto.getSenha(), dto.getProfissao());
        return repo.save(usuario);
    }

    public Usuario autenticar(LoginDto login) {
        return repo.findByEmailAndSenha(login.getEmail(), login.getSenha())
                .orElseThrow(() -> new EntityNotFoundException("Email ou senha inválidos"));
    }

    public Usuario buscarPorId(Long id) {
        return repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }
}