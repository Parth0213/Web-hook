package com.example.webhookapp.service;

import com.example.webhookapp.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WebhookService {
    private static final String WEBHOOK_URL = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";
    private static final String SUBMIT_URL = "https://bfhldevapigw.healthrx.co.in/hiring/testWebhook/JAVA";
    
    @Autowired
    private QueryService queryService;
    
    private final RestTemplate restTemplate = new RestTemplate();
    
    public void execute() {
        try {
            WebhookResponse response = generate();
            if (response != null && response.accessToken != null) {
                String query = queryService.buildQuery("REG12347");
                submit(query, response.accessToken);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    private WebhookResponse generate() {
        WebhookRequest req = new WebhookRequest("John Doe", "REG12347", "john@example.com");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<WebhookRequest> entity = new HttpEntity<>(req, headers);
        
        try {
            return restTemplate.postForObject(WEBHOOK_URL, entity, WebhookResponse.class);
        } catch (Exception e) {
            return null;
        }
    }
    
    private void submit(String query, String token) {
        SolutionRequest sol = new SolutionRequest(query);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        HttpEntity<SolutionRequest> entity = new HttpEntity<>(sol, headers);
        
        try {
            restTemplate.postForObject(SUBMIT_URL, entity, String.class);
        } catch (Exception e) {
            System.err.println("Submit failed: " + e.getMessage());
        }
    }
}