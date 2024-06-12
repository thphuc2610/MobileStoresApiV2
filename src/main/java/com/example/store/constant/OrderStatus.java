package com.example.store.constant;

public enum OrderStatus {
    PENDING(0),
    PROCESSING(1),
    COMPLETED(2),
    CANCELLED(3);

    int value;

    OrderStatus(int i) {
        this.value = i;
    }

    public static OrderStatus fromInteger(int i) {
        switch (i) {
            case 0:
                return PENDING;
            case 1:
                return PROCESSING;
            case 2:
                return COMPLETED;
            case 3:
                return CANCELLED;
            default:
                return null;
        }
    }
}
