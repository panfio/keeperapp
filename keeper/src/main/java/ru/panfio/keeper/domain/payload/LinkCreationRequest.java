package ru.panfio.keeper.domain.payload;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LinkCreationRequest {
    private String link;
    private Long userId;
}
