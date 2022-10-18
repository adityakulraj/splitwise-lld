package com.aditya.splitwise.models;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserBalPair {
    private final String user;
    private final Double bal;
}
