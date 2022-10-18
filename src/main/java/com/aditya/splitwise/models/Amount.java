package com.aditya.splitwise.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Amount {

    private final Currency currency;
    private final double amount;


    public Amount add(Amount amount) {
        return new Amount(currency, this.amount + amount.amount);
    }

}

