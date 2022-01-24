package com.fedak.application.entity;

import javax.persistence.*;

@Entity
@Table(name="todos")
public class ToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name="todo")
    private String todo;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return "ToDo{" +
                "todo='" + todo + '\'' +
                ", user=" + user +
                '}';
    }

    public ToDo() {
    }

    public ToDo(String todo) {
        this.todo = todo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
