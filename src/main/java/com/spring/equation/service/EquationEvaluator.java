package com.spring.equation.service;

import com.spring.equation.model.EquationExpression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class EquationEvaluator {

    public double evaluate(EquationExpression equationExpression, Map<String, Double> variables) {
        log.info("Equation Expression : {}", equationExpression);
        if (equationExpression.left == null && equationExpression.right == null) {
            try {
                return Double.parseDouble(equationExpression.value);
            } catch (NumberFormatException e) {
                if (!variables.containsKey(equationExpression.value)) {
                    throw new IllegalArgumentException("Missing variable: " + equationExpression.value);
                }
                return variables.get(equationExpression.value);
            }
        }

        double left = evaluate(equationExpression.left, variables);
        double right = evaluate(equationExpression.right, variables);

        return switch (equationExpression.value) {
            case "+" -> left + right;
            case "-" -> left - right;
            case "*" -> left * right;
            case "/" -> {
                if (0 == right) {
                    throw new ArithmeticException("Division by zero");
                }
                yield left / right;
            }
            case "^" -> Math.pow(left, right);
            default -> throw new IllegalArgumentException("Invalid operator: " + equationExpression.value);
        };
    }
}
