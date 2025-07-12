package com.spring.equation.controller;

import com.spring.equation.dto.EquationRequestDTO;
import com.spring.equation.model.EquationEntity;
import com.spring.equation.service.EquationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/equations")
@Slf4j
public class EquationController {

    @Autowired
    private EquationService equationService;

    @PostMapping("/store")
    public Map<String, Object> store(@RequestBody EquationRequestDTO equationRequestDTO) {
        EquationEntity equationEntity = equationService.store(equationRequestDTO.getEquation());
        log.info("Equation Entity in Controller : {}", equationEntity);
        return Map.of("message", "Equation stored successfully", "equationId", equationEntity.getId());
    }

    @GetMapping
    public Map<String, Object> getAll() {
        List<Map<String, Object>> results = equationService.getAll().stream()
                .map(e -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("equationId", e.getId());
                    map.put("equation", e.getInfix());
                    return map;
                })
                .collect(Collectors.toList());
        log.info("Results in Controller : {}", results);
        return Map.of("equations", results);
    }

    @PostMapping("/{id}/evaluate")
    public Map<String, Object> evaluate(@PathVariable Long id, @RequestBody Map<String, Map<String, Double>> body) {
        double result = equationService.evaluate(id, body.get("variables"));
        EquationEntity equationEntity = equationService.getAll().stream()
                .filter(eq -> eq.getId().equals(id)).findFirst().orElseThrow();
        log.info("Equation Entity by ID; {}", equationEntity);
        return Map.of("equationId", id, "equation", equationEntity.getInfix(),
                "variables", body.get("variables"), "result", result);
    }
}
