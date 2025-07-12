package com.spring.equation.service;

import com.spring.equation.model.EquationExpression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

@Service
@Slf4j
public class EquationTreeBuilder {

    public EquationExpression build(List<String> postfix) {
        log.info("PostFix Expression : {}", postfix);
        Deque<EquationExpression> stack = new ArrayDeque<>();
        for (String token : postfix) {
            if ("+-*/^".contains(token)) {
                EquationExpression right = stack.pop();
                EquationExpression left = stack.pop();
                EquationExpression op = new EquationExpression(token);
                op.left = left;
                op.right = right;
                stack.push(op);
            } else {
                stack.push(new EquationExpression(token));
            }
        }
        log.info("Stack Elements : {}", stack);
        return stack.pop();
    }

}
