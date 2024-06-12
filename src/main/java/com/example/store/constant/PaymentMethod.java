package com.example.store.constant;

public enum PaymentMethod {
    MOMO(0),
    BANK_TRANSFER(1),
    CASH(2),
    VISA(3);

    int value;

    PaymentMethod(int i) {
        this.value = i;
    }

    public static PaymentMethod fromInteger(int i) {
        switch (i) {
            case 0:
                return MOMO;
            case 1:
                return BANK_TRANSFER;
            case 2:
                return CASH;
            case 3:
                return VISA;
            default:
                return null;
        }
    }
}
