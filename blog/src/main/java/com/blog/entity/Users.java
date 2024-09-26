package com.blog.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "É obrigatorio um username!")
    private String username;

    @OneToMany
    @JsonManagedReference
    private List<Posts> posts;

    @OneToMany
    @JsonManagedReference
    private List<Comment> comments;
}
