package com.kszczep5;

public class TotalReimbursementCalculator {

    public static void calculateTotalReimbursement(Reimbursement reimbursement, AdminLimits adminLimits) {
        double receiptsTotal = 0;
        for (Receipt receipt : reimbursement.getSelectedReceipts()) {
            receiptsTotal += receipt.getAmount();
        }
        double allowanceAmount = adminLimits.getDailyAllowanceRate() * reimbursement.getClaimDays();
        double mileageReimbursement = adminLimits.getCarMileageRate() * reimbursement.getCarDistance();
        double total = receiptsTotal + allowanceAmount + mileageReimbursement;
        if (total > adminLimits.getTotalReimbursementLimit()) {
            total = adminLimits.getTotalReimbursementLimit();
        }
        reimbursement.setTotalReimbursement(total);
    }
}
