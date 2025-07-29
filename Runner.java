package com.example.webhookapp.config;

import com.example.webhookapp.service.WebhookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {
    
    @Autowired
    private WebhookService webhookService;
    
    @Override
    public void run(String... args) throws Exception {
        Thread.sleep(1000);
        webhookService.execute();
    }
}