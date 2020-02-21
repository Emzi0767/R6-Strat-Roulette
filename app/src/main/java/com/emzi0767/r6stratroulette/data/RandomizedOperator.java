package com.emzi0767.r6stratroulette.data;

import com.emzi0767.r6stratroulette.models.runtime.RouletteRuntimeOperator;

public class RandomizedOperator {
    private RouletteRuntimeOperator operator;
    private String primary, secondary, gadget;

    public RandomizedOperator(
            RouletteRuntimeOperator op,
            String primary,
            String secondary,
            String gadget) {
        this.operator = op;
        this.primary = primary;
        this.secondary = secondary;
        this.gadget = gadget;
    }

    public RouletteRuntimeOperator getOperator() {
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
