package com.linkdom.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class User{
    @TableGenerator(name = "user_gen", initialValue = 500000)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "user_gen")
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private String profession;
    private String newsletter;
}
