package com.spring.equation.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class EquationParser {

    public List<String> toPostfix(String expr) {
        log.info("Algebraic Equation Expression : {}", expr);
        List<String> output = new ArrayList<>();
        Deque<String> stack = new ArrayDeque<>();
        StringTokenizer tokenizer = new StringTokenizer(expr, "()+-*/^ ", true);
        Map<String, Integer> precedence = Map.of("+", 1, "-", 1, "*", 2, "/", 2, "^", 3);
        log.info("Precedence Map : {}", precedence);
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();
            if (token.isEmpty()) {
                continue;
            }
            if (Character.isLetterOrDigit(token.charAt(0))) {
                output.add(token);
            } else if (("(").equals(token)) {
                stack.push(token);
            } else if ((")").equals(token)) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    output.add(stack.pop());
                }
                stack.pop();
            } else {
                while (!stack.isEmpty() &&
                        precedence.getOrDefault(stack.peek(), 0) >= precedence.getOrDefault(token, 0)) {
                    output.add(stack.pop());
                }
                stack.push(token);
            }
        }
        while (!stack.isEmpty()) {
            output.add(stack.pop());
        }
        log.info("Equation in PostFix Format : {}", output);
        return output;
    }

    public String insertImplicitMultiplication(String expr) {
        return expr
                // number followed by variable → insert '*'
                .replaceAll("(?<=[0-9])(?=[a-zA-Z])", "*")
                // variable followed by variable → insert '*'
                .replaceAll("(?<=[a-zA-Z])(?=[a-zA-Z])", "*")
                // variable followed by '(' → insert '*'
                .replaceAll("(?<=[a-zA-Z])(?=\\()", "*")
                // ')' followed by variable or number → insert '*'
                .replaceAll("(?<=\\))(?=[a-zA-Z0-9])", "*");
    }
}
