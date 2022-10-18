package com.aditya.splitwise.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Group {

    private final String Id;
    @Setter
    private final String name;
    @Setter
    private List<String> listOfUsers;
}
