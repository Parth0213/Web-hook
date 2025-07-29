package com.example.webhookapp.model;

public class WebhookRequest {
    public String name;
    public String regNo;
    public String email;

    public WebhookRequest() {}
    public WebhookRequest(String name, String regNo, String email) {
        this.name = name;
        this.regNo = regNo;
        this.email = email;
    }
}