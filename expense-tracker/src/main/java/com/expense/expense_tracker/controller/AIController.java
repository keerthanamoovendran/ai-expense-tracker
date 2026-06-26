package com.expense.expense_tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expense.expense_tracker.service.AIService;

@RestController
@RequestMapping("/api/advice")
public class AIController {

    @Autowired
    private AIService aiService;

    @GetMapping
    public String getAdvice() {
        return aiService.getSpendingAdvice();
    }
}