package com.spring.equation.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Data
public class EquationRequestDTO implements Serializable {

    private static final long serialVersionUID = 7707576683418425977L;

    private String equation;
}
