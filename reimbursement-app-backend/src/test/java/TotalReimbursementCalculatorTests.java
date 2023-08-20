import com.kszczep5.AdminLimits;
import com.kszczep5.Receipt;
import com.kszczep5.Reimbursement;
import com.kszczep5.TotalReimbursementCalculator;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.mockito.Mockito.*;

public class TotalReimbursementCalculatorTests {

    @Test
    public void testCalculateTotalReimbursement() {
        // Arrange
        Reimbursement reimbursementMock = mock(Reimbursement.class);
        AdminLimits adminLimitsMock = mock(AdminLimits.class);

        when(reimbursementMock.getSelectedReceipts()).thenReturn(
                Arrays.asList(new Receipt("Receipt 1", 50), new Receipt("Receipt 2", 75))
        );
        when(reimbursementMock.getClaimDays()).thenReturn(5);
        when(reimbursementMock.getCarDistance()).thenReturn(100);
        when(adminLimitsMock.getDailyAllowanceRate()).thenReturn(25);
        when(adminLimitsMock.getCarMileageRate()).thenReturn(0.5);
        when(adminLimitsMock.getTotalReimbursementLimit()).thenReturn(1000.0);

        // Act
        TotalReimbursementCalculator.calculateTotalReimbursement(reimbursementMock, adminLimitsMock);

        // Assert
        verify(reimbursementMock).setTotalReimbursement(300.0);
    }

    @Test
    public void testCalculateTotalReimbursementWithExceedingLimit() {
        // Arrange
        Reimbursement reimbursementMock = mock(Reimbursement.class);
        AdminLimits adminLimitsMock = mock(AdminLimits.class);

        when(reimbursementMock.getSelectedReceipts()).thenReturn(
                Arrays.asList(new Receipt("Receipt 1", 100), new Receipt("Receipt 2", 75))
        );
        when(reimbursementMock.getClaimDays()).thenReturn(5);
        when(reimbursementMock.getCarDistance()).thenReturn(100);
        when(adminLimitsMock.getDailyAllowanceRate()).thenReturn(25);
        when(adminLimitsMock.getCarMileageRate()).thenReturn(0.5);
        when(adminLimitsMock.getTotalReimbursementLimit()).thenReturn(150.0);

        // Act
        TotalReimbursementCalculator.calculateTotalReimbursement(reimbursementMock, adminLimitsMock);

        // Assert
        verify(reimbursementMock).setTotalReimbursement(150.0);
    }
}