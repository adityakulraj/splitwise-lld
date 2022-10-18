package com.aditya.splitwise;

import com.aditya.splitwise.models.*;

import java.util.*;

import static com.aditya.splitwise.models.Currency.USD;

public class ExpenseService {
    private final Map<String, List<Expense>> groupExpenses;

    public ExpenseService() {
        this.groupExpenses = new HashMap<>();
    }
    public List<Expense> getGroupExpenses(String groupId) {
        return groupExpenses.get(groupId);
    }

    public void addExpense(Expense expense) {
        final var groupId = expense.getGroupID();
        if (groupId != null) {
            groupExpenses.putIfAbsent(groupId, new ArrayList<>());
            groupExpenses.get(groupId).add(expense);
        }
    }



    public PaymentGraph getPaymentGraph(BalanceMap resultExpense) {

        PriorityQueue<UserBalPair> pq_positive = new PriorityQueue<>(new Comparator<UserBalPair>() {
            @Override
            public int compare(UserBalPair o1, UserBalPair o2) {
                return o2.getBal().compareTo(o1.getBal());
            }
        });


        PriorityQueue<UserBalPair> pq_negative = new PriorityQueue<>(new Comparator<UserBalPair>() {
            @Override
            public int compare(UserBalPair o1, UserBalPair o2) {
                return o1.getBal().compareTo(o2.getBal());
            }
        });


        for(Map.Entry<String, Amount> e : resultExpense.getUserBal().entrySet()) {
            if(e.getValue().getAmount()<0) {
                pq_negative.add(new UserBalPair(e.getKey(), e.getValue().getAmount()));
            }
            else {
                pq_positive.add(new UserBalPair(e.getKey(), e.getValue().getAmount()));
            }
        }

        PaymentGraph finalPaymentGraph = new PaymentGraph();


        while(pq_positive.isEmpty()==false&&pq_negative.isEmpty()==false) {


            UserBalPair p1 = pq_positive.poll();

            UserBalPair n1 = pq_negative.poll();

            double val1 = p1.getBal();
            double val2 = n1.getBal()*-1;

            if(val1>val2) {
                System.out.println(n1.getUser()+" has to pay "+ p1.getUser()+" "+ val2);
                finalPaymentGraph.addToUserMap(p1.getUser(), n1.getUser(), new Amount(USD, val2));
            }
            else {
                System.out.println(n1.getUser()+" has to pay "+ p1.getUser()+" "+ val1);
                finalPaymentGraph.addToUserMap(p1.getUser(), n1.getUser(), new Amount(USD, val1));
            }


            double newVal = p1.getBal() + n1.getBal();

            if(newVal<0) {
                pq_negative.add(new UserBalPair(n1.getUser(), newVal));
            }
            else if(newVal>0) {
                pq_positive.add(new UserBalPair(p1.getUser(), newVal));
            }

        }

        return finalPaymentGraph;



    }
}
