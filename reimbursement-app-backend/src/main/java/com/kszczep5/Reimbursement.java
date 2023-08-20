package com.kszczep5;

import java.util.List;

public class Reimbursement {
    private String tripDate;
    private List<Receipt> selectedReceipts;
    private int claimDays;
    private boolean disableSpecificDays;
    private int carDistance;
    private double totalReimbursement;

    public Reimbursement() {

    }

    public String getTripDate() {
        return tripDate;
    }

    public List<Receipt> getSelectedReceipts() {
        return selectedReceipts;
    }

    public int getClaimDays() {
        return claimDays;
    }

    public boolean isDisableSpecificDays() {
        return disableSpecificDays;
    }

    public int getCarDistance() {
        return carDistance;
    }

    public double getTotalReimbursement() {
        return totalReimbursement;
    }

    public void setTotalReimbursement(double totalReimbursement) {
        this.totalReimbursement = totalReimbursement;
    }
}

