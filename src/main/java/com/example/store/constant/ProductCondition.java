package com.example.store.constant;

public enum ProductCondition {
    NEW(0),
    OLD(1),
    REFURBISHED(2);

    int value;


    ProductCondition(int i) {
        this.value = i;
    }

    public static ProductCondition fromInteger(int i){
        switch (i){
            case 0:
                return NEW;
            case 1:
                return OLD;
            case 2:
                return REFURBISHED;
            default:
                return null;
        }
    }
}
