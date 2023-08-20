package com.kszczep5;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class HttpHandlerWithCORS implements HttpHandler {

    private final HttpHandler handler;

    public HttpHandlerWithCORS(HttpHandler handler) {
        this.handler = handler;
    }

    @Override
    public void handle(HttpExchange exchange) {
        try {
            Headers headers = exchange.getResponseHeaders();
            headers.add("Access-Control-Allow-Origin", "*");
            headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            headers.add("Access-Control-Allow-Headers", "Origin, Content-Type, Accept, Authorization");

            if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            handler.handle(exchange);
        } catch (Exception e) {
            String errorMessage = "An error occurred: " + e.getMessage();
            HttpUtils.sendResponse(exchange, 500, errorMessage);
        }
    }

}

