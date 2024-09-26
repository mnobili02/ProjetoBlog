package com.blog.entity;

import com.blog.entity.enums.Category;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Titulo não pode ser nulo!")
    private String title;

    @NotBlank(message = "O post não pode estar vazio!")
    private String content;

    @ManyToOne
    @NotNull
    @JsonBackReference
    private Users users;

    @OneToMany
    private List<Comment> comments;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToMany
    private List<Tags> tags;
}
