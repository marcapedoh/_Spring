package com.gestiondestock.spring.DAO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AutoCompleteDAO {
    private String codeArticle;
    private String designation;
}
