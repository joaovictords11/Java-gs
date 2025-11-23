package com.exemplo.work.controller;

import com.exemplo.work.dto.DicaDto;
import com.exemplo.work.model.Dica;
import com.exemplo.work.service.DicaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dicas")
public class DicaController {

    private final DicaService service;

    public DicaController(DicaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<Dica>> listar(
            @RequestParam(required = false) String busca,
            @PageableDefault(size = 10, sort = "dataCriacao", direction = Sort.Direction.DESC) Pageable pageable) {

        if (busca != null && !busca.isEmpty()) {
            return ResponseEntity.ok(service.buscarPorTitulo(busca, pageable));
        }
        return ResponseEntity.ok(service.listar(pageable));
    }

    @PostMapping
    public ResponseEntity<Dica> criar(@RequestBody @Valid DicaDto dto) {
        return ResponseEntity.ok(service.criar(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}