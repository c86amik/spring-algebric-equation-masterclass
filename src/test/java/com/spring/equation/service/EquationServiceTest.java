package com.spring.equation.service;

import com.spring.equation.model.EquationExpression;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Slf4j
public class EquationServiceTest {
    @Autowired
    private EquationParser equationParser;
    @Autowired
    private EquationTreeBuilder equationTreeBuilder;
    @Autowired
    private EquationEvaluator equationEvaluator;

    @Test
    void testSimpleEquation() {
        List<String> postfix = equationParser.toPostfix("3 * x + 2");
        EquationExpression equationExpression = equationTreeBuilder.build(postfix);
        double result = equationEvaluator.evaluate(equationExpression, Map.of("x", 4.0));
        log.info("testSimpleEquation() -> Test Results : {}", result);
        assertEquals(14.0, result);
    }

    @Test
    void testExponentiation() {
        List<String> postfix = equationParser.toPostfix("x ^ 2 + y ^ 2");
        EquationExpression equationExpression = equationTreeBuilder.build(postfix);
        double result = equationEvaluator.evaluate(equationExpression, Map.of("x", 3.0, "y", 4.0));
        log.info("testExponentiation() -> Test Results : {}", result);
        assertEquals(25.0, result);
    }

    @Test
    void testMissingVariableThrows() {
        List<String> postfix = equationParser.toPostfix("x + y");
        EquationExpression equationExpression = equationTreeBuilder.build(postfix);
        log.info("testMissingVariableThrows() -> Test Results : {}", equationExpression);
        assertThrows(IllegalArgumentException.class, () -> equationEvaluator.evaluate(equationExpression, Map.of("x", 1.0)));
    }

    @Test
    void testDivisionByZero() {
        List<String> postfix = equationParser.toPostfix("x / y");
        EquationExpression equationExpression = equationTreeBuilder.build(postfix);
        log.info("testDivisionByZero() -> Test Results : {}", equationExpression);
        assertThrows(ArithmeticException.class, () -> equationEvaluator.evaluate(equationExpression, Map.of("x", 5.0, "y", 0.0)));
    }

    @Test
    void testComplexExpression() {
        List<String> postfix = equationParser.toPostfix("(2 + x) * (y - 3) / z");
        EquationExpression equationExpression = equationTreeBuilder.build(postfix);
        double result = equationEvaluator.evaluate(equationExpression, Map.of("x", 1.0, "y", 5.0, "z", 2.0));
        log.info("testComplexExpression() -> Test Results : {}", result);
        assertEquals(3.0, result);
    }
}
