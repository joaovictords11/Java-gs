package com.exemplo.work.service;

import com.exemplo.work.dto.LoginDto;
import com.exemplo.work.dto.UsuarioDto;
import com.exemplo.work.model.Usuario;
import com.exemplo.work.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    @DisplayName("Deve cadastrar usuário com sucesso")
    void testCadastrar_Success() {
        // Arrange
        UsuarioDto dto = new UsuarioDto();
        dto.setNome("Maria");
        dto.setEmail("maria@teste.com");
        dto.setSenha("123");
        dto.setProfissao("Designer");

        when(usuarioRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> {
            Usuario u = invocation.getArgument(0);
            u.setId(1L);
            return u;
        });

        // Act
        Usuario usuarioSalvo = usuarioService.cadastrar(dto);

        // Assert
        assertNotNull(usuarioSalvo);
        assertEquals(1L, usuarioSalvo.getId());
        assertEquals("maria@teste.com", usuarioSalvo.getEmail());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve lançar erro ao cadastrar email duplicado")
    void testCadastrar_Fail_EmailDuplicado() {
        UsuarioDto dto = new UsuarioDto();
        dto.setEmail("existente@teste.com");

        when(usuarioRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(new Usuario()));

        assertThrows(IllegalStateException.class, () -> usuarioService.cadastrar(dto));
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve autenticar usuário com sucesso")
    void testAutenticar_Success() {
        LoginDto login = new LoginDto();
        login.setEmail("maria@teste.com");
        login.setSenha("123");

        Usuario usuarioBanco = new Usuario(1L, "Maria", "maria@teste.com", "123", "Designer");
        when(usuarioRepository.findByEmailAndSenha(login.getEmail(), login.getSenha()))
                .thenReturn(Optional.of(usuarioBanco));

        Usuario resultado = usuarioService.autenticar(login);

        assertNotNull(resultado);
        assertEquals(usuarioBanco.getId(), resultado.getId());
    }

    @Test
    @DisplayName("Deve falhar autenticação com credenciais inválidas")
    void testAutenticar_Fail() {
        LoginDto login = new LoginDto();
        login.setEmail("errado@teste.com");
        login.setSenha("000");

        when(usuarioRepository.findByEmailAndSenha(login.getEmail(), login.getSenha()))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> usuarioService.autenticar(login));
    }
}