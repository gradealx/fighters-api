package com.example.fightersapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
public class Fighter {
    @Id
    private String id;
    private String name;
    private Integer experience;
    private boolean injured;
}
