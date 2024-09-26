package com.blog.service;

import com.blog.entity.Posts;
import com.blog.entity.Tags;
import com.blog.entity.Users;
import com.blog.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public void save(Users user) {
        Users userSaved = repository.save(user);
    }

    public List<Users> getAll() {
        return repository.findAll();
    }

    public Users getById(Long id) {
        Users users = repository.findById(id).orElse(null);
        Assert.notNull(users, "Users not found");
        return users;
    }

    public Users update(Long id, Users user) {
        Users userSaved = repository.findById(id).orElse(null);
        Assert.notNull(userSaved, "Usuário não existente");
        return repository.save(user);
    }

    public String delete(Long id){
        Users userSaved = repository.findById(id).orElse(null);
        Assert.notNull(userSaved, "Usuário não existente");
        repository.delete(userSaved);
        return "Deletado com sucesso!";
    }

}
