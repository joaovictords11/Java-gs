package com.exemplo.work.service;

import com.exemplo.work.dto.DicaDto;
import com.exemplo.work.model.Dica;
import com.exemplo.work.model.Usuario;
import com.exemplo.work.repository.DicaRepository;
import com.exemplo.work.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DicaService {
    private final DicaRepository dicaRepo;
    private final UsuarioRepository usuarioRepo;

    public DicaService(DicaRepository dicaRepo, UsuarioRepository usuarioRepo) {
        this.dicaRepo = dicaRepo;
        this.usuarioRepo = usuarioRepo;
    }

    public Page<Dica> listar(Pageable pageable) {
        return dicaRepo.findAll(pageable);
    }

    public Dica criar(DicaDto dto) {
        Usuario autor = usuarioRepo.findById(dto.getAutorId())
                .orElseThrow(() -> new EntityNotFoundException("Autor não encontrado"));

        Dica dica = new Dica(null, dto.getTitulo(), dto.getDescricao(), dto.getCategoria(), LocalDateTime.now(), autor);
        return dicaRepo.save(dica);
    }

    public void excluir(Long id) {
        if (!dicaRepo.existsById(id)) {
            throw new EntityNotFoundException("Dica não encontrada");
        }
        dicaRepo.deleteById(id);
    }

    public Page<Dica> buscarPorTitulo(String titulo, Pageable pageable) {
        return dicaRepo.findByTituloContainingIgnoreCase(titulo, pageable);
    }
}