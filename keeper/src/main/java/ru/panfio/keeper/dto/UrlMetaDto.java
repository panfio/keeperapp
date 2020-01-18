package ru.panfio.keeper.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UrlMetaDto {
    private String title;
    private String description;
    private String cover;
}
