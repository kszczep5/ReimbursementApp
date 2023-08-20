import static org.mockito.Mockito.*;

import com.sun.net.httpserver.HttpExchange;
import com.kszczep5.AdminLimits;
import com.kszczep5.Reimbursement;
import com.kszczep5.SetHandler;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;

public class SetHandlerTests {

    @Test
    void testHandleWithValidReimbursement() throws Exception {
        // Arrange
        List<Reimbursement> reimbursements = new ArrayList<>();
        AdminLimits adminLimits = new AdminLimits();
        SetHandler setHandler = new SetHandler(reimbursements, adminLimits);

        String requestBody = "{\"tripDate\":\"2023-08-19\", \"selectedReceipts\":[], \"claimDays\":1, \"disableSpecificDays\":false, \"carDistance\":50}";

        HttpExchange exchange = mock(HttpExchange.class);
        when(exchange.getRequestBody()).thenReturn(new ByteArrayInputStream(requestBody.getBytes(StandardCharsets.UTF_8)));

        OutputStream mockOutputStream = mock(OutputStream.class);
        when(exchange.getResponseBody()).thenReturn(mockOutputStream);

        ByteArrayOutputStream capturedOutputStream = new ByteArrayOutputStream();
        when(exchange.getResponseBody()).thenReturn(capturedOutputStream);

        // Act
        setHandler.handle(exchange);

        // Assert
        ArgumentCaptor<Integer> responseCodeCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(exchange).sendResponseHeaders(responseCodeCaptor.capture(), anyLong());

        assertEquals(200, responseCodeCaptor.getValue());

        String responseBody = capturedOutputStream.toString();
        assertEquals("Data set successfully", responseBody);
        assertEquals(1, reimbursements.size());
    }

    @Test
    void testHandleWithInvalidReimbursement() throws Exception {
        // Arrange
        AdminLimits adminLimits = new AdminLimits();
        SetHandler setHandler = new SetHandler(adminLimits);

        String invalidRequestBody = "invalid request body";

        HttpExchange exchange = mock(HttpExchange.class);
        when(exchange.getRequestBody()).thenReturn(new ByteArrayInputStream(invalidRequestBody.getBytes(StandardCharsets.UTF_8)));

        OutputStream mockOutputStream = mock(OutputStream.class);
        when(exchange.getResponseBody()).thenReturn(mockOutputStream);

        ByteArrayOutputStream capturedOutputStream = new ByteArrayOutputStream();
        when(exchange.getResponseBody()).thenReturn(capturedOutputStream);

        // Act
        setHandler.handle(exchange);

        // Assert
        ArgumentCaptor<Integer> responseCodeCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(exchange).sendResponseHeaders(responseCodeCaptor.capture(), anyLong());

        assertEquals(400, responseCodeCaptor.getValue());

        String responseBody = capturedOutputStream.toString();
        assertEquals("Invalid Request", responseBody);
    }

}
