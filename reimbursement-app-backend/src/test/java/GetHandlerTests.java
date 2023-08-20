import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.kszczep5.AdminLimits;
import com.kszczep5.GetHandler;
import com.kszczep5.Reimbursement;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GetHandlerTests {

    @Test
    void testHandleWithReimbursements() throws Exception {
        // Arrange
        List<Reimbursement> reimbursements = new ArrayList<>();
        reimbursements.add(new Reimbursement());

        GetHandler getHandler = new GetHandler(reimbursements);

        HttpExchange exchange = mock(HttpExchange.class);
        OutputStream mockOutputStream = mock(OutputStream.class);
        when(exchange.getResponseBody()).thenReturn(mockOutputStream);

        // Act
        getHandler.handle(exchange);

        // Assert
        ArgumentCaptor<Integer> responseCodeCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(exchange).sendResponseHeaders(responseCodeCaptor.capture(), anyLong());

        assertEquals(200, responseCodeCaptor.getValue());
        ArgumentCaptor<byte[]> responseBodyCaptor = ArgumentCaptor.forClass(byte[].class);
        verify(mockOutputStream).write(responseBodyCaptor.capture());

        ObjectMapper objectMapper = new ObjectMapper();
        String expectedJson = objectMapper.writeValueAsString(reimbursements);
        String actualJson = new String(responseBodyCaptor.getValue());

        assertEquals(expectedJson, actualJson);
    }

    @Test
    void testHandleWithAdminLimits() throws Exception {
        // Arrange
        AdminLimits adminLimits = new AdminLimits();
        GetHandler getHandler = new GetHandler(adminLimits);

        HttpExchange exchange = mock(HttpExchange.class);
        OutputStream mockOutputStream = mock(OutputStream.class);
        when(exchange.getResponseBody()).thenReturn(mockOutputStream);

        // Act
        getHandler.handle(exchange);

        // Assert
        ArgumentCaptor<Integer> responseCodeCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(exchange).sendResponseHeaders(responseCodeCaptor.capture(), anyLong());

        assertEquals(200, responseCodeCaptor.getValue());

        ArgumentCaptor<byte[]> responseBodyCaptor = ArgumentCaptor.forClass(byte[].class);
        verify(mockOutputStream).write(responseBodyCaptor.capture());

        ObjectMapper objectMapper = new ObjectMapper();
        String expectedJson = objectMapper.writeValueAsString(adminLimits);
        String actualJson = new String(responseBodyCaptor.getValue());

        assertEquals(expectedJson, actualJson);
    }
}
