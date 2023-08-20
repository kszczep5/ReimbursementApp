package com.kszczep5;

import com.sun.net.httpserver.HttpExchange;
import java.io.OutputStream;

public class HttpUtils {
    public static void sendResponse(HttpExchange exchange, int statusCode, String response) {
        try {
            exchange.sendResponseHeaders(statusCode, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
