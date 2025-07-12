package com.spring.equation.service;

import com.spring.equation.model.EquationEntity;
import com.spring.equation.model.EquationExpression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Slf4j
public class EquationService {
    private final Map<Long, EquationEntity> equationEntityConcurrentHashMap = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Autowired
    private EquationParser equationParser;
    @Autowired
    private EquationTreeBuilder equationTreeBuilder;
    @Autowired
    private EquationEvaluator equationEvaluator;

    public EquationEntity store(String infix) {
        log.info("Infix Expression : {}", infix);
        String fixed = equationParser.insertImplicitMultiplication(infix);
        log.info("Fixed Expression : {}", fixed);
        List<String> postfix = equationParser.toPostfix(fixed);
        log.info("PostFix Expression : {}", postfix);
        EquationEntity equationEntity = new EquationEntity();
        equationEntity.setId(idGenerator.getAndIncrement());
        equationEntity.setInfix(infix);
        equationEntity.setPostfix(String.join(" ", postfix));
        equationEntityConcurrentHashMap.put(equationEntity.getId(), equationEntity);
        log.info("Equation Concurrent HashMap : {}", equationEntityConcurrentHashMap);
        return equationEntity;
    }

    public List<EquationEntity> getAll() {
        return new ArrayList<>(equationEntityConcurrentHashMap.values());
    }

    public double evaluate(Long id, Map<String, Double> variables) {
        EquationEntity equationEntity = equationEntityConcurrentHashMap.get(id);
        log.info("Equation Entity : {}", equationEntity);
        if (null == equationEntity) {
            throw new NoSuchElementException("Equation not found");
        }
        List<String> postfix = List.of(equationEntity.getPostfix().split(" "));
        EquationExpression equationExpression = equationTreeBuilder.build(postfix);
        log.info("Algebraic Equation Expression : {}", equationExpression);
        return equationEvaluator.evaluate(equationExpression, variables);
    }
}
