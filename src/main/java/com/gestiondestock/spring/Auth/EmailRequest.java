package com.gestiondestock.spring.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class EmailRequest {
    private List<String> to;
    private String subject;
    private String text;
}
