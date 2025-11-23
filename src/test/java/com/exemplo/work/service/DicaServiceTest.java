package com.exemplo.work.service;

import com.exemplo.work.dto.DicaDto;
import com.exemplo.work.model.Dica;
import com.exemplo.work.model.Usuario;
import com.exemplo.work.repository.DicaRepository;
import com.exemplo.work.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DicaServiceTest {

    @Mock
    private DicaRepository dicaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private DicaService dicaService;

    @Test
    @DisplayName("Deve listar dicas com paginação")
    void testListar() {
        Pageable pageable = Pageable.ofSize(10);
        List<Dica> dicas = List.of(new Dica(), new Dica());
        Page<Dica> pagina = new PageImpl<>(dicas);

        when(dicaRepository.findAll(pageable)).thenReturn(pagina);

        Page<Dica> resultado = dicaService.listar(pageable);

        assertEquals(2, resultado.getTotalElements());
        verify(dicaRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Deve criar dica com sucesso")
    void testCriar_Success() {
        // Arrange
        DicaDto dto = new DicaDto();
        dto.setTitulo("Aprenda React");
        dto.setDescricao("Curso top");
        dto.setCategoria("Tecnologia");
        dto.setAutorId(1L);

        Usuario autor = new Usuario(1L, "João", "joao@email.com", "123", "Dev", new ArrayList<>());

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(autor));
        when(dicaRepository.save(any(Dica.class))).thenAnswer(invocation -> {
            Dica d = invocation.getArgument(0);
            d.setId(100L);
            return d;
        });

        // Act
        Dica criada = dicaService.criar(dto);

        // Assert
        assertNotNull(criada);
        assertEquals(100L, criada.getId());
        assertEquals("Aprenda React", criada.getTitulo());
        assertEquals(autor, criada.getAutor());
        verify(dicaRepository, times(1)).save(any(Dica.class));
    }

    @Test
    @DisplayName("Deve falhar ao criar dica se autor não existir")
    void testCriar_Fail_AutorInexistente() {
        DicaDto dto = new DicaDto();
        dto.setAutorId(99L);

        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> dicaService.criar(dto));
        verify(dicaRepository, never()).save(any(Dica.class));
    }

    @Test
    @DisplayName("Deve buscar dicas por título")
    void testBuscarPorTitulo() {
        String termo = "Java";
        Pageable pageable = Pageable.unpaged();
        when(dicaRepository.findByTituloContainingIgnoreCase(termo, pageable))
                .thenReturn(new PageImpl<>(List.of(new Dica())));

        Page<Dica> res = dicaService.buscarPorTitulo(termo, pageable);

        assertEquals(1, res.getTotalElements());
    }
}