package com.jpmc.midascore.dto;

public class Incentive {

    private float amount;

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Incentive() {
    }

    public Incentive(float amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Incentive{" +
                "amount=" + amount +
                '}';
    }
}
