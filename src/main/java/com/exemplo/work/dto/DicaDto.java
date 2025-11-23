package com.exemplo.work.dto;

import lombok.Data;

@Data
public class DicaDto {
    private String titulo;
    private String descricao;
    private String categoria;
    private Long autorId;
}