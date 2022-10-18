package com.aditya.splitwise.models;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
@Getter
public class Expense {

    String expenseID;
    String groupID;
    String title;
    String description;

    String imageUrl;
    BalanceMap userBalances;

    public Expense(BalanceMap userBalances, String title, String imageUrl, String description, String groupId) {
        this.userBalances = userBalances;
        this.title = title;
        this.imageUrl = imageUrl;
        this.description = description;
        this.groupID = groupId;
    }

}
