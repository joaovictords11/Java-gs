package com.exemplo.work.service;

import com.exemplo.work.dto.LoginDto;
import com.exemplo.work.dto.UsuarioDto;
import com.exemplo.work.model.Usuario;
import com.exemplo.work.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

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
        Usuario usuario = new Usuario(null, dto.getNome(), dto.getEmail(), dto.getSenha(), dto.getProfissao(), new ArrayList<>());
        return repo.save(usuario);
    }

    public Usuario autenticar(LoginDto login) {
        return repo.findByEmailAndSenha(login.getEmail(), login.getSenha())
                .orElseThrow(() -> new EntityNotFoundException("Email ou senha inválidos"));
    }

    public Usuario buscarPorId(Long id) {
        return repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }

    @Transactional
    public Usuario atualizar(Long id, UsuarioDto dto) {
        Usuario usuario = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        if (!usuario.getEmail().equals(dto.getEmail()) && repo.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalStateException("Email já está em uso por outro usuário");
        }

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        usuario.setProfissao(dto.getProfissao());

        return repo.save(usuario);
    }

    public void excluir(Long id) {
        Usuario usuario = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        repo.delete(usuario);
    }
}