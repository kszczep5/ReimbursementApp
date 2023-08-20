package com.kszczep5;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.List;

public class GetHandler implements HttpHandler {

    private List<Reimbursement> reimbursements;
    private AdminLimits adminLimits;

    public GetHandler(List<Reimbursement> reimbursements) {
        this.reimbursements = reimbursements;
    }

    public GetHandler(AdminLimits adminLimits) {
        this.adminLimits = adminLimits;
    }

    @Override
    public void handle(HttpExchange exchange) {
        try {
            if (reimbursements != null) {
                mapperToString(reimbursements, exchange);
            }
            if (adminLimits != null) {
                mapperToString(adminLimits, exchange);
            }
        } catch (Exception e) {
            HttpUtils.sendResponse(exchange, 500, "Error processing request: " + e.getMessage());
        }
    }

    private <T> void mapperToString(T object, HttpExchange exchange) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonReimbursements = objectMapper.writeValueAsString(object);
        HttpUtils.sendResponse(exchange, 200, jsonReimbursements);
        if (object instanceof List) {
            System.out.println("GET Reimbursement Request Successful: " + jsonReimbursements);
        }
        if (object instanceof AdminLimits) {
            System.out.println("GET AdminLimits Request Successful: " + jsonReimbursements);
        }
    }
}
