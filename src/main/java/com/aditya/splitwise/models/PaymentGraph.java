package com.aditya.splitwise.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
@AllArgsConstructor
@Getter
@Setter

public class PaymentGraph {

    private final Map<String, BalanceMap>  graph;

    public PaymentGraph() {
        graph = new HashMap<>();
    }

    public void addToUserMap(String user1, String user2, Amount amount) {

        if(graph.containsKey(user1)) {
            graph.get(user1).getUserBal().put(user2, amount);
        }
        else {
            BalanceMap b = new BalanceMap();
            b.getUserBal().put(user2,amount);
            graph.put(user1,b);
        }


    }

}
