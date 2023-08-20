package com.kszczep5;

import java.util.Arrays;
import java.util.List;

public class AdminLimits {

    private int dailyAllowanceRate;
    private double carMileageRate;
    private List<Receipt> receipts;
    private double totalReimbursementLimit;
    private int distanceLimit;
    private int receiptsLimit;

    public AdminLimits() {
        this.dailyAllowanceRate = 15;
        this.carMileageRate = 0.3;
        this.receipts = Arrays.asList(new Receipt("Taxi", 10),
                                      new Receipt("Hotel", 100),
                                      new Receipt("Plane", 200),
                                      new Receipt("Train", 50));
        this.totalReimbursementLimit = 10000.00;
        this.distanceLimit = 3000;
        this.receiptsLimit = 10;
    }

    public int getDailyAllowanceRate() {
        return dailyAllowanceRate;
    }

    public void setDailyAllowanceRate(int dailyAllowanceRate) {
        this.dailyAllowanceRate = dailyAllowanceRate;
    }

    public double getCarMileageRate() {
        return carMileageRate;
    }

    public void setCarMileageRate(double carMileageRate) {
        this.carMileageRate = carMileageRate;
    }

    public List<Receipt> getReceipts() {
        return receipts;
    }

    public void setReceipts(List<Receipt> receipts) {
        this.receipts = receipts;
    }

    public double getTotalReimbursementLimit() {
        return totalReimbursementLimit;
    }

    public void setTotalReimbursementLimit(double totalReimbursementLimit) {
        this.totalReimbursementLimit = totalReimbursementLimit;
    }

    public int getDistanceLimit() {
        return distanceLimit;
    }

    public void setDistanceLimit(int distanceLimit) {
        this.distanceLimit = distanceLimit;
    }

    public int getReceiptsLimit() {
        return receiptsLimit;
    }

    public void setReceiptsLimit(int receiptsLimit) {
        this.receiptsLimit = receiptsLimit;
    }
}
