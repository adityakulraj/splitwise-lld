package com.aditya.splitwise.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public class BalanceMap {



    private final Map<String, Amount> userBal;

    public BalanceMap() {
        userBal = new HashMap<>();

    }

    public Map<String, Amount> getBalances() {
        return userBal;
    }
}
