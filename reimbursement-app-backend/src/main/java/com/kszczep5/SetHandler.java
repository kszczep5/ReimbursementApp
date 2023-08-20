package com.kszczep5;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class SetHandler implements HttpHandler {

    private List<Reimbursement> reimbursements;
    private AdminLimits adminLimits;
    private String requestBody;

    public SetHandler(List<Reimbursement> reimbursements, AdminLimits adminLimits) {
        this.reimbursements = reimbursements;
        this.adminLimits = adminLimits;
    }

    public SetHandler(AdminLimits adminLimits) {
        this.adminLimits = adminLimits;
    }

    @Override
    public void handle(HttpExchange exchange) {
        try {
            requestBody = buildBody(exchange);
        } catch (Exception e) {
            HttpUtils.sendResponse(exchange, 500, "Internal Server Error");
        }

        try {
            if (reimbursements != null && adminLimits != null) {
                mapperToObject(reimbursements, exchange, adminLimits);
            }

            if (adminLimits != null && reimbursements == null) {
                mapperToObject(adminLimits, exchange, null);
            }
        } catch (Exception e) {
            HttpUtils.sendResponse(exchange, 400, "Invalid Request");
        }
    }

    private String buildBody(HttpExchange exchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder jsonString = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            jsonString.append(line);
        }
        br.close();
        isr.close();
        return jsonString.toString();
    }

    private <T> void mapperToObject(T object, HttpExchange exchange, AdminLimits adminLimitsCalc) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        if (object instanceof List) {
            Reimbursement newReimbursement = objectMapper.readValue(requestBody, Reimbursement.class);
            TotalReimbursementCalculator.calculateTotalReimbursement(newReimbursement, adminLimitsCalc);
            reimbursements.add(newReimbursement);
            System.out.println("New Reimbursement: " + requestBody);

        }
        if (object instanceof AdminLimits) {
            AdminLimits newAdminLimits = objectMapper.readValue(requestBody, AdminLimits.class);
            adminLimits.setDistanceLimit(newAdminLimits.getDistanceLimit());
            adminLimits.setCarMileageRate(newAdminLimits.getCarMileageRate());
            adminLimits.setReceipts(newAdminLimits.getReceipts());
            adminLimits.setDailyAllowanceRate(newAdminLimits.getDailyAllowanceRate());
            adminLimits.setTotalReimbursementLimit(newAdminLimits.getTotalReimbursementLimit());
            adminLimits.setReceiptsLimit(newAdminLimits.getReceiptsLimit());
            System.out.println("New Admin Limits: " + requestBody);
        }
        HttpUtils.sendResponse(exchange, 200, "Data set successfully");
    }

}

