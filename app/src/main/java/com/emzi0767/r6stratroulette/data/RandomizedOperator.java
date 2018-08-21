package com.emzi0767.r6stratroulette.data;

import com.emzi0767.r6stratroulette.models.OperatorData;

public class RandomizedOperator {
    private OperatorData operator;
    private String primary, secondary, gadget;

    public RandomizedOperator(OperatorData op, String primary, String secondary, String gadget) {
        this.operator = op;
        this.primary = primary;
        this.secondary = secondary;
        this.gadget = gadget;
    }

    public OperatorData getOperator() {
        return this.operator;
    }

    public String getPrimary() {
        return this.primary;
    }

    public String getSecondary() {
        return this.secondary;
    }

    public String getGadget() {
        return this.gadget;
    }
}
