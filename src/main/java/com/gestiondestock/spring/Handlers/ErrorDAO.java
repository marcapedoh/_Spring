package com.gestiondestock.spring.Handlers;

import com.gestiondestock.spring.Exception.ErrorCodes;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDAO {
    private Integer httpcode;

    private ErrorCodes codes;

    private String message;

    private List<String> error=new ArrayList<>();
}
