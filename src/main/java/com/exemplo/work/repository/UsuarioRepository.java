package com.exemplo.work.repository;

import com.exemplo.work.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    // Método auxiliar para login
    Optional<Usuario> findByEmailAndSenha(String email, String senha);
}