package com.expense.expense_tracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.expense.expense_tracker.model.Expense;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AIService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ExpenseService expenseService;

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    public String getSpendingAdvice() {
        List<Expense> expenses = expenseService.getAllExpenses();

        if (expenses.isEmpty()) {
            return "You don't have any expenses logged yet. Add a few expenses first, then come back here for personalized advice.";
        }

        StringBuilder expenseSummary = new StringBuilder();
        double total = 0;
        for (Expense e : expenses) {
            expenseSummary.append("- ").append(e.getTitle())
                    .append(" | Category: ").append(e.getCategory())
                    .append(" | Amount: ").append(e.getAmount())
                    .append(" | Date: ").append(e.getDate())
                    .append("\n");
            total += e.getAmount();
        }

        String prompt = "You are a helpful personal finance assistant. Here is a list of a user's expenses:\n\n"
                + expenseSummary
                + "\nTotal spent: " + total
                + "\n\nBased on this data, give the user 3-4 short, practical, friendly tips to improve their spending habits. "
                + "Keep it concise, use simple language, and format as a short bulleted list.";

        return callGeminiApi(prompt);
    }

    private String callGeminiApi(String prompt) {
        try {
            String url = apiUrl + "?key=" + apiKey;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String requestBody = """
                {
                  "contents": [{
                    "parts": [{"text": %s}]
                  }]
                }
                """.formatted(new ObjectMapper().writeValueAsString(prompt));

            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

            String response = restTemplate.postForObject(url, requestEntity, String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);
            return root.path("candidates").get(0)
                    .path("content").path("parts").get(0)
                    .path("text").asText();

        } catch (Exception e) {
            return "Sorry, I couldn't get advice right now. Error: " + e.getMessage();
        }
    }
}