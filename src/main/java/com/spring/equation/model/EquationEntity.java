package com.spring.equation.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@Data
@ToString
public class EquationEntity implements Serializable {

    private static final long serialVersionUID = 7707576683418425977L;

    private Long id;
    private String infix;
    private String postfix;
}
