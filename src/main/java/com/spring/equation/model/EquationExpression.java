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
public class EquationExpression implements Serializable {

    private static final long serialVersionUID = 7707576683418425977L;

    public String value;
    public EquationExpression left;
    public EquationExpression right;

    public EquationExpression(String value) {
        this.value = value;
    }

}
