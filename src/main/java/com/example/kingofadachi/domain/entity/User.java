package com.example.kingofadachi.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String username;
    private String password; // Consider hashing and salting in a real application
    private List<Character> characters;
    // ToDo: Add more attributes as needed
}
