package com.aditya.splitwise;

import com.aditya.splitwise.exceptions.NotAuthorizedException;
import com.aditya.splitwise.models.*;
import lombok.NonNull;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GroupService {

    private final ExpenseService expenseService;

    public static final Amount NIL = new Amount(Currency.USD, 0);

   @Setter
    Map<String, Group> groups;

    public GroupService(ExpenseService expenseService, Map<String, Group> groups) {
        this.expenseService = expenseService;
        this.groups = groups;
    }

    public void createGroup( String groupname,  List<String> users) {

            String groupId = UUID.randomUUID().toString();
            Group g = new Group(groupId, groupname, users);
            groups.put(groupId, g);

    }



    public PaymentGraph getGroupPaymentGraph(@NonNull  final String groupId, @NonNull final String userId) {

        if(!checkifUserExistsInGroup(groupId, userId))
            throw new NotAuthorizedException("User "+userId +" is not authorized to view these transactions");

        List<Expense> groupExpenses = expenseService.getGroupExpenses(groupId);
        BalanceMap resultExpense = sumExpenses(groupExpenses);
        return expenseService.getPaymentGraph(resultExpense);
    }

    private BalanceMap sumExpenses(List<Expense> groupExpenses) {

        Map<String,Amount> resultMap = new HashMap<>();

        for(Expense e : groupExpenses) {
          for(String user : e.getUserBalances().getUserBal().keySet()) {
              Amount amount = e.getUserBalances().getUserBal().get(user);
              resultMap.put(user, resultMap.getOrDefault(user, NIL).add(amount));
          }
        }

        return new BalanceMap(resultMap);
    }

    public BalanceMap getBalances(@NonNull final String groupId, @NonNull  final String userId) {

        if(!checkifUserExistsInGroup(groupId, userId))
            throw new NotAuthorizedException("User "+userId +" is not authorized to view these transactions");

        return sumExpenses(expenseService.getGroupExpenses(groupId));


    }

    private boolean checkifUserExistsInGroup(final String groupId, final String userId) {

        if(groups.containsKey(groupId)) {
            if( groups.get(groupId).getListOfUsers().contains(userId))
                return true;
        }
        return false;
    }


}
