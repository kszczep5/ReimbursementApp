package com.kszczep5;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    private static Map<String, String> dataStore = new HashMap<>();
    private static List<Reimbursement> reimbursements = new ArrayList<>();
    private static AdminLimits adminLimits = new AdminLimits();

    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

            HttpHandler corsGetReimbursementHandler = new HttpHandlerWithCORS(new GetHandler(reimbursements));
            HttpHandler corsSetReimbursementHandler = new HttpHandlerWithCORS(new SetHandler(reimbursements, adminLimits));

            HttpHandler corsGetAdminLimitsHandler = new HttpHandlerWithCORS(new GetHandler(adminLimits));
            HttpHandler corsSetAdminLimitsHandler = new HttpHandlerWithCORS(new SetHandler(adminLimits));

            server.createContext("/getReimbursement", corsGetReimbursementHandler);
            server.createContext("/setReimbursement", corsSetReimbursementHandler);

            server.createContext("/getAdminLimits", corsGetAdminLimitsHandler);
            server.createContext("/setAdminLimits", corsSetAdminLimitsHandler);

            server.setExecutor(null);
            server.start();

            System.out.println("Server started successfully on port 8080");
        } catch (IOException e) {
            System.err.println("An error occurred while starting the server: " + e.getMessage());
        }
    }

}
